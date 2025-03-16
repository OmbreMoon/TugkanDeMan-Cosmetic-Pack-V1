package com.ombremoon.tdmcosmeticsone.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ombremoon.tdmcosmeticsone.client.model.MilitaryRocketModel;
import com.ombremoon.tdmcosmeticsone.client.renderer.layer.EmissiveLayer;
import com.ombremoon.tdmcosmeticsone.common.object.entity.MilitaryRocket;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MilitaryRocketRenderer extends GeoEntityRenderer<MilitaryRocket> {
    public MilitaryRocketRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MilitaryRocketModel());
    }

    @Override
    protected void applyRotations(MilitaryRocket animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        poseStack.translate(0.0F, -0.2F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(animatable.xRotO));
    }
}
