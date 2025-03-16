package com.ombremoon.tdmcosmeticsone.client.renderer;

import com.ombremoon.tdmcosmeticsone.client.model.TDMCosmeticModel;
import com.ombremoon.tdmcosmeticsone.client.renderer.layer.HeadEmissiveLayer;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TDMCosmeticRenderer extends GeoArmorRenderer<TDMCosmeticItem> {
    public TDMCosmeticRenderer() {
        super(new TDMCosmeticModel());
        addRenderLayer(new HeadEmissiveLayer<>(this));
    }

    @Override
    public RenderType getRenderType(TDMCosmeticItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return animatable.isTransparent() ? RenderType.entityTranslucent(texture) : super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        setAllVisible(false);

        TDMCosmeticItem item = (TDMCosmeticItem) this.currentStack.getItem();
        switch (item.getType()) {
            case HEAD -> setBoneVisible(this.head, true);
            case BODY -> {
                setBoneVisible(this.body, true);
                setBoneVisible(this.rightArm, true);
                setBoneVisible(this.leftArm, true);
            }
            case BACK, BELT, CURIO -> setBoneVisible(this.body, true);
            default -> {}
        }
    }
}
