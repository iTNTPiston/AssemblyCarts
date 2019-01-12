package com.tntp.assemblycarts.block.behavior;

import com.tntp.assemblycarts.tileentity.IAssemblyStructure;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.minecraftmodapi.ExeResult;
import com.tntp.minecraftmodapi.block.IBlockBehavior;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BehaviorAssemblyStructure implements IBlockBehavior {
    private void setManagerUnFormedFromComponentCoord(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof IAssemblyStructure) {
            IAssemblyStructure t = (IAssemblyStructure) tile;
            if (t.getManager() != null) {
                t.getManager().setFormed(false);
            }
        } else if (tile instanceof TileAssemblyManager) {
            ((TileAssemblyManager) tile).setFormed(false);
        }
    }

    @Override
    @Overlap
    public ExeResult breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (!world.isRemote) {
            setManagerUnFormedFromComponentCoord(world, x, y, z);
        }
        return ExeResult.CONTINUE;
    }

    @Override
    @Overlap
    public ExeResult onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (!world.isRemote) {
            setManagerUnFormedFromComponentCoord(world, x, y, z);
        }
        return ExeResult.CONTINUE;
    }
}
