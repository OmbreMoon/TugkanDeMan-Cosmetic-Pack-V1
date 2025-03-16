package com.ombremoon.tdmcosmeticsone.client.model;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.object.item.TDMCosmeticItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.model.GeoModel;

public class TDMCosmeticModel extends GeoModel<TDMCosmeticItem> {
    @Override
    public ResourceLocation getModelResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "geo/item/" + getName(animatable) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "textures/item/" + getName(animatable) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(TDMCosmeticItem animatable) {
        return CommonClass.customLocation( "animations/item/" + getName(animatable) + ".animation.json");
    }

    private String getName(TDMCosmeticItem item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
}
