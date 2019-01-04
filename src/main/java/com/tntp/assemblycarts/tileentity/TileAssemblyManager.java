package com.tntp.assemblycarts.tileentity;

import java.util.List;

import com.tntp.assemblycarts.block.IAssemblyStructure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileAssemblyManager extends STileInventory {
  private Process activeProcess;
  private List<ItemStack> activeRequesting;
  private boolean formed;

  public TileAssemblyManager() {
    super(73);// 0-8 for process 9-72 for storage
  }

  public void updateEntity() {
    if (worldObj != null && !worldObj.isRemote) {
      if (!formed) {
        setFormed(detectStructure());
      }
    }
  }

  public boolean detectStructure() {
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
          else
            ((IAssemblyStructure) tile).setManager(this);
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

  public void startProcess() {

  }

}
