package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerProcessBook extends SContainer {
  private AssemblyProcess process;
  private InventoryPlayer playerInv;

  public ContainerProcessBook(IInventory playerInventory, AssemblyProcess p) {
    super(playerInventory, 0, null, 8, 140);
    process = p;
    playerInv = (InventoryPlayer) playerInventory;
  }

  public void setupPlayerInventory(IInventory playerInventory, int playerSlotsX, int playerSlotsY) {
    for (int j = 0; j < 9; j++) {
      Slot s;
      if (j == ((InventoryPlayer) playerInventory).currentItem) {
        s = new SlotDecorative(playerInventory, j, playerSlotsX + j * 18, playerSlotsY + 58);
      } else
        s = new Slot(playerInventory, j, playerSlotsX + j * 18, playerSlotsY + 58);
      this.addSlotToContainer(s);
    }
    for (int k = 0; k < 3; k++) {
      for (int j = 0; j < 9; j++) {
        this.addSlotToContainer(new Slot(playerInventory, j + 9 + k * 9, playerSlotsX + j * 18, playerSlotsY + k * 18));
      }
    }
  }

  protected AssemblyProcess getProcess() {
    return process;
  }

  @Override
  public void setupMachineSlots(IInventory machine) {

  }

  @Override
  public boolean canInteractWith(EntityPlayer p_75145_1_) {
    return true;
  }

  public void processSlotClick(int slotID, int mouseButton) {
    // both server and client
    ItemStack current = playerInv.getItemStack();
    if (slotID == 0) {
      ItemStack main = process.getMainOutput();
      if (main == null || (current != null && !ItemUtil.areItemAndTagEqual(current, main))) {
        process.setMainOutput(current);
      } else {
        if (mouseButton == 0) {
          main.stackSize--;
          if (main.stackSize <= 0)
            process.setMainOutput(null);
        } else if (mouseButton == 1) {
          main.stackSize++;
        } else {
          process.setMainOutput(null);
        }
      }
    } else if (slotID <= 18) {
      slotID--;// match list id
      ItemStack input = process.getInput(slotID);
      if (input == null || (current != null && !ItemUtil.areItemAndTagEqual(current, input))) {
        process.setInput(slotID, current);
      } else {
        if (mouseButton == 0) {
          input.stackSize--;
          if (input.stackSize <= 0)
            process.setInput(slotID, null);
        } else if (mouseButton == 1) {
          input.stackSize++;
        } else {
          process.setInput(slotID, null);
        }
      }
    } else if (slotID <= 36) {
      slotID -= 19;// match list id
      ItemStack output = process.getOtherOutput(slotID);
      if (output == null || (current != null && !ItemUtil.areItemAndTagEqual(current, output))) {
        process.setOtherOutput(slotID, current);
      } else {
        if (mouseButton == 0) {
          output.stackSize--;
          if (output.stackSize <= 0)
            process.setOtherOutput(slotID, null);
        } else if (mouseButton == 1) {
          output.stackSize++;
        } else {
          process.setOtherOutput(slotID, null);
        }
      }
    }
  }

  /**
   * Called when the container is closed.
   */
  @Override
  public void onContainerClosed(EntityPlayer player) {
    ItemStack current = player.inventory.getCurrentItem();
    if (current != null && current.getItem() == ACItems.process_book) {
      ItemProcessBook.writeProcessToStack(current, process);
      player.inventory.setInventorySlotContents(player.inventory.currentItem, current);
    }
    super.onContainerClosed(player);
  }

}
