package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.ProvideManager;
import com.tntp.assemblycarts.block.BlockProviderTrack;
import com.tntp.assemblycarts.block.IAssemblyStructure;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;
import com.tntp.assemblycarts.init.ACBlocks;
import com.tntp.assemblycarts.util.DirUtil;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileAssemblyProvider extends STileInventory implements IProvider, IAssemblyStructure {
  private ProvideManager provideManager;
  private int trackPowerTimeLeft;

  public TileAssemblyProvider() {
    super(0);
    provideManager = new ProvideManager(this, null);
  }

  @Override
  public void updateEntity() {
    super.updateEntity();
    if (worldObj != null && !worldObj.isRemote) {
      updateCart();
    }
  }

  private void updateCart() {
    if (trackPowerTimeLeft > 0) {
      trackPowerTimeLeft--;
      if (trackPowerTimeLeft == 0) {
        setTrackPowered(false);
      }
    } else {
      EntityMinecartAssembly cart = getDockedCart();
      if (cart != null) {
        if (cart.getRequestManager().isFulfilled()) {
          // Cart is ready to go
          powerTrack(30);
        } else {
          if (!provideToCart(cart)) {
            powerTrack(30);
          }
        }
      }
    }
  }

  /**
   * Provide to the cart
   * 
   * @param cart
   * @return true if the provide is successful
   */
  public boolean provideToCart(EntityMinecartAssembly cart) {
    provideManager.setProvideTarget(null);
    for (int dir : DirUtil.ALL_DIR) {
      if (dir != DirUtil.DOWN_MY) {
        int[] off = DirUtil.OFFSETS[dir ^ 1];
        if (detectContainer(xCoord + off[0], yCoord + off[1], zCoord + off[2], dir)) {
          if (provideManager.tryProvide(cart.getRequestManager(), dir))
            return true;
        }
      }
    }
    return false;

  }

  /**
   * Detect container. Side is the the side of the CONTAINER
   */
  public boolean detectContainer(int x, int y, int z, int side) {
    TileEntity t = worldObj.getTileEntity(x, y, z);
    if (t instanceof IInventory) {
      int[] slots;
      if (t instanceof ISidedInventory) {
        slots = ((ISidedInventory) t).getAccessibleSlotsFromSide(side);
      } else {
        slots = new int[((IInventory) t).getSizeInventory()];
        for (int i = 0; i < slots.length; i++) {
          slots[i] = i;
        }
      }
      provideManager.setProvidingInventory((IInventory) t);
      provideManager.setProvidingSlots(slots);
      return true;
    }
    return false;
  }

  @Override
  public ProvideManager getProvideManager() {
    return provideManager;
  }

  @Override
  public void writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    provideManager.writeToNBT(tag);
    tag.setInteger("trackPowerTimeLeft", trackPowerTimeLeft);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    provideManager.readFromNBT(tag);
    trackPowerTimeLeft = tag.getInteger("trackPowerTimeLeft");
  }

  private void powerTrack(int time) {
    trackPowerTimeLeft = time;
    setTrackPowered(true);
  }

  public void setTrackPowered(boolean powered) {
    Block b = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
    if (b == ACBlocks.provider_track) {
      BlockProviderTrack.setPowered(worldObj, xCoord, yCoord + 1, zCoord, powered);
    }
  }

  public EntityMinecartAssembly getDockedCart() {
    TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
    if (tile instanceof TileProviderTrack) {
      return ((TileProviderTrack) tile).getDockedCart();
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

}
