package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerMinecartAssembly extends SContainerRequestManager {
  private EntityMinecartAssembly cart;

  public ContainerMinecartAssembly(IInventory playerInventory, EntityMinecartAssembly cart) {
    super(playerInventory, cart.getSizeInventory(), cart, 8, 140);
    this.cart = cart;
  }

  public EntityMinecartAssembly getCart() {
    return cart;
  }

  @Override
  public void setupMachineSlots(IInventory machine) {
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 9; j++) {
        this.addSlotToContainer(new Slot(machine, i * 9 + j, 8 + j * 18, 90 + i * 18));
      }
    }
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return cart.isUseableByPlayer(player);
  }

  @Override
  public RequestManager getContainerRequestManager() {
    return cart.getRequestManager();
  }

}
