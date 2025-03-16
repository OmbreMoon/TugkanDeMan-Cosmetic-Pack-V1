package com.ombremoon.tdmcosmeticsone.common.object.capability;

import com.ombremoon.tdmcosmeticsone.common.init.ItemInit;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SuitcaseItemStackHandler extends ItemStackHandler {

    public SuitcaseItemStackHandler() {
        super(5);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return !stack.is(ItemInit.SUITCASE.get());
    }
}
