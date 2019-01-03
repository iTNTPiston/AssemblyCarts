package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.block.BlockAssemblyManager;
import com.tntp.assemblycarts.core.AssemblyCartsMod;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

@ObjectHolder("assemblycarts")
public class ACBlocks {

  public static final Block assembly_manager = null;

  public static void loadBlocks() {
    regBlock(new BlockAssemblyManager(), "assemlby_manager");
  }

  private static void regBlock(Block b, String name) {
    b.setBlockName(name);
    b.setBlockTextureName(AssemblyCartsMod.MODID + ":" + name);
    b.setCreativeTab(ACCreativeTabs.instance);
    GameRegistry.registerBlock(b, name);
  }

  private static void regTileEntity(Class<? extends TileEntity> clazz) {
    String name = clazz.getSimpleName().replaceFirst("Tile", "tile");
    GameRegistry.registerTileEntity(clazz, name);
  }

}
