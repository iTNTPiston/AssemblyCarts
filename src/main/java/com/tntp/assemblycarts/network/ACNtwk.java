package com.tntp.assemblycarts.network;

import com.tntp.minecraftmodapi.network.IMessageRegisterFactory.ChannelHolder;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.entity.player.EntityPlayerMP;

public abstract class ACNtwk {
    @ChannelHolder
    public static final SimpleNetworkWrapper network = null;

    public static void sendToServer(SMessage<?> mes) {
        network.sendToServer(mes);
    }

    public static void sendToAll(SMessage<?> mes) {
        network.sendToAll(mes);
    }

    public static void sendToAllAround(SMessage<?> mes, int dimension, double x, double y, double z, double range) {
        network.sendToAllAround(mes, new TargetPoint(dimension, x, y, z, range));
    }

    public static void sendTo(SMessage<?> mes, EntityPlayerMP player) {
        network.sendTo(mes, player);
    }

    public static void sendToDimension(SMessage<?> mes, int dimension) {
        network.sendToDimension(mes, dimension);
    }

}
