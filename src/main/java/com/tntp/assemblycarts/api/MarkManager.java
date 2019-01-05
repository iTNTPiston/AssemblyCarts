package com.tntp.assemblycarts.api;

import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class MarkManager {
  private ItemStack[] markedItems;

  public MarkManager(int markSize) {
    markedItems = new ItemStack[markSize];
  }

  /**
   * Test if anything is marked at all
   * 
   * @return true if at least 1 itemstack in the mark contents is not null
   */
  public boolean hasMark() {
    for (ItemStack s : markedItems) {
      if (s != null)
        return true;
    }
    return false;
  }

  public boolean isMarked(ItemStack stack) {
    for (int i = 0; i < markedItems.length; i++) {
      if (ItemUtil.areItemAndTagEqual(stack, markedItems[i]))
        return true;
    }
    return false;
  }

  public int getSizeMark() {
    return markedItems.length;
  }

  public ItemStack getMarkedItem(int i) {
    return markedItems[i];
  }

  public ItemStack[] getAllMarked() {
    return markedItems;
  }

  public void setMarkedItem(int i, ItemStack s) {
    markedItems[i] = s;
  }

  public void writeToNBT(NBTTagCompound tag) {
    NBTTagList markedList = new NBTTagList();
    for (int i = 0; i < markedItems.length; i++) {
      if (markedItems[i] != null) {
        NBTTagCompound entry = new NBTTagCompound();
        entry.setInteger("slot", i);
        NBTTagCompound stack = new NBTTagCompound();
        markedItems[i].writeToNBT(stack);
        entry.setTag("item", stack);
        markedList.appendTag(entry);
      }
    }
    tag.setTag("markedItems", markedList);
  }

  public void readFromNBT(NBTTagCompound tag) {
    for (int i = 0; i < markedItems.length; i++) {
      markedItems[i] = null;
    }
    NBTTagList markedList = tag.getTagList("markedItems", NBT.TAG_COMPOUND);
    for (int i = 0; i < markedList.tagCount(); i++) {
      NBTTagCompound entry = markedList.getCompoundTagAt(i);
      int slot = entry.getInteger("slot");
      if (slot >= 0 && slot < markedItems.length) {
        ItemStack item = ItemStack.loadItemStackFromNBT(entry.getCompoundTag("item"));
        markedItems[slot] = item;
      }
    }
  }
}
