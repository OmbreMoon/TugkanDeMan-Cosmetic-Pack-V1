package com.ombremoon.tdmcosmeticsone.common.init;

import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.network.ModNetworking;
import com.ombremoon.tdmcosmeticsone.common.object.entity.MilitaryRocket;
import com.ombremoon.tdmcosmeticsone.common.object.item.GauntletItem;
import com.ombremoon.tdmcosmeticsone.common.object.item.SuitcaseItem;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final RegistryObject<Item> SHORT_ENTREPRENEUR_HAT = registerItem("entrepreneur_hat_short", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.HEAD, itemProperties()) {
        @Override
        public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
            return 1;
        }
    });
    public static final RegistryObject<Item> MEDIUM_ENTREPRENEUR_HAT = registerItem("entrepreneur_hat_medium", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.HEAD, itemProperties()) {
        @Override
        public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
            return 2;
        }
    });
    public static final RegistryObject<Item> TALL_ENTREPRENEUR_HAT = registerItem("entrepreneur_hat_tall", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.HEAD, itemProperties()) {
        @Override
        public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
            return 3;
        }
    });
    public static final RegistryObject<Item> SUITCASE = registerItem("suitcase", () -> new SuitcaseItem(itemProperties()));
    public static final RegistryObject<Item> MONEY_BAG = registerItem("money_bag", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.BELT, itemProperties()) {
        @Override
        public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {
            return 1;
        }
    });
    public static final RegistryObject<Item> DOLLAR_BALLOON = registerItem("dollar_balloon", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.CURIO, renderProperties().hasAnimation().isTranslucent(), itemProperties()));

    public static final RegistryObject<Item> HACKER_VISOR = registerItem("hacker_visor", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.HEAD, renderProperties().isTranslucent(), itemProperties()) {
        @Override
        public void curioTick(SlotContext slotContext, ItemStack stack) {
            super.curioTick(slotContext, stack);
            LivingEntity livingEntity = slotContext.entity();
            if (livingEntity instanceof Player && !livingEntity.level().isClientSide) {
                List<Entity> entities = livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(15));
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity living) {
                        if (living.tickCount % 20 == 0)
                            living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false));
                    }
                }
            }
        }
    });
    public static final RegistryObject<Item> HACKING_GAUNTLET = registerItem("hacking_gauntlet", () -> new GauntletItem(itemProperties()));
    public static final RegistryObject<Item> DISRUPTION_BACKPACK = registerItem("disruption_backpack", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.BACK, renderProperties().isEmissive(), itemProperties()) {
        @Override
        public void curioTick(SlotContext slotContext, ItemStack stack) {
            super.curioTick(slotContext, stack);
            LivingEntity livingEntity = slotContext.entity();
            if (livingEntity instanceof Player && !livingEntity.level().isClientSide) {
                List<Entity> entities = livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(10));
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity living) {
                        if (living.tickCount % 20 == 0)
                            living.addEffect(new MobEffectInstance(MobEffectInit.BIG_SLOW.get(), 100));
                    }
                }
            }
        }
    });

    public static final RegistryObject<Item> MILITARY_COAT = registerItem("military_coat", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.BODY, itemProperties()));
    public static final RegistryObject<Item> MILITARY_CAP = registerItem("military_cap", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.HEAD, itemProperties()));
    public static final RegistryObject<Item> MILITARY_CANNON = registerItem("military_cannon", () -> new Item(itemProperties()) {

        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            if (Screen.hasShiftDown()) {
                pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".military_cannon.description").withStyle(ChatFormatting.GRAY));
            } else {
                pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".press_shift").withStyle(ChatFormatting.GRAY));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
            ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
            if (!pLevel.isClientSide) {
                MilitaryRocket militaryRocket = new MilitaryRocket(pLevel, pPlayer);
                militaryRocket.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2.0F, 1.0F);
                pLevel.addFreshEntity(militaryRocket);
                pLevel.playSound(null, militaryRocket, SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

                if (!pPlayer.getAbilities().instabuild)
                    pPlayer.getCooldowns().addCooldown(this, 200);

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.consume(itemStack);
            }

            return InteractionResultHolder.pass(itemStack);
        }

        @Override
        public UseAnim getUseAnimation(ItemStack pStack) {
            return UseAnim.CUSTOM;
        }
    });

    public static final RegistryObject<Item> GHOSTVISION_GOGGLES = registerItem("ghostvision_goggles", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.HEAD, renderProperties().isTranslucent(), itemProperties()) {
        @Override
        public void curioTick(SlotContext slotContext, ItemStack stack) {
            super.curioTick(slotContext, stack);
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0,false, false));
        }
    });
    public static final RegistryObject<Item> VACUUM_BACKPACK = registerItem("vacuum_backpack", () -> new TDMCosmeticItem(TDMCosmeticItem.Type.BACK, renderProperties().isEmissive(), itemProperties()));
    public static final RegistryObject<Item> ECTOCLEANER = registerItem("ectocleaner", () -> new Item(itemProperties()) {
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            if (Screen.hasShiftDown()) {
                pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".ectocleaner.description").withStyle(ChatFormatting.GRAY));
            } else {
                pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".press_shift").withStyle(ChatFormatting.GRAY));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }

        @Override
        public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
            if (pEntity instanceof LivingEntity living) {
                boolean flag = pStack.equals(living.getMainHandItem(), false) || pStack.equals(living.getOffhandItem(), false);
                SlotResult result = CuriosApi.getCuriosInventory(living).orElse(null).findCurio("back", 0).orElse(null);
                int range = result != null && result.stack().is(ItemInit.VACUUM_BACKPACK.get()) ? 20 : 10;
                if (flag) {
                    if (living instanceof Player && !living.level().isClientSide) {
                        List<ItemEntity> entities = living.level().getEntitiesOfClass(ItemEntity.class, living.getBoundingBox().inflate(range));
                        for (ItemEntity item : entities) {
                            if (item.isAlive() && !item.hasPickUpDelay()) {
                                item.playerTouch((Player) living);
                            }
                        }
                    }
                    if (living instanceof Player && !living.level().isClientSide) {
                        List<ExperienceOrb> entities = living.level().getEntitiesOfClass(ExperienceOrb.class, living.getBoundingBox().inflate(range));
                        for (ExperienceOrb orb : entities) {
                            if (orb.isAlive()) {
                                orb.playerTouch((Player) living);
                            }
                        }
                    }
                }
            }
        }
    });

    public static final RegistryObject<CreativeModeTab> TAB = registerCreativeModeTab(Constants.MOD_ID, SUITCASE);

    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    protected static RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<Item> item) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(item.get()))
                .title(Component.translatable("itemgroup." + name + ".tab"))
                .displayItems(
                        ((itemDisplayParameters, output) -> {
                            for (var object : ITEMS.getEntries()) {
                                ItemStack stack = new ItemStack(object.get());
                                output.accept(stack);
                            }
                        })
                ).build());
    }

    public static Item.Properties itemProperties() {
        return new Item.Properties().stacksTo(1);
    }

    public static TDMCosmeticItem.RenderProperties renderProperties() {
        return new TDMCosmeticItem.RenderProperties();
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
