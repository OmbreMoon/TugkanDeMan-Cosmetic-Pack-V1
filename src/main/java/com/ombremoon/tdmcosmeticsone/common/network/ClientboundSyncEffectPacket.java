package com.ombremoon.tdmcosmeticsone.common.network;

import com.ombremoon.tdmcosmeticsone.common.object.effect.MatrixEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSyncEffectPacket {
    private final int entityID;
    private final boolean active;

    public ClientboundSyncEffectPacket(int entityID, boolean active) {
        this.entityID = entityID;
        this.active = active;
    }

    public ClientboundSyncEffectPacket(final FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
        this.active = buf.readBoolean();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.active);
    }

    public static void handle(ClientboundSyncEffectPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handle.handleClient(packet.entityID, packet.active)));
        ctx.get().setPacketHandled(true);
    }

    public static class Handle {
        public static DistExecutor.SafeRunnable handleClient(int entityID, boolean active) {
            return new DistExecutor.SafeRunnable() {
                @Override
                public void run() {
                    Level level = Minecraft.getInstance().level;
                    Entity entity = level.getEntity(entityID);

                    if (entity == null)
                        return;

                    String tag = MatrixEffect.TAG;
                    if (entity instanceof LivingEntity living) {
                        if (active) {
                            living.addTag(tag);
                        } else {
                            living.removeTag(tag);
                        }
                    }
                }
            };
        }
    }
}
