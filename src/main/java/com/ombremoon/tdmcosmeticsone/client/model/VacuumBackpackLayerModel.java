package com.ombremoon.tdmcosmeticsone.client.model;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VacuumBackpackLayerModel extends GeoModel<TDMCosmeticItem> {
    @Override
    public ResourceLocation getModelResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "geo/item/vacuum_backpack_layer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "textures/item/vacuum_backpack_layer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "animations/item/vacuum_backpack_layer.animation.json");
    }
}
