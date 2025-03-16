package com.ombremoon.tdmcosmeticsone.common.object;

import com.ombremoon.tdmcosmeticsone.common.init.ItemInit;
import com.ombremoon.tdmcosmeticsone.common.init.MenuTypeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SuitcaseMenu extends AbstractContainerMenu {
    private final ItemStack itemStack;

    public SuitcaseMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf buf) {
        this(pContainerId, pPlayerInventory, /*buf.readItem()*/pPlayerInventory.player.getMainHandItem().is(ItemInit.SUITCASE.get()) ? pPlayerInventory.player.getMainHandItem() : pPlayerInventory.player.getOffhandItem());
    }

    public SuitcaseMenu(int pContainerId, Inventory pPlayerInventory, ItemStack itemStack) {
        super(MenuTypeInit.SUITCASE_MENU.get(), pContainerId);
        this.itemStack = itemStack;
        IItemHandler handler = itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        int i = 51;

        for(int j = 0; j < 5; ++j) {
            this.addSlot(new SlotItemHandler(handler, j, 44 + j * 18, 20));
        }

        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(pPlayerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(pPlayerInventory, i1, 8 + i1 * 18, 109));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        IItemHandler handler = this.itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < handler.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, handler.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, handler.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
