package com.tntp.assemblycarts.init;

import java.lang.reflect.Field;

import com.tntp.assemblycarts.block.BlockAssemblium;
import com.tntp.assemblycarts.block.BlockAssemblyFrame;
import com.tntp.assemblycarts.block.BlockAssemblyManager;
import com.tntp.assemblycarts.block.BlockAssemblyPort;
import com.tntp.assemblycarts.block.BlockAssemblyProvider;
import com.tntp.assemblycarts.block.BlockAssemblyRequester;
import com.tntp.assemblycarts.block.BlockAssemblyRequesterSticky;
import com.tntp.assemblycarts.block.BlockAssemblyWorker;
import com.tntp.assemblycarts.block.BlockDockingTrack;
import com.tntp.assemblycarts.block.behavior.BehaviorAssemblyStructure;
import com.tntp.assemblycarts.block.behavior.BehaviorCrowbar;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.item.ItemBlockDockingTrack;
import com.tntp.assemblycarts.tileentity.TileAssemblyFrame;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;
import com.tntp.assemblycarts.tileentity.TileAssemblyProvider;
import com.tntp.assemblycarts.tileentity.TileDockingTrack;
import com.tntp.minecraftmodapi.block.IBlockBehavior;
import com.tntp.minecraftmodapi.block.IBlockRegisterFactory;
import com.tntp.minecraftmodapi.block.RegBlock;
import com.tntp.minecraftmodapi.tileentity.ITileEntityRegisterFactory;
import com.tntp.minecraftmodapi.tileentity.RegTileEntity;

import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.block.Block;

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

    public static final Block docking_track = null;

    public static void loadBlocks() {
        IBlockBehavior bhvrAssemStruc = new BehaviorAssemblyStructure();
        IBlockBehavior bhvrCrowBar = new BehaviorCrowbar();

        IBlockRegisterFactory reg = new RegBlock().creativeTabs(ACCreativeTabs.instance);
        ITileEntityRegisterFactory treg = new RegTileEntity();
        reg.of(new BlockAssemblium(), "assemblium_block").register();
        reg.of(new BlockAssemblyWorker(), "assembly_worker").behave(bhvrCrowBar).register();

        reg.of(new BlockAssemblyFrame(), "assembly_frame").behave(bhvrAssemStruc).register();
        treg.ofTE(TileAssemblyFrame.class).register();
        reg.of(new BlockAssemblyManager(), "assembly_manager").behave(bhvrAssemStruc).register();
        treg.ofTE(TileAssemblyManager.class).register();
        reg.of(new BlockAssemblyPort(), "assembly_port").behave(bhvrAssemStruc).behave(bhvrCrowBar).register();
        treg.ofTE(TileAssemblyPort.class).register();
        reg.of(new BlockAssemblyRequester(), "assembly_requester").behave(bhvrAssemStruc).behave(bhvrCrowBar).register();
        reg.of(new BlockAssemblyRequesterSticky(), "assembly_requester_sticky").behave(bhvrAssemStruc).behave(bhvrCrowBar).register();

        reg.of(new BlockAssemblyProvider(), "assembly_provider").behave(bhvrCrowBar).register();
        treg.ofTE(TileAssemblyProvider.class).register();

        reg.of(new BlockDockingTrack(), "docking_track").item(ItemBlockDockingTrack.class).behave(bhvrCrowBar).register();
        treg.ofTE(TileDockingTrack.class).register();

    }

    public static void validateInjection() {
        AssemblyCartsMod.log.info("Validating Injection");
        Field[] field = ACBlocks.class.getDeclaredFields();
        for (Field f : field) {
            try {
                if (f.get(null) == null) {
                    AssemblyCartsMod.log.error("Incomplete Injection!");
                    throw new IllegalStateException(f.toString() + " is null");
                }
                AssemblyCartsMod.log.info("[Injection] " + f.getName() + " validated.");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                AssemblyCartsMod.log.warn("Cannot check injection!");
            }
        }
    }

}
