package com.ombremoon.tdmcosmeticsone.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.Emissive;
import com.ombremoon.tdmcosmeticsone.common.init.ItemInit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HeadEmissiveLayer<T extends Emissive> extends GeoRenderLayer<T> {

    public HeadEmissiveLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (animatable == ItemInit.HACKER_VISOR.get()) {
            RenderType emissive = RenderType.eyes(CommonClass.customLocation("textures/item/hacker_visor_layer.png"));
            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, emissive, bufferSource.getBuffer(emissive), partialTick, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (animatable == ItemInit.GHOSTVISION_GOGGLES.get()) {
            RenderType emissive = RenderType.eyes(CommonClass.customLocation("textures/item/goggle_layer.png"));
            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, emissive, bufferSource.getBuffer(emissive), partialTick, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
