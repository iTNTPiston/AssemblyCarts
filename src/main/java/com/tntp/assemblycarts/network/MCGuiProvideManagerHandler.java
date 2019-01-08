package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.ProvideManager;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MCGuiProvideManagerHandler implements IMessageHandler<MCGuiProvideManager, IMessage> {

    @Override
    public IMessage onMessage(MCGuiProvideManager message, MessageContext ctx) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player.openContainer.windowId == message.windowID) {
            if (player.openContainer instanceof IProvider) {
                ProvideManager pm = ((IProvider) player.openContainer).getProvideManager();
                pm.readFromNBT(message.getNBT1());
            }
        }
        return null;
    }

}
