package com.tntp.assemblycarts.gui;

import java.util.List;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class HandlerClient extends HandlerServer {
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    // SGui gui = null;
    TileEntity tile = world.getTileEntity(x, y, z);
    if (ID == ACGuis.getGuiID("ProcessBook")) {
      ItemStack stack = player.getCurrentEquippedItem();
      if (stack != null && stack.getItem() == ACItems.process_book) {
        AssemblyProcess proc = ItemProcessBook.getProcessFromStack(stack);
        return new GuiProcessBook(player.inventory, proc);
      }
    } else if (ID == ACGuis.getGuiID("MinecartAssembly")) {
      AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x - 0.5, y - 0.5, z - 0.5, x + 1.5, y + 1.5, z + 1.5);
      List<Object> entities = world.selectEntitiesWithinAABB(EntityMinecartAssembly.class, box, null);
      if (entities.size() > 0) {
        EntityMinecartAssembly cart = (EntityMinecartAssembly) entities.get(0);
        return new GuiMinecartAssembly(player.inventory, cart);
      }
    } else if (ID == ACGuis.getGuiID("AssemblyRequester")) {
      if (tile instanceof TileAssemblyRequester) {
        return new GuiAssemblyRequester(player.inventory, (TileAssemblyRequester) tile);
      }
    }

    return null;
  }
}
