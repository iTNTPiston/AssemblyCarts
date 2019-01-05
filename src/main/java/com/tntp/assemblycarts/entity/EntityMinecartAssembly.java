package com.tntp.assemblycarts.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.IRequester;
import com.tntp.assemblycarts.api.ProvideManager;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.init.ACBlocks;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartAssembly extends EntityMinecartContainer implements IProvider, IRequester {

  private RequestManager requestManager;
  private ProvideManager provideManager;

  public EntityMinecartAssembly(World world) {
    super(world);
    initManagers();
  }

  public EntityMinecartAssembly(World world, double x, double y, double z) {
    super(world, x, y, z);
    initManagers();
  }

  private void initManagers() {
    requestManager = new RequestManager(this, 0, 17);
    int[] slots = new int[18];
    for (int i = 0; i < slots.length; i++)
      slots[i] = i;
    provideManager = new ProvideManager(this, slots);
  }

  public void setTarget(ItemStack stack, List<ItemStack> need) {
    requestManager.initRequestDirectly(stack, need);
    provideManager.setProvideTarget(stack);
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    if (worldObj != null && !worldObj.isRemote) {
      if (requestManager.isFulfilled()) {
        boolean isEmpty = true;
        for (int i = 0; i < getSizeInventory(); i++) {
          if (getStackInSlot(i) != null) {
            isEmpty = false;
            break;
          }
        }
        if (isEmpty) {
          provideManager.setProvideTarget(null);
        }
      }

    }
  }

  @Override
  public Block func_145817_o() {
    return Blocks.diamond_ore;
  }

  @Override
  public void killMinecart(DamageSource p_94095_1_) {
    if (!worldObj.isRemote)
      super.killMinecart(p_94095_1_);
  }

  @Override
  public void setDead() {
    this.isDead = true;
  }

  @Override
  public int getSizeInventory() {
    return 18;
  }

  @Override
  public int getMinecartType() {
    return -1;
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound tag) {
    super.writeEntityToNBT(tag);
    requestManager.writeToNBT(tag);
    provideManager.writeToNBT(tag);
  }

  @Override
  protected void readEntityFromNBT(NBTTagCompound tag) {
    super.readEntityFromNBT(tag);
    requestManager.readFromNBT(tag);
    provideManager.readFromNBT(tag);
  }

  @Override
  public RequestManager getRequestManager() {
    return requestManager;
  }

  @Override
  public ProvideManager getProvideManager() {
    return provideManager;
  }

}
