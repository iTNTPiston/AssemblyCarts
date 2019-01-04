package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAssemblyRequester extends SBlockContainer {

  public BlockAssemblyRequester() {
    super(Material.iron, 5.0f, 10.0f);
  }

  @Override
  public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
    return new TileAssemblyRequester();
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
      float hitY, float hitZ) {
    if (!world.isRemote) {
      player.openGui(AssemblyCartsMod.MODID, ACGuis.getGuiID("AssemblyRequester"), world, x, y, z);
    }
    return true;
  }

}
