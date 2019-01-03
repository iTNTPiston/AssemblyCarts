package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.item.ItemAssemblyCart;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.item.Item;

@ObjectHolder("assemblycarts")
public class ACItems {
  public static final Item assembly_cart = null;

  public static void loadItems() {
    regItem(new ItemAssemblyCart(), "assembly_cart");
  }

  private static void regItem(Item item, String name) {
    item.setTextureName(AssemblyCartsMod.MODID + ":" + name);
    item.setCreativeTab(ACCreativeTabs.instance);
    item.setUnlocalizedName(name);
    GameRegistry.registerItem(item, name);
  }
}
