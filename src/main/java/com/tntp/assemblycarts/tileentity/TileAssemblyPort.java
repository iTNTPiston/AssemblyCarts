package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.block.IAssemblyStructure;
import com.tntp.assemblycarts.util.DirUtil;
import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TileAssemblyPort extends STileInventory implements IAssemblyStructure, ISidedInventory {
  private int[] cachedSlots;
  private int iteratingSlot;
  private ItemStack[] markedItemStack;
  public boolean bypassInsertionSide;

  public TileAssemblyPort() {
    super(9);
    iteratingSlot = -1;
    markedItemStack = new ItemStack[9];
    bypassInsertionSide = false;
  }

  public void setMarkedItemStack(int i, ItemStack s) {
    markedItemStack[i] = s;
    markDirty();
  }

  public ItemStack getMarkedItemStack(int i) {
    return markedItemStack[i];
  }

  public ItemStack[] getMarkedItemStacks() {
    return markedItemStack;
  }

  @Override
  public void updateEntity() {
    super.updateEntity();
    if (worldObj != null && !worldObj.isRemote) {
      int outputSide = this.getBlockMetadata();
      int[] off = DirUtil.OFFSETS[outputSide];
      IInventory inv = detectContainer(xCoord + off[0], yCoord + off[1], zCoord + off[2], outputSide ^ 1);
      if (inv != null) {
        nextSlot();
        exportToContainer(inv, outputSide ^ 1);
      }
    }
  }

  private void importFromManager() {
    TileAssemblyManager manager = getManager();
    if (manager == null)
      return;
    bypassInsertionSide = true;
    manager.supplyToPort(this);
    bypassInsertionSide = false;
  }

  private void exportToContainer(IInventory inv, int side) {
    ItemStack stackInSlot = getStackInSlot(iteratingSlot);
    if (stackInSlot == null)
      return;

    ItemStack sup = stackInSlot.splitStack(stackInSlot.stackSize);

    ItemUtil.addToInventory(sup, inv, cachedSlots, side);

    if (sup.stackSize > 0) {// supply failed, add the stack back
      stackInSlot.stackSize++;
    } else {
      // supply succeeded, apply change to inventory
      if (stackInSlot.stackSize == 0)
        stackInSlot = null;
      setInventorySlotContents(iteratingSlot, stackInSlot);
      markDirty();
    }
  }

  private void nextSlot() {
    if (iteratingSlot < 0 || iteratingSlot >= getSizeInventory() - 1)
      iteratingSlot = 0;
    else
      iteratingSlot++;
  }

  /**
   * Detect container. Side is the the side of the CONTAINER
   */
  private IInventory detectContainer(int x, int y, int z, int side) {
    TileEntity t = worldObj.getTileEntity(x, y, z);
    if (t instanceof IInventory) {
      int[] slots;
      if (t instanceof ISidedInventory) {
        slots = ((ISidedInventory) t).getAccessibleSlotsFromSide(side);
        if (slots == null || slots.length == 0)
          return null;
      } else {
        slots = new int[((IInventory) t).getSizeInventory()];
        for (int i = 0; i < slots.length; i++) {
          slots[i] = i;
        }
      }
      cachedSlots = slots;
      return (IInventory) t;
    }
    return null;
  }

  private TileAssemblyManager manager;

  @Override
  public TileAssemblyManager getManager() {
    if (!manager.isValidInWorld())
      manager = null;
    return manager;
  }

  @Override
  public void setManager(TileAssemblyManager tile) {
    manager = tile;
  }

  @Override
  public void writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    tag.setInteger("iterSlot", iteratingSlot);
    NBTTagList markedList = new NBTTagList();
    for (int i = 0; i < markedItemStack.length; i++) {
      if (markedItemStack[i] != null) {
        NBTTagCompound entry = new NBTTagCompound();
        entry.setInteger("slot", i);
        NBTTagCompound stack = new NBTTagCompound();
        markedItemStack[i].writeToNBT(stack);
        entry.setTag("item", stack);
        markedList.appendTag(entry);
      }
    }
    tag.setTag("mark", markedList);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    iteratingSlot = tag.getInteger("iterSlot");
    markedItemStack = new ItemStack[9];
    NBTTagList markedList = tag.getTagList("mark", NBT.TAG_COMPOUND);
    for (int i = 0; i < markedList.tagCount(); i++) {
      NBTTagCompound entry = markedList.getCompoundTagAt(i);
      int slot = entry.getInteger("slot");
      ItemStack item = ItemStack.loadItemStackFromNBT(entry.getCompoundTag("item"));
      markedItemStack[slot] = item;
    }
  }

  @Override
  public boolean isItemValidForSlot(int slot, ItemStack stack) {
    return stack != null && ItemUtil.areItemAndTagEqual(stack, markedItemStack[slot]);
  }

  @Override
  public int[] getAccessibleSlotsFromSide(int side) {
    return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
  }

  @Override
  public boolean canInsertItem(int slot, ItemStack stack, int side) {
    return bypassInsertionSide;
  }

  @Override
  public boolean canExtractItem(int slot, ItemStack stack, int side) {
    return side == getBlockMetadata();
  }

}
