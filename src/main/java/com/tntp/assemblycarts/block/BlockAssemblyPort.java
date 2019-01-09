package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.api.Assemblium;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.item.Crowbar;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;
import com.tntp.assemblycarts.util.ClientUtil;
import com.tntp.assemblycarts.util.LocalUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAssemblyPort extends SBlockContainer {
    public BlockAssemblyPort() {
        super(Assemblium.BLOCK_MATERIAL, 5.0f, 10.0f);
    }

    private IIcon base;

    /**
     * Called upon block activation (right click on the block.)
     */
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
                ClientUtil.printChatMessage(LocalUtil.localize("ac.message.side_arg_s", LocalUtil.localize("ac.message.side_" + side)));
            }

        } else {
            if (!world.isRemote) {
                player.openGui(AssemblyCartsMod.MODID, ACGuis.getGuiID("AssemblyPort"), world, x, y, z);
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int meta = world.getBlockMetadata(x, y, z);
        return side == meta ? blockIcon : base;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int meta) {
        return s == 4 ? blockIcon : base;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);
        base = reg.registerIcon(AssemblyCartsMod.MODID + ":assembly_frame");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAssemblyPort();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int l = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }
}
