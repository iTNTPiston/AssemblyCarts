package com.tntp.minecraftmodapi.gui;

import com.tntp.assemblycarts.gui.EnumGui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EnumGuiHandler implements IGuiHandler {
    public static void openGui(EnumGui gui, String mod, EntityPlayer player, World world, int x, int y, int z) {
        player.openGui(mod, gui.ordinal(), world, x, y, z);
    }

    @Override
    public final Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        IEnumGui gui = getEnumGuiWithID(ID);
        if (y < 0)
            return getContainer(gui, player, world, x);
        else
            return getContainer(gui, player, world, x, y, z);
    }

    @Override
    public final Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        IEnumGui gui = getEnumGuiWithID(ID);
        if (y < 0)
            return getGui(gui, player, world, x);
        else
            return getGui(gui, player, world, x, y, z);
    }

    public abstract Object getContainer(IEnumGui gui, EntityPlayer player, World world, int x, int y, int z);

    public abstract Object getContainer(IEnumGui gui, EntityPlayer player, World world, int entityID);

    public abstract Object getGui(IEnumGui gui, EntityPlayer player, World world, int x, int y, int z);

    public abstract Object getGui(IEnumGui gui, EntityPlayer player, World world, int entityID);

    private IEnumGui getEnumGuiWithID(int ID) {
        IEnumGui[] enumGuis = getEnumClass().getEnumConstants();
        if (ID < 0 || ID >= enumGuis.length)
            throw new ArrayIndexOutOfBoundsException("EnumGui with ID=" + ID);
        return enumGuis[ID];
    }

    public abstract Class<? extends IEnumGui> getEnumClass();
}
