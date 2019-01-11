package com.tntp.assemblycarts.gui;

import com.tntp.minecraftmodapi.gui.EnumGuiHandler;
import com.tntp.minecraftmodapi.gui.IEnumGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ACGuiHandler extends EnumGuiHandler {

    @Override
    public Object getContainer(IEnumGui gui, EntityPlayer player, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getContainer(IEnumGui gui, EntityPlayer player, World world, int entityID) {
        return null;
    }

    @Override
    public Object getGui(IEnumGui gui, EntityPlayer player, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getGui(IEnumGui gui, EntityPlayer player, World world, int entityID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<? extends IEnumGui> getEnumClass() {
        return EnumGui.class;
    }

}
