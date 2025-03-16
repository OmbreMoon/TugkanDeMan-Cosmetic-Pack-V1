package com.ombremoon.tdmcosmeticsone.client;

import com.ombremoon.tdmcosmeticsone.common.object.SuitcaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SuitcaseScreen extends AbstractContainerScreen<SuitcaseMenu> {
        private static final ResourceLocation HOPPER_LOCATION = new ResourceLocation("textures/gui/container/hopper.png");

        public SuitcaseScreen(SuitcaseMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
            super(pMenu, pPlayerInventory, pTitle);
            this.imageHeight = 133;
            this.inventoryLabelY = this.imageHeight - 94;
        }

        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            this.renderBackground(pGuiGraphics);
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        }

        protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            pGuiGraphics.blit(HOPPER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        }
}
