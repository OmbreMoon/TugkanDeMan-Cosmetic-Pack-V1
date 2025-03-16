package com.ombremoon.tdmcosmeticsone.common.object.entity;

import com.ombremoon.tdmcosmeticsone.common.init.EntityInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MilitaryRocket extends Projectile implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MilitaryRocket(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MilitaryRocket(Level pLevel, LivingEntity shooter) {
        super(EntityInit.ROCKET.get(), pLevel);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (result.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, result))
            this.onHit(result);
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        double d3 = this.random.nextDouble() * 0.25;
        for(int i = 0; i < 2; ++i) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + d3, this.getY() + d3, this.getZ() + d3, vec3.x, vec3.y, vec3.z);
        }
        for(int i = 0; i < 2; ++i) {
            this.level().addParticle(ParticleTypes.FLAME, this.getX() + d3, this.getY() + d3, this.getZ() + d3, vec3.x, vec3.y, vec3.z);
        }

        float f = this.isInWaterOrBubble() ? 0.89F : 0.99F;
        float f1 = -0.06F;
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale(f));
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, f1, 0.0D));
            }
            this.setPos(d0, d1, d2);
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 6.0F, Level.ExplosionInteraction.TNT);
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
