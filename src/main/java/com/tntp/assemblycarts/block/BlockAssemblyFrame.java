package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.tileentity.TileAssemblyFrame;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAssemblyFrame extends SBlock implements ITileEntityProvider {

  public BlockAssemblyFrame() {
    super(Material.iron, 5.0f, 10.0f);
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    if (!world.isRemote) {
      IAssemblyStructure tile = (IAssemblyStructure) world.getTileEntity(x, y, z);
      if (tile.getManager() != null) {
        tile.getManager().setFormed(false);
      }
    }
    super.breakBlock(world, x, y, z, block, meta);

  }

  /**
   * Lets the block know when one of its neighbor changes. Doesn't know which
   * neighbor changed (coordinates passed are
   * their own) Args: x, y, z, neighbor Block
   */
  @Override
  public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
    if (!world.isRemote) {
      IAssemblyStructure tile = (IAssemblyStructure) world.getTileEntity(x, y, z);
      if (tile.getManager() != null) {
        tile.getManager().setFormed(false);
      }
    }
  }

  @Override
  public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
    return new TileAssemblyFrame();
  }

}
