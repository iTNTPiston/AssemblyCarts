package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.gui.ContainerAssemblyPort;
import com.tntp.assemblycarts.gui.ContainerProcessBook;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Handles GuiAssemblyPort slot click
 * 
 * @author iTNTPiston
 *
 */
public class MSGuiAssemblyPortSlotClick extends MAInt3<MSGuiAssemblyPortSlotClick> {

  public MSGuiAssemblyPortSlotClick(int windowID, int slot, int mouse) {
    super(windowID, slot, mouse);
  }

  public MSGuiAssemblyPortSlotClick() {
    super(0, 0, 0);
  }

  @Override
  public IMessage onMessage(MSGuiAssemblyPortSlotClick message, MessageContext ctx) {
    EntityPlayer player = ctx.getServerHandler().playerEntity;
    if (player.openContainer.windowId == message.getI1()) {
      ContainerAssemblyPort cont = (ContainerAssemblyPort) player.openContainer;
      cont.processSlotClick(message.getI2(), message.getI3());
    }
    return null;
  }

}
