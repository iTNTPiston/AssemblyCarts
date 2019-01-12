package com.tntp.minecraftmodapi.block;

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
public class BlockAPIiTNTPiston extends Block {

    public BlockAPIiTNTPiston(Material mat, float hardness, float resistance) {
        super(mat);
        this.setHardness(hardness);
        if (resistance >= 0)
            this.setResistance(resistance);
    }

    @SideOnly(Side.CLIENT)
    public Object[] getTooltipArgs() {
        return UniversalUtil.EMPTY_OBJ_ARRAY;
    }

    /**
     * Called by RegBlock before block is registered. Used by Container to setup
     * default behavior
     */
    public void preBlockRegister(IBlockRegister reg) {

    }

}
