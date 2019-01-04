package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HandlerServer implements IGuiHandler {

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == ACGuis.getGuiID("GuiProcessBook")) {
      ItemStack stack = player.getCurrentEquippedItem();
      if (stack != null && stack.getItem() == ACItems.process_book) {
        AssemblyProcess proc = ItemProcessBook.getProcessFromStack(stack);
        return new ContainerProcessBook(player.inventory, proc);
      }
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }

}
