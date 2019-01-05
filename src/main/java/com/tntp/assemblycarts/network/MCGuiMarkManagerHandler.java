package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.api.IMarker;
import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.MarkManager;
import com.tntp.assemblycarts.api.ProvideManager;
import com.tntp.assemblycarts.gui.ContainerAssemblyPort;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MCGuiMarkManagerHandler implements IMessageHandler<MCGuiMarkManager, IMessage> {

  @Override
  public IMessage onMessage(MCGuiMarkManager message, MessageContext ctx) {
    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    if (player.openContainer.windowId == message.windowID) {
      if (player.openContainer instanceof IMarker) {
        MarkManager markManager = ((IMarker) player.openContainer).getMarkManager();
        markManager.readFromNBT(message.getNBT1());
      }
    }
    return null;
  }

}
