package com.ombremoon.tdmcosmeticsone.common.init;

import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.object.entity.MilitaryRocket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MOD_ID);

    public static final RegistryObject<EntityType<MilitaryRocket>> ROCKET = registerProjectile("military_rocket", MilitaryRocket::new, 0.7F, 0.5F);

    protected static <T extends Projectile> RegistryObject<EntityType<T>> registerProjectile(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, MobCategory.MISC).sized(width, height).clientTrackingRange(4);

        return EntityInit.ENTITY_TYPES.register(name, () -> {
            return builder.build(name);
        });
    }
    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
