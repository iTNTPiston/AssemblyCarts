package com.tntp.assemblycarts.tileentity;

import net.minecraft.tileentity.TileEntity;

/**
 * Super class of (hopefully) all tileentities
 * 
 * @author iTNTPiston
 *
 */
public class STile extends TileEntity {
  protected static final int EVENT_TRACK_REVERSE = 0;

  public boolean isValidInWorld() {
    return this.hasWorldObj() && this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
  }

  /**
   * Called by breakBlock() before items are dropped, useful for setting
   * decorative slots to null
   */
  public void onBreakingContainer() {

  }
}
