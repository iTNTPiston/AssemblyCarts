package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.api.Assemblium;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.item.Crowbar;
import com.tntp.assemblycarts.tileentity.TileAssemblyProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAssemblyProvider extends SBlockContainer {
    private IIcon port;
    private IIcon side;

    public BlockAssemblyProvider() {
        super(Assemblium.BLOCK_MATERIAL, 5.0f, 10.0f);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAssemblyProvider();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        // change direction
        ItemStack item = player.getCurrentEquippedItem();
        if (item != null && Crowbar.isCrowbar(item.getItem())) {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta == side)
                side ^= 1;
            world.setBlockMetadataWithNotify(x, y, z, side, 2);
            if (world.isRemote) {
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            }
            return true;
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int meta) {
        if (s == meta)
            return port;
        switch (s) {
        case 0:
        case 1:
            return blockIcon;
        }
        return side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(AssemblyCartsMod.MODID + ":assemblium_block");
        side = reg.registerIcon(this.getTextureName() + "_side");
        port = reg.registerIcon(this.getTextureName() + "_port");
    }

}
