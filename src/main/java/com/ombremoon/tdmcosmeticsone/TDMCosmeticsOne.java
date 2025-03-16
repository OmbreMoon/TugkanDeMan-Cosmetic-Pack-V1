package com.ombremoon.tdmcosmeticsone;

import com.ombremoon.tdmcosmeticsone.client.SuitcaseScreen;
import com.ombremoon.tdmcosmeticsone.client.renderer.MilitaryRocketRenderer;
import com.ombremoon.tdmcosmeticsone.client.renderer.layer.MatrixLayer;
import com.ombremoon.tdmcosmeticsone.client.renderer.layer.TDMCosmeticLayer;
import com.ombremoon.tdmcosmeticsone.common.GlobalLootModifiers;
import com.ombremoon.tdmcosmeticsone.common.init.EntityInit;
import com.ombremoon.tdmcosmeticsone.common.init.ItemInit;
import com.ombremoon.tdmcosmeticsone.common.init.MenuTypeInit;
import com.ombremoon.tdmcosmeticsone.common.init.MobEffectInit;
import com.ombremoon.tdmcosmeticsone.common.network.ModNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Mod(Constants.MOD_ID)
public class TDMCosmeticsOne {

    public TDMCosmeticsOne() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);
        MinecraftForge.EVENT_BUS.register(this);
        CommonClass.init(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetworking::registerPackets);
    }

    private void gatherData(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        DataGenerator generator = event.getGenerator();

        boolean includeServer = event.includeServer();
        generator.addProvider(includeServer, new GlobalLootModifiers(packOutput));
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    public static class CommonEvents {

        @SubscribeEvent
        public static void onLivingAttack(LivingHurtEvent event) {
            Entity entity = event.getSource().getEntity();
            if (entity == null)
                return;

            if (!(entity instanceof Player player))
                return;

            AtomicInteger sharpeness = new AtomicInteger();
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {

                for (var entry : handler.getCurios().entrySet()) {
                    IDynamicStackHandler stackHandler = entry.getValue().getStacks();

                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        sharpeness.addAndGet(CuriosApi.getCurio(stackHandler.getStackInSlot(i)).map(
                                curio -> {
                                    if (curio.getStack().is(ItemInit.MILITARY_CAP.get()) || curio.getStack().is(ItemInit.MILITARY_COAT.get())) {
                                        return 1;
                                    }
                                    return 0;
                                }
                        ).orElse(0));
                    }
                }
            });
            event.setAmount(event.getAmount() + (sharpeness.get() * 0.5F));

            ItemStack itemStack = player.getOffhandItem();
            if (itemStack.is(ItemInit.HACKING_GAUNTLET.get())) {
                LivingEntity target = event.getEntity();
                EntityDimensions dimensions = target.getDimensions(Pose.STANDING);
                if (dimensions.width <= 1 && dimensions.height <= 3) {
                    target.addEffect(new MobEffectInstance(MobEffectInit.MATRIX.get(), 100, 0, false, false));
                    ModNetworking.activateMatrix(target.getId(), true);
                }
            }
        }

        @SubscribeEvent
        public static void onEntityDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntity();
            if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player) {
                var result = CuriosApi.getCuriosInventory(player).orElse(null).findFirstCurio(ItemInit.DOLLAR_BALLOON.get());
                float f0 = CuriosApi.getCuriosInventory(player).orElse(null).findFirstCurio(ItemInit.MONEY_BAG.get()).isPresent() ? 1.0F : 0.0F;
                result.ifPresent(slotResult -> {
                    Random random = new Random();
                    float f = random.nextFloat(0.0F, 1.0F);
                    if (f < 0.07 + ((float) EnchantmentHelper.getMobLooting(player) + f0) * 0.01F) {
                        ItemEntity itemEntity = new ItemEntity(player.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.EMERALD));
                        player.level().addFreshEntity(itemEntity);
                    }
                });
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityInit.ROCKET.get(), MilitaryRocketRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.AddLayers event) {
            for (final String skin : event.getSkins()) {
                final LivingEntityRenderer<Player, PlayerModel<Player>> playerRenderer = event.getSkin(skin);
                if (playerRenderer == null)
                    continue;

                playerRenderer.addLayer(new TDMCosmeticLayer<>(playerRenderer));
                playerRenderer.addLayer(new MatrixLayer<>(playerRenderer));
            }

            for (var renders : event.getContext().getEntityRenderDispatcher().renderers.values()) {
                if (renders instanceof LivingEntityRenderer<?,?>) {
                    LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer = (LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>) renders;
                    renderer.addLayer(new MatrixLayer<>(renderer));
                }
            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypeInit.SUITCASE_MENU.get(), SuitcaseScreen::new);
        }
    }
}
