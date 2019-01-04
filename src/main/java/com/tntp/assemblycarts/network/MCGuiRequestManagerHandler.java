package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.api.IRequester;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MCGuiRequestManagerHandler implements IMessageHandler<MCGuiRequestManager, IMessage> {

  @Override
  public IMessage onMessage(MCGuiRequestManager message, MessageContext ctx) {
    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    if (player.openContainer.windowId == message.windowID) {
      if (player.openContainer instanceof IRequester) {
        RequestManager rm = ((IRequester) player.openContainer).getRequestManager();
        rm.readFromNBT(message.getNBT1());
      }
    }
    return null;
  }

}
