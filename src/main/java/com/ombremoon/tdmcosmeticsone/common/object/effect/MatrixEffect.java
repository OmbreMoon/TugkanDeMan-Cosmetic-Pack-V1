package com.ombremoon.tdmcosmeticsone.common.object.effect;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import com.ombremoon.tdmcosmeticsone.common.network.ModNetworking;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

public class MatrixEffect extends MobEffect {
    public static final String TAG = CommonClass.customLocation("matrix").toString();
    public MatrixEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide) {
            pLivingEntity.setDeltaMovement(0, 0, 0);
            pLivingEntity.hurtMarked = true;
            if (!(pLivingEntity instanceof Player)) {
                ((Mob)pLivingEntity).setNoAi(true);
            }
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        if (!(pLivingEntity instanceof Player)) {
            ((Mob)pLivingEntity).setNoAi(false);
        }
        ModNetworking.activateMatrix(pLivingEntity.getId(), false);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
