package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.api.IRequester;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.block.BlockProviderTrack;
import com.tntp.assemblycarts.block.IAssemblyStructure;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;
import com.tntp.assemblycarts.init.ACBlocks;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileAssemblyRequester extends STileInventory implements IAssemblyStructure, IRequester {

  private int trackPowerTimeLeft;

  public TileAssemblyRequester() {
    super(19);// 0 for book
    requestManager = new RequestManager(this, 1, 18);
  }

  private void updateBook() {

    if (getManager() == null && requestManager.getCraftingTarget() == null) {
      ItemStack stack = getStackInSlot(0);
      if (stack != null && stack.getItem() == ACItems.process_book) {
        AssemblyProcess process = ItemProcessBook.getProcessFromStack(stack);
        if (process.getMainOutput() != null) {
          requestManager.initRequestWithProcess(process, 1);
          markDirty();
        }
      }
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
        if (requestManager.isFulfilled()) {
          System.out.println("Fulfilled! let go");
          // Let all passed carts go in this case
          powerTrack(30);
        } else if (cart.getProvideManager().canProvideTo(requestManager)) {
          if (cart.getProvideManager().getProvideTarget() == null) {
            System.out.println("Set Target");
            // If the cart doesn't have a target, set the target
            cart.setTarget(requestManager.getCraftingTarget().copy(), requestManager.getNeed());
          } else if (!cart.getProvideManager().tryProvide(requestManager, -1)) {
            // Let the cart provide. If cannot provide then let the cart go.
            System.out.println("Nothing to provide, let go");
            powerTrack(30);
          }
        }
      }
    }
  }

  @Override
  public void updateEntity() {
    super.updateEntity();
    if (worldObj != null && !worldObj.isRemote) {

      updateBook();
      updateCart();

    }
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
    return manager;
  }

  @Override
  public void setManager(TileAssemblyManager tile) {
    manager = tile;
  }

  private RequestManager requestManager;

  @Override
  public RequestManager getRequestManager() {
    return requestManager;
  }

  @Override
  public void writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    requestManager.writeToNBT(tag);
    tag.setInteger("trackPowerTimeLeft", trackPowerTimeLeft);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    requestManager.readFromNBT(tag);
    trackPowerTimeLeft = tag.getInteger("trackPowerTimeLeft");
  }

}
