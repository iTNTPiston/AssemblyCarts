package com.tntp.assemblycarts.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ACCreativeTabs extends CreativeTabs {
  public static final ACCreativeTabs instance = new ACCreativeTabs();

  public ACCreativeTabs() {
    super("assemblycarts");
  }

  @Override
  public Item getTabIconItem() {
    return Item.getItemFromBlock(ACBlocks.assembly_manager);
  }

}
