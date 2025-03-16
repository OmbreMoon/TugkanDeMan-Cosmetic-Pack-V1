package com.ombremoon.tdmcosmeticsone.common.object.item;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.Emissive;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class TDMCosmeticItem extends Item implements ICurioItem, Emissive {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private final Type type;
    private final RenderProperties renderProperties;

    public TDMCosmeticItem(Type type, Properties properties) {
        this(type, new RenderProperties(), properties);
    }

    public TDMCosmeticItem(Type type, RenderProperties renderProperties, Properties pProperties) {
        super(pProperties);
        this.type = type;
        this.renderProperties = renderProperties;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + "." + ForgeRegistries.ITEMS.getKey(this).getPath() + ".description").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".press_shift").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            var result = CuriosApi.getCuriosInventory(pPlayer).orElse(null).findCurio(type.getName(), 0);
            if (type == Type.CURIO) {
                for (Type types : Type.values()) {
                    var result1 = CuriosApi.getCuriosInventory(pPlayer).orElse(null).findCurio(types.getName(), 0);
                    if (result1.isEmpty()) {
                        var handler = CuriosApi.getCuriosInventory(pPlayer).orElse(null).getStacksHandler(types.getName());
                        ItemStack copy = itemStack.copy();
                        handler.get().getStacks().setStackInSlot(0, copy);
                        itemStack.shrink(1);
                        break;
                    }
                }
            } else if (result.isEmpty()) {
                var handler = CuriosApi.getCuriosInventory(pPlayer).orElse(null).getStacksHandler(type.getName());
                ItemStack copy = itemStack.copy();
                handler.get().getStacks().setStackInSlot(0, copy);
                itemStack.shrink(1);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public Type getType() {
        return this.type;
    }

    public boolean isTransparent() {
        return this.renderProperties.isTranslucent;
    }

    public boolean isEmissive() {
        return this.renderProperties.isEmissive;
    }

    public ResourceLocation getEmissiveTexture() {
        return CommonClass.customLocation(this.renderProperties.emissiveLoc);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        if (this.renderProperties.hasAnimation) {
            controllers.add(new AnimationController<>(this, "Idle", 0, state -> state.setAndContinue(IDLE)));
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public enum Type {
        HEAD("head", EquipmentSlot.HEAD),
        BACK("back", EquipmentSlot.CHEST),
        BELT("belt", EquipmentSlot.CHEST),
        BODY("body", EquipmentSlot.CHEST),
        CURIO("curio", EquipmentSlot.CHEST);

        private final String name;
        private final EquipmentSlot pseudoSlot;

        Type(String name, EquipmentSlot pseudoSlot) {
            this.name = name;
            this.pseudoSlot = pseudoSlot;
        }

        public String getName() {
            return this.name;
        }

        public EquipmentSlot getPseudoSlot() {
            return this.pseudoSlot;
        }
    }

    public static class RenderProperties {
        boolean hasAnimation;
        boolean isTranslucent;
        boolean isEmissive;
        @Nullable
        String emissiveLoc;

        public RenderProperties hasAnimation() {
            this.hasAnimation = true;
            return this;
        }

        public RenderProperties isTranslucent() {
            this.isTranslucent = true;
            return this;
        }

        public RenderProperties isEmissive() {
            this.isEmissive = true;
            return this;
        }

        public RenderProperties emissiveLoc(String loc) {
            this.emissiveLoc = loc;
            return this;
        }
    }
}
