package com.ombremoon.tdmcosmeticsone.common.object.item;

import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.object.SuitcaseMenu;
import com.ombremoon.tdmcosmeticsone.common.object.capability.SuitcaseCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuitcaseItem extends SwordItem {
    public SuitcaseItem(Properties pProperties) {
        super(Tiers.GOLD, 3, -1.3F, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".suitcase.description").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".press_shift").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) pPlayer, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer1) -> new SuitcaseMenu(pContainerId, pPlayerInventory, itemStack), Component.translatable("menu.tdmcosmeticsone.suitcase")));
        }
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (stack.is(this)) {
            return new SuitcaseCapabilityProvider(stack);
        }
        return super.initCapabilities(stack, nbt);
    }
}
