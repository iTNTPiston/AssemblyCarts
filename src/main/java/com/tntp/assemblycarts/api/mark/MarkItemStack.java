package com.tntp.assemblycarts.api.mark;

import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MarkItemStack implements IMarkItem {
    private final ItemStack wrapped;

    public MarkItemStack(ItemStack s) {
        wrapped = s;
    }

    public MarkItemStack() {
        this(null);
    }

    @Override
    public boolean matchesStack(ItemStack stack) {
        return ItemUtil.areItemAndTagEqual(wrapped, stack);
    }

    @Override
    public ItemStack getDisplayStack() {
        return wrapped;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        if (wrapped != null)
            wrapped.writeToNBT(tag);
    }

    @Override
    public IMarkItem readFromNBT(NBTTagCompound tag) {
        return new MarkItemStack(ItemStack.loadItemStackFromNBT(tag));
    }

}
