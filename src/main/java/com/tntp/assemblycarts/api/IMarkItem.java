package com.tntp.assemblycarts.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public interface IMarkItem {
    public boolean matchesStack(ItemStack stack);

    @SideOnly(Side.CLIENT)
    public ItemStack getDisplayStack();
}
