package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.entity.EntityMinecartAssembly;

import net.minecraft.nbt.NBTTagCompound;

public class TileProviderTrack extends STile {
  private boolean reversed;
  private boolean occupied;
  private EntityMinecartAssembly dockedCart;

  public TileProviderTrack() {
  }

  @Override
  public void updateEntity() {
    if (dockedCart != null && dockedCart.isDead) {
      dockedCart = null;
    }
  }

  public boolean isReversed() {
    return reversed;
  }

  public void setReversed(boolean reversed) {
    this.reversed = reversed;
    if (worldObj != null && !worldObj.isRemote)
      this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), EVENT_TRACK_REVERSE, reversed ? 1 : 0);
  }

  public boolean isOccupied() {
    return occupied;
  }

  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }

  public void writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    tag.setBoolean("reversed", reversed);
    tag.setBoolean("occupied", occupied);
    // tag.setBoolean("hasCart", hasCartInThisTick);
  }

  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    setReversed(tag.getBoolean("reversed"));
    occupied = tag.getBoolean("occupied");
    // hasCartInThisTick = tag.getBoolean("hasCart");
  }

  public EntityMinecartAssembly getDockedCart() {
    return dockedCart;
  }

  public void setDockedCart(EntityMinecartAssembly dockedCart) {
    this.dockedCart = dockedCart;
  }

  @Override
  public boolean receiveClientEvent(int event, int param) {
    if (super.receiveClientEvent(event, param))
      return true;
    if (event == EVENT_TRACK_REVERSE) {
      reversed = param == 1;
      worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
      return true;
    }
    return false;
  }
}
