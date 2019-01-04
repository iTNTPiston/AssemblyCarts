package com.tntp.assemblycarts.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemUtil {
  public static int VERSION = 1;

  /**
   * See if the two ItemStack are the same except for the stacksize
   * 
   * @param stack1
   * @param stack2
   * @return
   */
  public static boolean areItemAndTagEqual(ItemStack stack1, ItemStack stack2) {
    if (stack1 == null && stack2 == null)
      return true;
    if (ItemStack.areItemStackTagsEqual(stack1, stack2)) {
      return stack1.isItemEqual(stack2);
    }
    return false;
  }

  /**
   * Add the stack to the inventory. The stacksize of the stack will decrease when
   * items are put into the inventory
   * 
   * @param stackToPut
   * @param inventory  The inventory to put the stack
   * @param startSlot  The start slot, inclusive
   * @param endSlot    The end slot, inclusive
   */
  public static void addToInventory(ItemStack stackToPut, IInventory inventory, int startSlot, int endSlot) {
    if (endSlot >= inventory.getSizeInventory())
      endSlot = inventory.getSizeInventory() - 1;
    boolean inventoryChanged = false;
    for (int i = startSlot; i <= endSlot && stackToPut.stackSize > 0; i++) {
      ItemStack stackInInventory = inventory.getStackInSlot(i);
      int maxStackSize = Math.min(inventory.getInventoryStackLimit(), stackToPut.getMaxStackSize());
      if (stackInInventory == null) {
        // Can directly putin the stack
        int putInSize;
        if (stackToPut.stackSize <= maxStackSize) {
          // put the whole stack
          putInSize = stackToPut.stackSize;
        } else {
          // put in max
          putInSize = maxStackSize;
        }
        inventory.setInventorySlotContents(i, stackToPut.splitStack(putInSize));
        inventoryChanged = true;
      } else {
        // There is already a stack in the slot
        if (ItemUtil.areItemAndTagEqual(stackToPut, stackInInventory)) {
          // If the the stack in slot is equal to stack to put
          int putInSize;
          if (stackInInventory.stackSize + stackToPut.stackSize <= maxStackSize) {
            // Can putin the whole stack
            putInSize = stackToPut.stackSize;
            stackToPut.stackSize = 0;
          } else {
            // put in to max
            putInSize = maxStackSize - stackInInventory.stackSize;
            stackToPut.stackSize -= putInSize;
          }
          stackToPut.splitStack(putInSize);
          stackInInventory.stackSize += putInSize;
          inventoryChanged = true;
        }
      }
    }
    if (inventoryChanged)
      inventory.markDirty();
  }

  public static String toTwoDigitStackSizeDisplay(int stacksize) {
    if (stacksize == 0)
      return EnumChatFormatting.GRAY + "" + stacksize;
    if (stacksize < 100)
      return String.valueOf(stacksize);
    if (stacksize < 1000)
      return stacksize / 100 + "!";
    if (stacksize < 10000)
      return EnumChatFormatting.YELLOW + "" + stacksize / 1000 + "K";
    if (stacksize < 100000)
      return EnumChatFormatting.GREEN + "" + stacksize / 10000 + "W";
    if (stacksize < 1000000)
      return EnumChatFormatting.AQUA + "" + stacksize / 100000 + "L";
    if (stacksize < 10000000)
      return EnumChatFormatting.LIGHT_PURPLE + "" + stacksize / 1000000 + "M";
    if (stacksize < 100000000)
      return EnumChatFormatting.LIGHT_PURPLE + "" + stacksize / 10000000 + "U";
    if (stacksize < 1000000000)
      return EnumChatFormatting.LIGHT_PURPLE + "" + stacksize / 100000000 + "V";
    return EnumChatFormatting.LIGHT_PURPLE + "?G";
  }
}
