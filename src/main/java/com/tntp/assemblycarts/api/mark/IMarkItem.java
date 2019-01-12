package com.tntp.assemblycarts.api.mark;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IMarkItem {

    public boolean matchesStack(ItemStack stack);

    @SideOnly(Side.CLIENT)
    public ItemStack getDisplayStack();

    @SideOnly(Side.CLIENT)
    default void nextDisplayStack() {

    }

    public void writeToNBT(NBTTagCompound tag);

    public IMarkItem readFromNBT(NBTTagCompound tag);

}
