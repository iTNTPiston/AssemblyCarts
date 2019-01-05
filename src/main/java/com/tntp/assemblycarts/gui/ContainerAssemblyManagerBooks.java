package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.item.ItemProcessBook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerAssemblyManagerBooks extends SContainer {
  private IInventory machine;

  public ContainerAssemblyManagerBooks(IInventory playerInventory, IInventory machine) {
    super(playerInventory, 27, machine, 8, 86);
    this.machine = machine;
  }

  @Override
  public void setupMachineSlots(IInventory machine) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        Slot bookSlot = new Slot(machine, 27 + i * 9 + j, 8 + j * 18, 18 + i * 18);
        bookSlot.setBackgroundIcon(ItemProcessBook.getEmptySlotIcon());
        this.addSlotToContainer(bookSlot);
      }
    }
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return machine.isUseableByPlayer(player);
  }

}
