package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.tileentity.TileAssemblyProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAssemblyProvider extends SBlockContainer {
  private IIcon bottom;
  private IIcon side;

  public BlockAssemblyProvider() {
    super(Material.iron, 5.0f, 10.0f);
  }

  @Override
  public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
    return new TileAssemblyProvider();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int s, int meta) {
    switch (s) {
    case 0:
      return bottom;
    case 1:
      return blockIcon;
    }
    return side;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister reg) {
    blockIcon = reg.registerIcon(AssemblyCartsMod.MODID + ":assembly_provider_top");
    side = reg.registerIcon(this.getTextureName() + "_side");
    bottom = reg.registerIcon(this.getTextureName() + "_bottom");
  }

}
