package com.tntp.assemblycarts.item;

import java.util.Arrays;
import java.util.HashSet;

import com.tntp.assemblycarts.api.Assemblium;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class ItemCrowbarAssemblium extends ItemTool {

    public ItemCrowbarAssemblium() {
        super(5, Assemblium.ASSEMBLIUM, new HashSet<Block>(Arrays.asList(new Block[] { Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail })));
        setHarvestLevel("crowbar", 2);
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
        return true;
    }
}
