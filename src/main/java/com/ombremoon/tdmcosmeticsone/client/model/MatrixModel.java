package com.ombremoon.tdmcosmeticsone.client.model;

import com.ombremoon.tdmcosmeticsone.client.renderer.layer.MatrixLayer;
import com.ombremoon.tdmcosmeticsone.CommonClass;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.model.GeoModel;

public class MatrixModel<T extends LivingEntity, M extends EntityModel<T>> extends GeoModel<MatrixLayer<T, M>> {

    @Override
    public ResourceLocation getModelResource(MatrixLayer animatable) {
        return CommonClass.customLocation( "geo/matrix.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MatrixLayer animatable) {
        return CommonClass.customLocation( "textures/item/disruption_backpack_layer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MatrixLayer animatable) {
        return CommonClass.customLocation( "animations/matrix.animation.json");
    }
}
