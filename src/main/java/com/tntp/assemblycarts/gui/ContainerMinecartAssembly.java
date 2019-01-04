package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.ProvideManager;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;
import com.tntp.assemblycarts.network.ACNetwork;
import com.tntp.assemblycarts.network.MCGuiProvideManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerMinecartAssembly extends SContainerRequestManager implements IProvider {
  private EntityMinecartAssembly cart;

  public ContainerMinecartAssembly(IInventory playerInventory, EntityMinecartAssembly cart) {
    super(playerInventory, cart.getSizeInventory(), cart, 8, 140);
    this.cart = cart;
  }

  public EntityMinecartAssembly getCart() {
    return cart;
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

  @Override
  public void setupMachineSlots(IInventory machine) {
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlotToContainer(new SlotDecorative(machine, i * 9 + j, 8 + j * 18, 90 + i * 18));
      }
    }
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return cart.isUseableByPlayer(player);
  }

  @Override
  public RequestManager getRequestManager() {
    return cart.getRequestManager();
  }

  @Override
  public ProvideManager getProvideManager() {
    return cart.getProvideManager();
  }

}
