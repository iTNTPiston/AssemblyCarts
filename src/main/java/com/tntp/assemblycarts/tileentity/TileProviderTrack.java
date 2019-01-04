package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.entity.EntityMinecartAssembly;

import net.minecraft.nbt.NBTTagCompound;

public class TileProviderTrack extends STile {
  private boolean reversed;
  private boolean occupied;
  private EntityMinecartAssembly dockedCart;
  // private boolean hasCartInThisTick;

//  private
//
//  @Override public void updateEntity() {
//    super.updateEntity();
//    if (worldObj != null) {
//      if (hasCartInThisTick) {
//        
//      }
//    }
//  }

  public boolean isReversed() {
    return reversed;
  }

  public void setReversed(boolean reversed) {
    this.reversed = reversed;
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
    reversed = tag.getBoolean("reversed");
    occupied = tag.getBoolean("occupied");
    // hasCartInThisTick = tag.getBoolean("hasCart");
  }

  public EntityMinecartAssembly getDockedCart() {
    return dockedCart;
  }

  public void setDockedCart(EntityMinecartAssembly dockedCart) {
    this.dockedCart = dockedCart;
  }
}
