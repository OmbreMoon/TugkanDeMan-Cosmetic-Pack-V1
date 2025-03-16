package com.ombremoon.tdmcosmeticsone.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ombremoon.tdmcosmeticsone.client.model.MatrixModel;
import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.object.effect.MatrixEffect;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Random;

public class MatrixLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final GeoObjectRenderer<MatrixLayer<T, M>> model;

    public MatrixLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
        this.model = new GeoObjectRenderer<>(new MatrixModel<>());
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity.getTags().contains(MatrixEffect.TAG)) {
            RenderType renderType = RenderType.eyes(CommonClass.customLocation("textures/item/disruption_backpack_layer.png"));
            VertexConsumer vertexConsumer = pBuffer.getBuffer(renderType);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180));
            pPoseStack.translate(-0.5F, -2.0F, -0.5F);
            model.render(pPoseStack, this, pBuffer, renderType, vertexConsumer, 15728640);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object object) {
        return RenderUtils.getCurrentTick();
    }
}
