package com.ombremoon.tdmcosmeticsone.common.network;

import com.ombremoon.tdmcosmeticsone.CommonClass;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static final String VER = "1";
    public static final SimpleChannel PACKET_CHANNEL = NetworkRegistry.newSimpleChannel(CommonClass.customLocation("main"), () -> VER, VER::equals, VER::equals);

    public static void activateMatrix(int entityID, boolean active) {
        sendToClients(new ClientboundSyncEffectPacket(entityID, active));
    }

    public static void registerPackets() {
        var id = 0;
        PACKET_CHANNEL.registerMessage(id++, ClientboundSyncEffectPacket.class, ClientboundSyncEffectPacket::encode, ClientboundSyncEffectPacket::new, ClientboundSyncEffectPacket::handle);
    }

    protected <MSG> void sendToServer(MSG message) {
        ModNetworking.PACKET_CHANNEL.sendToServer(message);
    }

    protected static  <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    protected static  <MSG> void sendToClients(MSG message) {
        PACKET_CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }
}
