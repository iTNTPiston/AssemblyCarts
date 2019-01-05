package com.tntp.assemblycarts.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.api.IRequester;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.block.IAssemblyStructure;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileAssemblyManager extends STileInventory implements IRequester {
  private RequestManager requestManager;
  private boolean formed;
  private boolean bypassInsertionCheck;
  private List<IAssemblyStructure> cachedStructure;

  public TileAssemblyManager() {
    super(54);// 0-26 for process 27-53 for storage
    requestManager = new RequestManager(this, 27, 53);
    bypassInsertionCheck = false;
    cachedStructure = new ArrayList<IAssemblyStructure>();
  }

  public void updateEntity() {
    if (worldObj != null && !worldObj.isRemote) {
      if (!formed) {
        setFormed(detectStructure());
      }
    }
  }

  public boolean detectStructure() {
    cachedStructure.clear();
    if (this.isInvalid())
      return false;
    int minY = yCoord;
    int maxY = yCoord;
    int minX = xCoord;
    int maxX = xCoord;
    int minZ = zCoord;
    int maxZ = zCoord;

    while (worldObj.getTileEntity(xCoord, minY - 1, zCoord) instanceof IAssemblyStructure)
      minY--;
    while (worldObj.getTileEntity(xCoord, maxY + 1, zCoord) instanceof IAssemblyStructure)
      maxY++;
    if (maxY - minY > 4)
      return false;
    while (worldObj.getTileEntity(minX - 1, yCoord, zCoord) instanceof IAssemblyStructure)
      minX--;
    while (worldObj.getTileEntity(maxX + 1, yCoord, zCoord) instanceof IAssemblyStructure)
      maxX++;
    if (maxX - minX > 4)
      return false;
    while (worldObj.getTileEntity(xCoord, yCoord, minZ - 1) instanceof IAssemblyStructure)
      minZ--;
    while (worldObj.getTileEntity(xCoord, yCoord, maxZ + 1) instanceof IAssemblyStructure)
      maxZ++;
    if (maxZ - minZ > 4)
      return false;
    for (int xx = minX; xx <= maxX; xx++) {
      for (int yy = minY; yy <= maxY; yy++) {
        for (int zz = minZ; zz <= maxZ; zz++) {
          TileEntity tile = worldObj.getTileEntity(xx, yy, zz);
          if (tile == this)
            continue;

          if (!(tile instanceof IAssemblyStructure))
            return false;
          else {
            ((IAssemblyStructure) tile).setManager(this);
            cachedStructure.add((IAssemblyStructure) tile);
          }
        }
      }
    }
    return true;

  }

  public void setFormed(boolean f) {
    if (formed != f) {
      worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, f ? 8 : 0, 3);
      worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
      formed = f;
    }
  }

  public boolean isFormed() {
    return formed;
  }

  public void startProcess(int processBookSlotID, int multiplier) {
    ItemStack book = getStackInSlot(processBookSlotID);
    if (book != null && book.getItem() == ACItems.process_book) {
      if (ItemProcessBook.hasProcess(book)) {
        AssemblyProcess process = ItemProcessBook.getProcessFromStack(book);
        if (process.getMainOutput() != null) {
          initRequest(process, multiplier);
          markDirty();
        }
      }
    }
  }

  private void initRequest(AssemblyProcess p, int multiplier) {
    if (detectStructure()) {
      requestManager.initRequestWithProcess(p, multiplier);

    }
  }

  @Override
  public RequestManager getRequestManager() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isItemValidForSlot(int slot, ItemStack stack) {
    return bypassInsertionCheck;
  }

  public void supplyToPort(TileAssemblyPort port) {
    for (int i = 27; i < getSizeInventory(); i++) {
      ItemStack takenOut = getStackInSlot(i);
      ItemUtil.addToInventory(takenOut, port, 0, port.getSizeInventory(), -1);
      if (takenOut.stackSize <= 0)
        takenOut = null;
      setInventorySlotContents(i, takenOut);
    }
  }

}
