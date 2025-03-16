package com.ombremoon.tdmcosmeticsone.client.model;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HackingGauntletLayerModel extends GeoModel<TDMCosmeticItem> {
    @Override
    public ResourceLocation getModelResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "geo/item/hacking_gauntlet_layer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "textures/item/disruption_backpack_layer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "animations/item/disruption_backpack_layer.animation.json");
    }
}
