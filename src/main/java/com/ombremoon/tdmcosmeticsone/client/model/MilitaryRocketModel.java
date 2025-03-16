package com.ombremoon.tdmcosmeticsone.client.model;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.object.entity.MilitaryRocket;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MilitaryRocketModel extends GeoModel<MilitaryRocket> {
    @Override
    public ResourceLocation getModelResource(MilitaryRocket animatable) {
        return CommonClass.customLocation( "geo/item/military_rocket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MilitaryRocket animatable) {
        return CommonClass.customLocation( "textures/item/military_rocket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MilitaryRocket animatable) {
        return CommonClass.customLocation( "animations/item/military_rocket.animation.json");
    }
}
