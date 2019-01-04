package com.tntp.assemblycarts.render;

import com.tntp.assemblycarts.block.BlockProviderTrack;
import com.tntp.assemblycarts.init.ACBlocks;
import com.tntp.assemblycarts.tileentity.TileProviderTrack;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderTrack implements ISimpleBlockRenderingHandler {
  public static int renderID;

  @Override
  public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
      RenderBlocks renderer) {
    boolean reversed = ((TileProviderTrack) world.getTileEntity(x, y, z)).isReversed();
    renderBlockMinecartTrack(renderer, (BlockProviderTrack) block, x, y, z, reversed);
    return true;
  }

  @Override
  public boolean shouldRender3DInInventory(int modelId) {
    return false;
  }

  @Override
  public int getRenderId() {
    return renderID;
  }

  public boolean renderBlockMinecartTrack(RenderBlocks renderBlock, BlockProviderTrack block, int x, int y, int z,
      boolean reversed) {
    Tessellator tessellator = Tessellator.instance;
    int meta = renderBlock.blockAccess.getBlockMetadata(x, y, z);
    IIcon iicon = renderBlock.getBlockIconFromSideAndMetadata(block, 0, meta);

    if (renderBlock.hasOverrideBlockTexture()) {
      iicon = renderBlock.overrideBlockTexture;
    }

    if (block.isPowered()) {
      meta &= 7;
    }

    tessellator.setBrightness(block.getMixedBrightnessForBlock(renderBlock.blockAccess, x, y, z));
    tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
    double minU = (double) iicon.getMinU();
    double minV = (double) iicon.getMinV();
    double maxU = (double) iicon.getMaxU();
    double maxV = (double) iicon.getMaxV();
    double height = 0.0625D;
    double x1 = (double) (x + 1);
    double x2 = (double) (x + 1);
    double x3 = (double) (x + 0);
    double x4 = (double) (x + 0);
    double z1 = (double) (z + 0);
    double z2 = (double) (z + 1);
    double z3 = (double) (z + 1);
    double z4 = (double) (z + 0);
    double y1 = (double) y + height;
    double y2 = (double) y + height;
    double y3 = (double) y + height;
    double y4 = (double) y + height;

    if (meta != 1 && meta != 2 && meta != 3 && meta != 7) {
      if (meta == 8) {
        x1 = x2 = (double) (x + 0);
        x3 = x4 = (double) (x + 1);
        z1 = z4 = (double) (z + 1);
        z2 = z3 = (double) (z + 0);
      } else if (meta == 9) {
        x1 = x4 = (double) (x + 0);
        x2 = x3 = (double) (x + 1);
        z1 = z2 = (double) (z + 0);
        z3 = z4 = (double) (z + 1);
      }
    } else {
      x1 = x4 = (double) (x + 1);
      x2 = x3 = (double) (x + 0);
      z1 = z2 = (double) (z + 1);
      z3 = z4 = (double) (z + 0);
    }

    if (meta != 2 && meta != 4) {
      if (meta == 3 || meta == 5) {
        ++y2;
        ++y3;
      }
    } else {
      ++y1;
      ++y4;
    }
    // Reversion hack
    if (reversed) {
      if (meta == 0 || meta == 4 || meta == 5) {
        double zt = z1;
        z4 = z1 = z2;
        z2 = z3 = zt;

      } else if (meta == 1 || meta == 2 || meta == 3) {
        double xt = x1;
        x4 = x1 = x2;
        x2 = x3 = xt;
      }
      double yt = y1;
      y4 = y1 = y2;
      y2 = y3 = yt;
    }

    tessellator.addVertexWithUV(x1, y1, z1, maxU, minV);
    tessellator.addVertexWithUV(x2, y2, z2, maxU, maxV);
    tessellator.addVertexWithUV(x3, y3, z3, minU, maxV);
    tessellator.addVertexWithUV(x4, y4, z4, minU, minV);
    tessellator.addVertexWithUV(x4, y4, z4, minU, minV);
    tessellator.addVertexWithUV(x3, y3, z3, minU, maxV);
    tessellator.addVertexWithUV(x2, y2, z2, maxU, maxV);
    tessellator.addVertexWithUV(x1, y1, z1, maxU, minV);
    return true;
  }

}
