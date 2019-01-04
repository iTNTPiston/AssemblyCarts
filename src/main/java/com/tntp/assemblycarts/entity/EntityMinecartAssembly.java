package com.tntp.assemblycarts.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartAssembly extends EntityMinecartContainer {

  private ItemStack crafting;
  private List<ItemStack> requesting = new ArrayList<ItemStack>();

  public EntityMinecartAssembly(World world) {
    super(world);
  }

  public EntityMinecartAssembly(World world, double x, double y, double z) {
    super(world, x, y, z);
  }

  public void setCrafting(ItemStack stack) {
    crafting = stack;
  }

  public ItemStack getCrafting() {
    return crafting;
  }

  public List<ItemStack> getRequesting() {
    return requesting;
  }

  public boolean isRequesting() {
    return !requesting.isEmpty();
  }

  /**
   * Called by loader
   * 
   * @param stack
   */
  public ItemStack loadItemStack(ItemStack stack) {
    for (Iterator<ItemStack> iter = requesting.iterator(); iter.hasNext();) {
      ItemStack request = iter.next();
      if (ItemUtil.areItemAndTagEqual(request, stack)) {
        int needed = request.stackSize;
        int provided = stack.stackSize;
        ItemStack leftOver;
        if (needed >= provided) {
          leftOver = stack.splitStack(0);
        } else {
          leftOver = stack.splitStack(provided - needed);
        }
        int putIn = stack.stackSize;
        ItemStack leftOverAfterAdd = addToInventory(stack);
        int sizeAfterAdd = leftOverAfterAdd == null ? 0 : leftOverAfterAdd.stackSize;
        int got = putIn - sizeAfterAdd;
        request.stackSize -= got;
        if (request.stackSize <= 0) {
          iter.remove();
        }
        if (sizeAfterAdd <= 0)
          return leftOver.stackSize <= 0 ? null : leftOver;
        leftOver.stackSize += sizeAfterAdd;
        return leftOver;
      }
    }
    return stack;
  }

  private ItemStack addToInventory(ItemStack stack) {
    for (int i = 0; i < this.getSizeInventory(); i++) {
      ItemStack slot = this.getStackInSlot(i);
      int max = Math.min(this.getInventoryStackLimit(), stack.getMaxStackSize());
      if (slot == null) {
        if (stack.stackSize <= max) {
          this.setInventorySlotContents(i, stack);
          return null;
        } else {
          this.setInventorySlotContents(i, stack.splitStack(max));
        }
      } else {
        if (ItemUtil.areItemAndTagEqual(stack, slot)) {
          int putIn = slot.stackSize + stack.stackSize <= max ? stack.stackSize : max - slot.stackSize;
          stack.splitStack(putIn);
          slot.stackSize += putIn;
        }
      }
      if (stack.stackSize == 0)
        return null;
    }
    if (stack.stackSize == 0)
      return null;
    return stack;
  }

  @Override
  public AxisAlignedBB getCollisionBox(Entity entity) {
    return super.getCollisionBox(entity);
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
    return 27;
  }

  @Override
  public int getMinecartType() {
    return -1;
  }

}
