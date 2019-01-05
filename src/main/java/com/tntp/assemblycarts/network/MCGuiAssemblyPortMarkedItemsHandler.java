package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.ProvideManager;
import com.tntp.assemblycarts.gui.ContainerAssemblyPort;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MCGuiAssemblyPortMarkedItemsHandler implements IMessageHandler<MCGuiAssemblyPortMarkedItems, IMessage> {

  @Override
  public IMessage onMessage(MCGuiAssemblyPortMarkedItems message, MessageContext ctx) {
    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    if (player.openContainer.windowId == message.windowID) {
      if (player.openContainer instanceof ContainerAssemblyPort) {
        ItemStack[] marked = MCGuiAssemblyPortMarkedItems.toStacks(message.getNBT1());
        for (int i = 0; i < marked.length; i++) {
          ((ContainerAssemblyPort) player.openContainer).setMarkedItemStack(i, marked[i]);
        }
      }
    }
    return null;
  }

}
