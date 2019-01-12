package com.tntp.assemblycarts.api.mark;

import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.item.ItemStack;

public class MarkItemStack implements IMarkItem {
    private final ItemStack wrapper;

    public MarkItemStack(ItemStack s) {
        wrapper = s;
    }

    @Override
    public boolean matchesStack(ItemStack stack) {
        return ItemUtil.areItemAndTagEqual(wrapper, stack);
    }

    @Override
    public ItemStack getDisplayStack() {
        return wrapper;
    }

}
