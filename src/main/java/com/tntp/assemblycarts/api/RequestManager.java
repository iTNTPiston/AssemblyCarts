package com.tntp.assemblycarts.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class RequestManager {
  public boolean update;
  private ItemStack target;
  private ArrayList<ItemStack> need;
  private IInventory requestingInventory;
  private int startSlot;
  private int endSlot;

  public RequestManager(IInventory inv, int startSlot, int endSlot) {
    requestingInventory = inv;
    setStartSlot(startSlot);
    setEndSlot(endSlot);
    need = new ArrayList<ItemStack>();
  }

  public void initRequestWithProcess(AssemblyProcess proc, int multiplier) {
    need.clear();
    for (Iterator<ItemStack> iter = proc.inputIterator(); iter.hasNext();) {
      ItemStack input = iter.next();
      ItemStack clone = input.copy();
      clone.stackSize *= multiplier;
      need.add(clone);
    }
    if (!need.isEmpty()) {
      target = proc.getMainOutput().copy();
      target.stackSize *= multiplier;
    }
    update = true;
  }

  public void initRequestDirectly(ItemStack tar, List<ItemStack> request) {
    need.clear();
    need.addAll(request);
    target = tar;
    update = true;
  }

  public void setStartSlot(int s) {
    startSlot = s;
  }

  public void setEndSlot(int s) {
    endSlot = s;
  }

  public boolean isFulfilled() {
    return need.isEmpty() || target == null;
  }

  public ItemStack getCraftingTarget() {
    return target;
  }

  public List<ItemStack> getNeed() {
    return Collections.unmodifiableList(need);
  }

  /**
   * Test if the requester wants this stack
   * 
   * @param stack
   * @return
   */
  public boolean isRequesting(ItemStack stack) {
    for (ItemStack is : need) {
      if (ItemUtil.areItemAndTagEqual(is, stack))
        return true;
    }
    return false;
  }

  /**
   * Actually give the stack to the requester. The stacksize should decrease as
   * the requester takes the items
   * 
   * @param stack
   */
  public void supply(ItemStack stack) {
    int stackSizeBefore = stack.stackSize;
    ItemUtil.addToInventory(stack, requestingInventory, startSlot, endSlot);

    int stackSizeChange = stack.stackSize - stackSizeBefore;// change is negative
    if (stackSizeChange < 0) {

      for (Iterator<ItemStack> iter = need.iterator(); iter.hasNext();) {
        ItemStack needStack = iter.next();
        if (ItemUtil.areItemAndTagEqual(needStack, stack)) {
          needStack.stackSize += stackSizeChange;
          update = true;
          if (needStack.stackSize <= 0) {
            iter.remove();// remove the need;
          }
        }
      }
      if (need.isEmpty()) {
        target = null;
      }
    }

  }

  public void readFromNBT(NBTTagCompound tag) {
    target = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("target"));

    NBTTagList needList = tag.getTagList("need", NBT.TAG_COMPOUND);
    need.clear();
    for (int i = 0; i < needList.tagCount(); i++) {
      ItemStack s = ItemStack.loadItemStackFromNBT(needList.getCompoundTagAt(i));
      if (s != null)
        need.add(s);
    }

    startSlot = tag.getInteger("startSlot");
    endSlot = tag.getInteger("endSlot");
  }

  public void writeToNBT(NBTTagCompound tag) {

    if (target != null) {
      NBTTagCompound targetTag = new NBTTagCompound();
      target.writeToNBT(targetTag);
      tag.setTag("target", targetTag);
    }

    NBTTagList needList = new NBTTagList();
    for (ItemStack needStack : need) {
      if (needStack.stackSize > 0) {
        NBTTagCompound needTag = new NBTTagCompound();
        needStack.writeToNBT(needTag);
        needList.appendTag(needTag);
      }
    }
    tag.setTag("need", needList);

    tag.setInteger("startSlot", startSlot);
    tag.setInteger("endSlot", endSlot);
  }
}
