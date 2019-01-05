package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.IMarker;
import com.tntp.assemblycarts.api.MarkManager;
import com.tntp.assemblycarts.network.ACNetwork;
import com.tntp.assemblycarts.network.MCGuiMarkManager;
import com.tntp.assemblycarts.network.MCGuiProvideManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SContainerMarkManager extends SContainer implements IMarker {

  public SContainerMarkManager(IInventory playerInventory, int slots, IInventory machine, int playerSlotsX,
      int playerSlotsY) {
    super(playerInventory, slots, machine, playerSlotsX, playerSlotsY);
  }

  public void sendMarkManager(EntityPlayerMP player) {
    NBTTagCompound tag = new NBTTagCompound();
    getMarkManager().writeToNBT(tag);
    ACNetwork.network.sendTo(new MCGuiMarkManager(this.windowId, tag), player);
  }

  @Override
  public void addCraftingToCrafters(ICrafting ic) {
    super.addCraftingToCrafters(ic);
    if (ic instanceof EntityPlayerMP) {
      sendMarkManager((EntityPlayerMP) ic);
    }
  }

}
