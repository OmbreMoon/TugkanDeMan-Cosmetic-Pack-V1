package com.ombremoon.tdmcosmeticsone.client.renderer;

import com.ombremoon.tdmcosmeticsone.client.renderer.layer.EmissiveLayer;
import com.ombremoon.tdmcosmeticsone.client.model.DisruptionBackpackLayerModel;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class DisruptionBackpackRenderer extends TDMCosmeticRenderer {
    public DisruptionBackpackRenderer() {
        super();
        addRenderLayer(new EmissiveLayer<>(this));
    }

    @Override
    public RenderType getRenderType(TDMCosmeticItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public GeoModel<TDMCosmeticItem> getGeoModel() {
        return new DisruptionBackpackLayerModel();
    }
}
