package com.ombremoon.tdmcosmeticsone.common.init;

import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.object.effect.BigSlowEffect;
import com.ombremoon.tdmcosmeticsone.common.object.effect.MatrixEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);

    public static final RegistryObject<MobEffect> MATRIX = MOB_EFFECTS.register("matrix", () -> new MatrixEffect(MobEffectCategory.HARMFUL, 0x448AFF));
    public static final RegistryObject<MobEffect> BIG_SLOW = MOB_EFFECTS.register("big_slow", () -> new BigSlowEffect(MobEffectCategory.HARMFUL, 0x448AFF).addAttributeModifier(Attributes.MOVEMENT_SPEED, "f0871e7f-9245-4384-9a36-f6b8fc58b65b", -0.25F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus modEventBus) {
        MOB_EFFECTS.register(modEventBus);
    }
}
