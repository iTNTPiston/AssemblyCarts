package com.tntp.assemblycarts.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HandlerClient extends HandlerServer {
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    // SGui gui = null;
    TileEntity tile = world.getTileEntity(x, y, z);

    return null;
  }
}
