package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.util.UniversalUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * A template for blocks
 * 
 * @author iTNTPiston
 *
 */
public class SBlock extends Block {

  public SBlock(Material mat, float hardness, float resistance) {
    super(mat);
    this.setHardness(hardness);
    if (resistance >= 0)
      this.setResistance(resistance);
  }

  @SideOnly(Side.CLIENT)
  public Object[] getTooltipArgs() {
    return UniversalUtil.EMPTY_OBJ_ARRAY;
  }

  @Override
  public boolean onBlockEventReceived(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_) {
    super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
    if (this.hasTileEntity(world.getBlockMetadata(x, y, z))) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
    return false;
  }

}
