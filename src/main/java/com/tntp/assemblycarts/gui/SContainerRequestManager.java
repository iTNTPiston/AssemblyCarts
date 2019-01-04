package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.network.ACNetwork;
import com.tntp.assemblycarts.network.MCGuiRequestManager;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SContainerRequestManager extends SContainer implements IContainerRequestManager {

  public SContainerRequestManager(IInventory playerInventory, int slots, IInventory machine, int playerSlotsX,
      int playerSlotsY) {
    super(playerInventory, slots, machine, playerSlotsX, playerSlotsY);
  }

  @Override
  public void addCraftingToCrafters(ICrafting ic) {
    super.addCraftingToCrafters(ic);
    if (ic instanceof EntityPlayerMP) {
      sendRequestManager((EntityPlayerMP) ic);
    }
  }

  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    if (getContainerRequestManager().update) {
      for (int j = 0; j < this.crafters.size(); ++j) {
        ICrafting ic = (ICrafting) this.crafters.get(j);
        if (ic instanceof EntityPlayerMP) {
          sendRequestManager((EntityPlayerMP) ic);
        }
      }
      getContainerRequestManager().update = false;
    }
  }

  public void sendRequestManager(EntityPlayerMP player) {
    NBTTagCompound tag = new NBTTagCompound();
    getContainerRequestManager().writeToNBT(tag);
    ACNetwork.network.sendTo(new MCGuiRequestManager(this.windowId, tag), player);
  }

}
