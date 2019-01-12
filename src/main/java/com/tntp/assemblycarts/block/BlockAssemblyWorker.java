package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.api.Assemblium;
import com.tntp.assemblycarts.block.behavior.BehaviorCrowbar.ICrowbarRotatable;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.item.Crowbar;
import com.tntp.minecraftmodapi.block.BlockBehaviorAPIiTNTPiston;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAssemblyWorker extends BlockBehaviorAPIiTNTPiston implements ICrowbarRotatable {
    public BlockAssemblyWorker() {
        super(Assemblium.BLOCK_MATERIAL, 5.0f, 10.0f);
    }

    private IIcon front;
    private IIcon base;

//    @Override
//    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
//        // change direction
//        ItemStack item = player.getCurrentEquippedItem();
//        if (item != null && Crowbar.isCrowbar(item.getItem())) {
//            int meta = world.getBlockMetadata(x, y, z);
//            if (meta == side)
//                side ^= 1;
//            if (side > 1) {
//                world.setBlockMetadataWithNotify(x, y, z, side, 2);
//            }
//            if (world.isRemote) {
//                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
//            }
//            return true;
//        }
//        return false;
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int meta) {
        return ((s == 0 || s == 1) ? blockIcon : (s == meta || (s ^ 1) == meta) ? front : base);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(AssemblyCartsMod.MODID + ":assemblium_block");
        front = reg.registerIcon(this.getTextureName() + "_front");
        base = reg.registerIcon(this.getTextureName() + "_base");
    }

}
