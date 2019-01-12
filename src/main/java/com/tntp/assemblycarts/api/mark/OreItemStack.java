package com.tntp.assemblycarts.api.mark;

import java.util.List;

import com.tntp.assemblycarts.util.ItemUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreItemStack implements IMarkItem {
    private final String oreDictEntry;
    @SideOnly(Side.CLIENT)
    private int displayIndex;
    @SideOnly(Side.CLIENT)
    private ItemStack[] cachedDisplayStack;

    public OreItemStack(String ore) {
        oreDictEntry = ore;
    }

    public OreItemStack() {
        this(null);
    }

    @Override
    public boolean matchesStack(ItemStack stack) {
        return ItemUtil.isItemInOreDict(stack, oreDictEntry);
    }

    @Override
    public ItemStack getDisplayStack() {
        if (cachedDisplayStack == null) {
            List<ItemStack> ores = OreDictionary.getOres(oreDictEntry);
            cachedDisplayStack = ores.toArray(new ItemStack[ores.size()]);
            displayIndex = 0;
        }
        if (cachedDisplayStack.length == 0)
            return null;
        if (displayIndex < 0 || displayIndex >= cachedDisplayStack.length)
            displayIndex = 0;

        return cachedDisplayStack[displayIndex];
    }

    @SideOnly(Side.CLIENT)
    public void nextDisplayStack() {
        if (cachedDisplayStack != null && cachedDisplayStack.length > 0) {
            if (displayIndex >= cachedDisplayStack.length) {
                displayIndex = 0;
            } else {
                displayIndex++;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("ore", oreDictEntry);
    }

    @Override
    public IMarkItem readFromNBT(NBTTagCompound tag) {
        return new OreItemStack(tag.getString("ore"));
    }

}
