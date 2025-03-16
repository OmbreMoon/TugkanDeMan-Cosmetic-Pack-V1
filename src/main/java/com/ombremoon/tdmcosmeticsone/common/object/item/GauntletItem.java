package com.ombremoon.tdmcosmeticsone.common.object.item;

import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.init.MobEffectInit;
import com.ombremoon.tdmcosmeticsone.common.network.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GauntletItem extends SwordItem {
    public GauntletItem(Properties pProperties) {
        super(Tiers.IRON, -1, -1.0F, pProperties);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".hacking_gauntlet.description").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".press_shift").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        EntityDimensions dimensions = pTarget.getDimensions(Pose.STANDING);
        Constants.LOG.debug("{}", pAttacker.level());
        if (dimensions.width <= 1 && dimensions.height <= 3) {
            pTarget.addEffect(new MobEffectInstance(MobEffectInit.MATRIX.get(), 120, 0, false, false));
            ModNetworking.activateMatrix(pTarget.getId(), true);
        }
        return true;
    }
}
