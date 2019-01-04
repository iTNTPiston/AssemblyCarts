package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.tileentity.TileAssemblyProvider;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAssemblyProvider extends SBlockContainer {

  public BlockAssemblyProvider() {
    super(Material.iron, 5.0f, 10.0f);
    // TODO Auto-generated constructor stub
  }

  @Override
  public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
    return new TileAssemblyProvider();
  }

}
