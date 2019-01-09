package com.tntp.minecraftmodapi.block;

import com.tntp.minecraftmodapi.APIiTNTPiston;
import com.tntp.minecraftmodapi.SuperRegister;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class RegBlock extends SuperRegister implements IBlockRegisterFactory {
    private CreativeTabs tab;

    public RegBlock() {
        APIiTNTPiston.log.info("Registering Blocks");
    }

    @Override
    public IBlockRegisterFactory creativeTabs(CreativeTabs tab) {
        this.tab = tab;
        APIiTNTPiston.log.info("[Block Registry] Registered CreativeTabs >> " + tab);
        return this;
    }

    @Override
    public IBlockRegister ofBlock(Block block, String name) {
        return new Reg(block, name);
    }

    private class Reg implements IBlockRegister {
        private Block block;
        private String name;
        private String textureName;
        private Class<? extends ItemBlock> itemBlock;
        private boolean hide = false;

        private Reg(Block b, String n) {
            block = b;
            name = n;
        }

        @Override
        public IBlockRegister item(Class<? extends ItemBlock> itemBlockClass) {
            itemBlock = itemBlockClass;
            return this;
        }

        @Override
        public IBlockRegister texture(String name) {
            this.textureName = name;
            return this;
        }

        @Override
        public IBlockRegister hide() {
            hide = true;
            return this;
        }

        @Override
        public void register() {
            block.setBlockName(name);
            if (textureName == null)
                textureName = name;
            block.setBlockTextureName(modid + ":" + textureName);
            if (!hide && tab != null)
                block.setCreativeTab(tab);
            if (itemBlock != null) {
                GameRegistry.registerBlock(block, itemBlock, name);
            } else {
                GameRegistry.registerBlock(block, name);
            }
            APIiTNTPiston.log.info("[Block Registry] >> " + name);

        }

    }

}
