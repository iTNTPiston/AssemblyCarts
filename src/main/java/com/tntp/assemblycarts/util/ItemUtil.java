package com.tntp.assemblycarts.util;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

    public static boolean isItemInOreDict(ItemStack stack, String oreDictEntry) {
        List<ItemStack> ores = OreDictionary.getOres(oreDictEntry);
        for (ItemStack ore : ores) {
            if (OreDictionary.itemMatches(ore, stack, false))
                return true;
        }
        return false;
    }

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

    public static boolean addToInventory(ItemStack stackToPut, IInventory inventory, int start, int end, int side) {
        int[] slots = new int[end - start + 1];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i + start;
        }
        return addToInventory(stackToPut, inventory, slots, -1);
    }

    /**
     * Add the stack to the inventory. The stacksize of the stack will decrease when
     * items are put into the inventory
     * 
     * @param stackToPut
     * @param inventory  The inventory to put the stack
     * @param slots      An array containing the slots to put the stacks
     */
    public static boolean addToInventory(ItemStack stackToPut, IInventory inventory, int[] slots, int side) {
        boolean inventoryChanged = false;
        boolean sided = side >= 0 && side < 6 && inventory instanceof ISidedInventory;
        for (int slotsID = 0; slotsID < slots.length && stackToPut.stackSize > 0; slotsID++) {
            int i = slots[slotsID];
            if (i < 0 || i >= inventory.getSizeInventory())
                continue;

            if (sided) {
                if (!((ISidedInventory) inventory).canInsertItem(i, stackToPut, side))
                    continue;
            }

            ItemStack stackInInventory = inventory.getStackInSlot(i);
            int maxStackSize = Math.min(inventory.getInventoryStackLimit(), stackToPut.getMaxStackSize());
            if (stackInInventory == null) {
                if (inventory.isItemValidForSlot(i, stackToPut)) {
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
                }
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
                    // stackToPut.splitStack(putInSize);
                    stackInInventory.stackSize += putInSize;
                    inventoryChanged = true;
                }
            }
        }
        if (inventoryChanged)
            inventory.markDirty();
        return inventoryChanged;
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
