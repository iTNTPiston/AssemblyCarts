package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.gui.ContainerProcessBook;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Handles GuiProcessBook slot click
 * 
 * @author zhaoy12
 *
 */
public class MSGuiProcessBookSlotClick extends MAInt3<MSGuiProcessBookSlotClick> {

  public MSGuiProcessBookSlotClick(int windowID, int slot, int mouse) {
    super(windowID, slot, mouse);
    // TODO Auto-generated constructor stub
  }

  public MSGuiProcessBookSlotClick() {
    super(0, 0, 0);
  }

  @Override
  public IMessage onMessage(MSGuiProcessBookSlotClick message, MessageContext ctx) {
    EntityPlayer player = ctx.getServerHandler().playerEntity;
    if (player.openContainer.windowId == message.getI1()) {
      ContainerProcessBook cont = (ContainerProcessBook) player.openContainer;
      cont.processSlotClick(message.getI2(), message.getI3());
    }
    return null;
  }

}
