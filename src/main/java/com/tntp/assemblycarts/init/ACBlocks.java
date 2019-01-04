package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.block.BlockAssemblyFrame;
import com.tntp.assemblycarts.block.BlockAssemblyManager;
import com.tntp.assemblycarts.block.BlockProviderTrack;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.tileentity.TileAssemblyFrame;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileProviderTrack;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

@ObjectHolder("assemblycarts")
public class ACBlocks {

  public static final Block assembly_manager = null;
  public static final Block assembly_frame = null;

  public static final Block provider_track = null;

  public static void loadBlocks() {
    regBlock(new BlockAssemblyManager(), "assembly_manager");
    regTileEntity(TileAssemblyManager.class);
    regBlock(new BlockAssemblyFrame(), "assembly_frame");
    regTileEntity(TileAssemblyFrame.class);
    regBlock(new BlockProviderTrack(), "provider_track");
    regTileEntity(TileProviderTrack.class);

  }

  private static void regBlock(Block b, String name) {
    regBlockHide(b, name, false);
  }

  private static void regBlockHide(Block b, String name, boolean hide) {
    regBlockHide(b, name, name, hide);
  }

  private static void regBlockHide(Block b, String name, String texName, boolean hide) {
    b.setBlockName(name);
    b.setBlockTextureName(AssemblyCartsMod.MODID + ":" + texName);
    if (!hide)
      b.setCreativeTab(ACCreativeTabs.instance);
    GameRegistry.registerBlock(b, name);
  }

  private static void regTileEntity(Class<? extends TileEntity> clazz) {
    String name = clazz.getSimpleName().replaceFirst("Tile", "tile");
    GameRegistry.registerTileEntity(clazz, name);
  }

}
