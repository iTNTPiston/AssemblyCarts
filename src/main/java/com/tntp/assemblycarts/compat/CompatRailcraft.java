package com.tntp.assemblycarts.compat;

import com.tntp.assemblycarts.init.ACItems;
import com.tntp.minecraftmodapi.compat.ICompat;

import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.ItemStack;

public class CompatRailcraft implements ICompat {

    @Override
    public void loadClient() {
    }

    @Override
    public void loadCommon() {
        // Load rolling machine recipes
        RailcraftCraftingManager.rollingMachine.addRecipe(new ItemStack(ACItems.plate_assemblium, 4), "AA", "AA", 'A', ACItems.ingot_assemblium);
    }

}
