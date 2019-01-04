package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.ProvideManager;
import com.tntp.assemblycarts.network.ACNetwork;
import com.tntp.assemblycarts.network.MCGuiProvideManager;
import com.tntp.assemblycarts.network.MCGuiRequestManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SContainerProvideManager extends SContainer implements IProvider {

  public SContainerProvideManager(IInventory playerInventory, int slots, IInventory machine, int playerSlotsX,
      int playerSlotsY) {
    super(playerInventory, slots, machine, playerSlotsX, playerSlotsY);
    // TODO Auto-generated constructor stub
  }

  public void sendProvideManager(EntityPlayerMP player) {
    NBTTagCompound tag = new NBTTagCompound();
    getProvideManager().writeToNBT(tag);
    ACNetwork.network.sendTo(new MCGuiProvideManager(this.windowId, tag), player);
  }

  @Override
  public void addCraftingToCrafters(ICrafting ic) {
    super.addCraftingToCrafters(ic);
    if (ic instanceof EntityPlayerMP) {
      sendProvideManager((EntityPlayerMP) ic);
    }
  }

  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    if (getProvideManager().update) {
      for (int j = 0; j < this.crafters.size(); ++j) {
        ICrafting ic = (ICrafting) this.crafters.get(j);
        if (ic instanceof EntityPlayerMP) {
          sendProvideManager((EntityPlayerMP) ic);
        }
      }
      getProvideManager().update = false;
    }
  }

}
