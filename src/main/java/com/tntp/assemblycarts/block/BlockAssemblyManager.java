package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.api.Assemblium;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.gui.EnumGui;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.item.Crowbar;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.minecraftmodapi.gui.EnumGuiHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAssemblyManager extends SBlockContainer {
    private IIcon on;

    public BlockAssemblyManager() {
        super(Assemblium.BLOCK_MATERIAL, 5.0f, 10.0f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta == 0 ? blockIcon : on;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);
        this.on = reg.registerIcon(this.getTextureName() + "_on");
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (!world.isRemote) {
            TileAssemblyManager tile = (TileAssemblyManager) world.getTileEntity(x, y, z);
            if (tile != null)
                tile.setFormed(false);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAssemblyManager();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack item = player.getCurrentEquippedItem();
            EnumGui e;
            if (item != null && Crowbar.isCrowbar(item.getItem())) {
                e = EnumGui.AssemblyManagerBooks;
            } else {
                e = EnumGui.AssemblyManager;
            }
            EnumGuiHandler.openGui(e, AssemblyCartsMod.MODID, player, world, x, y, z);
        }
        return true;
    }

}
