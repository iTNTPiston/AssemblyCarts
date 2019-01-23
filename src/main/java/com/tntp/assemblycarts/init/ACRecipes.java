package com.tntp.assemblycarts.init;

import com.tntp.minecraftmodapi.APIiTNTPiston;
import com.tntp.minecraftmodapi.recipe.IRecipeRegisterFactory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ACRecipes {
    public static void registerRecipes() {
        OreDictionary.registerOre("ingotAssemblium", ACItems.ingot_assemblium);
        OreDictionary.registerOre("plateAssemblium", ACItems.plate_assemblium);
        IRecipeRegisterFactory reg = APIiTNTPiston.newRecipeRegister();
        reg.ofCrafting(new ItemStack(ACItems.green_quartz, 4)).pattern("ABA,BCB,ABA").input("dyeGreen", "gemQuartz", "gemEmerald").register();
        reg.ofCrafting(new ItemStack(ACItems.ingot_assemblium, 4)).pattern("ABA,B B,ABA").input(ACItems.green_quartz, "ingotIron").register();
        reg.ofCrafting(ACItems.plate_assemblium).pattern("AA").input("ingotAssemblium").register();
    }
}
