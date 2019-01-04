package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.entity.EntityMinecartAssembly;

import net.minecraft.nbt.NBTTagCompound;

public class TileProviderTrack extends STile {
  private boolean reversed;
  private boolean occupied;
  private EntityMinecartAssembly dockedCart;

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
  }

  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    reversed = tag.getBoolean("reversed");
    occupied = tag.getBoolean("occupied");
  }

  public EntityMinecartAssembly getDockedCart() {
    return dockedCart;
  }

  public void setDockedCart(EntityMinecartAssembly dockedCart) {
    this.dockedCart = dockedCart;
  }
}
