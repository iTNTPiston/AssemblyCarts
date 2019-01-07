package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.block.BlockAssemblium;
import com.tntp.assemblycarts.block.BlockAssemblyFrame;
import com.tntp.assemblycarts.block.BlockAssemblyManager;
import com.tntp.assemblycarts.block.BlockAssemblyPort;
import com.tntp.assemblycarts.block.BlockAssemblyProvider;
import com.tntp.assemblycarts.block.BlockAssemblyRequester;
import com.tntp.assemblycarts.block.BlockAssemblyRequesterSticky;
import com.tntp.assemblycarts.block.BlockAssemblyWorker;
import com.tntp.assemblycarts.block.BlockProviderTrack;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.item.ItemBlockProviderTrack;
import com.tntp.assemblycarts.tileentity.TileAssemblyFrame;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;
import com.tntp.assemblycarts.tileentity.TileAssemblyProvider;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;
import com.tntp.assemblycarts.tileentity.TileProviderTrack;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

@ObjectHolder("assemblycarts")
public class ACBlocks {

    public static final Block assemblium_block = null;
    public static final Block assembly_worker = null;

    public static final Block assembly_manager = null;
    public static final Block assembly_frame = null;
    public static final Block assembly_requester = null;
    public static final Block assembly_provider = null;
    public static final Block assembly_requester_sticky = null;
    public static final Block assembly_port = null;

    public static final Block provider_track = null;

    public static void loadBlocks() {
        regBlock(new BlockAssemblium(), "assemblium_block");
        regBlock(new BlockAssemblyWorker(), "assembly_worker");
        regBlock(new BlockAssemblyManager(), "assembly_manager");
        regTileEntity(TileAssemblyManager.class);
        regBlock(new BlockAssemblyFrame(), "assembly_frame");
        regTileEntity(TileAssemblyFrame.class);
        regBlock(new BlockAssemblyRequester(), "assembly_requester");
        regBlock(new BlockAssemblyRequesterSticky(), "assembly_requester_sticky");
        regTileEntity(TileAssemblyRequester.class);
        regBlock(new BlockAssemblyPort(), "assembly_port");
        regTileEntity(TileAssemblyPort.class);
        regBlock(new BlockAssemblyProvider(), "assembly_provider");
        regTileEntity(TileAssemblyProvider.class);
        regBlock(new BlockProviderTrack(), ItemBlockProviderTrack.class, "provider_track");
        regTileEntity(TileProviderTrack.class);

    }

    private static void regBlock(Block b, String name) {
        regBlockHide(b, name, false);
    }

    private static void regBlock(Block b, Class<? extends ItemBlock> item, String name) {
        regBlockHide(b, name, name, false, item);
    }

    private static void regBlockHide(Block b, String name, boolean hide) {
        regBlockHide(b, name, name, hide, null);
    }

    private static void regBlockHide(Block b, String name, String texName, boolean hide, Class<? extends ItemBlock> item) {
        b.setBlockName(name);
        b.setBlockTextureName(AssemblyCartsMod.MODID + ":" + texName);
        if (!hide)
            b.setCreativeTab(ACCreativeTabs.instance);
        if (item != null) {
            GameRegistry.registerBlock(b, item, name);
        } else {
            GameRegistry.registerBlock(b, name);
        }
    }

    private static void regTileEntity(Class<? extends TileEntity> clazz) {
        String name = clazz.getSimpleName().replaceFirst("Tile", "tile");
        GameRegistry.registerTileEntity(clazz, name);
    }

}
