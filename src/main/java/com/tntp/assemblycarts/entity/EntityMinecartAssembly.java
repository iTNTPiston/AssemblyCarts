package com.tntp.assemblycarts.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tntp.assemblycarts.api.IProvider;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.init.ACBlocks;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.tileentity.IRequester;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartAssembly extends EntityMinecartContainer implements IProvider, IRequester {

  private ItemStack target;
  private RequestManager requestManager;

  public EntityMinecartAssembly(World world) {
    super(world);
    requestManager = new RequestManager(this, 0, 26);
  }

  public EntityMinecartAssembly(World world, double x, double y, double z) {
    super(world, x, y, z);
    requestManager = new RequestManager(this, 0, 26);
  }

  public void setTarget(ItemStack stack, List<ItemStack> need) {
    requestManager.initRequestDirectly(stack, need);
    target = stack;
  }

  @Override
  public ItemStack getProviderTarget() {
    return target;
  }

  @Override
  public ItemStack getCartItem() {
    return new ItemStack(ACItems.assembly_cart);
  }

  @Override
  public Block func_145820_n() {
    return Blocks.diamond_ore;
  }

  public void killMinecart(DamageSource p_94095_1_) {
    this.setDead();
    ItemStack cart = new ItemStack(ACItems.assembly_cart, 1);
    if (this.func_95999_t() != null) {
      cart.setStackDisplayName(this.func_95999_t());
    }

    this.entityDropItem(cart, 0.0F);

    for (int i = 0; i < this.getSizeInventory(); ++i) {
      ItemStack itemstack = this.getStackInSlot(i);

      if (itemstack != null) {
        float f = this.rand.nextFloat() * 0.8F + 0.1F;
        float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
        float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

        while (itemstack.stackSize > 0) {
          int j = this.rand.nextInt(21) + 10;

          if (j > itemstack.stackSize) {
            j = itemstack.stackSize;
          }

          itemstack.stackSize -= j;
          EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1,
              this.posZ + (double) f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
          float f3 = 0.05F;
          entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
          entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
          entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);
          this.worldObj.spawnEntityInWorld(entityitem);
        }
      }
    }
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
  public boolean provide(RequestManager rm) {
    for (int i = 0; i < getSizeInventory(); i++) {
      ItemStack stackInSlot = getStackInSlot(i);
      if (stackInSlot != null && rm.isRequesting(stackInSlot)) {
        ItemStack sup = stackInSlot.splitStack(1);
        rm.supply(sup);
        if (sup.stackSize > 0) {// supply failed, add the stack back
          stackInSlot.stackSize++;
        } else {
          // supply succeeded, apply change to inventory
          if (stackInSlot.stackSize == 0)
            stackInSlot = null;
          setInventorySlotContents(i, stackInSlot);
          return true;
        }

      }
    }
    return false;
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound tag) {
    super.writeEntityToNBT(tag);
    requestManager.writeToNBT(tag);
  }

  @Override
  protected void readEntityFromNBT(NBTTagCompound tag) {
    super.readEntityFromNBT(tag);
    requestManager.readFromNBT(tag);
  }

  @Override
  public RequestManager getRequestManager() {
    return requestManager;
  }

}
