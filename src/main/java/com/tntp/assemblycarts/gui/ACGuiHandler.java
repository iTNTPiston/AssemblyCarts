package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;
import com.tntp.assemblycarts.item.tag.TagProcessBook;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;
import com.tntp.minecraftmodapi.gui.EnumGuiHandler;
import com.tntp.minecraftmodapi.gui.IEnumGui;
import com.tntp.minecraftmodapi.util.ItemUtil;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ACGuiHandler extends EnumGuiHandler {

    public static void registerGuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(AssemblyCartsMod.MODID, new ACGuiHandler());
    }

    private ACGuiHandler() {
    }

    private Object getArgFromEntity(IEnumGui gui, Entity entity) {
        if (gui == EnumGui.MinecartAssemblyWorker && entity instanceof EntityMinecartAssemblyWorker)
            return entity;
        return null;
    }

    private Object getArgFromTE(IEnumGui gui, TileEntity tile) {
        return validateTEForGui(gui, tile) ? tile : null;
    }

    private boolean validateTEForGui(IEnumGui gui, TileEntity tile) {
        if (gui == EnumGui.AssemblyManager || gui == EnumGui.AssemblyManagerBooks)
            return tile instanceof TileAssemblyManager;
        if (gui == EnumGui.AssemblyRequester || gui == EnumGui.AssemblyRequesterMark)
            return tile instanceof TileAssemblyRequester;
        if (gui == EnumGui.AssemblyPort)
            return tile instanceof TileAssemblyPort;
        return false;
    }

    private Object getArgFromItem(IEnumGui gui, ItemStack stack) {
        if (gui == EnumGui.ProcessBook) {
            if (stack != null) {
                TagProcessBook tag = ItemUtil.getItemTag(stack, new TagProcessBook());
                if (tag == null)
                    return new AssemblyProcess();
                return tag.process;
            }
        }
        return null;
    }

    @Override
    public Object getContainer(IEnumGui gui, EntityPlayer player, World world, int x, int y, int z) {
        IInventory playerInv = player.inventory;
        Object guiInv = getArgFromTE(gui, world.getTileEntity(x, y, z));
        if (guiInv != null)
            return gui.buildContainer(playerInv, guiInv);
        return null;
    }

    @Override
    public Object getContainerFromEntity(IEnumGui gui, EntityPlayer player, World world, int entityID) {
        IInventory playerInv = player.inventory;
        Object guiInv = getArgFromEntity(gui, world.getEntityByID(entityID));
        if (guiInv != null)
            return gui.buildContainer(playerInv, guiInv);
        return null;
    }

    public Object getContainerFromCurrentItem(IEnumGui gui, ItemStack mainHand, EntityPlayer player, World world) {
        IInventory playerInv = player.inventory;
        Object guiInv = getArgFromItem(gui, mainHand);
        if (guiInv != null)
            return gui.buildContainer(playerInv, guiInv);
        return null;
    }

    @Override
    public Object getGui(IEnumGui gui, EntityPlayer player, World world, int x, int y, int z) {
        IInventory playerInv = player.inventory;
        Object guiInv = getArgFromTE(gui, world.getTileEntity(x, y, z));
        if (guiInv != null)
            return gui.buildGui(playerInv, guiInv);
        return null;
    }

    @Override
    public Object getGuiFromEntity(IEnumGui gui, EntityPlayer player, World world, int entityID) {
        IInventory playerInv = player.inventory;
        Object guiInv = getArgFromEntity(gui, world.getEntityByID(entityID));
        if (guiInv != null)
            return gui.buildGui(playerInv, guiInv);
        return null;
    }

    public Object getGuiFromCurrentItem(IEnumGui gui, ItemStack mainHand, EntityPlayer player, World world) {
        IInventory playerInv = player.inventory;
        Object guiInv = getArgFromItem(gui, mainHand);
        if (guiInv != null)
            return gui.buildGui(playerInv, guiInv);
        return null;
    }

    @Override
    public Class<? extends IEnumGui> getEnumClass() {
        return EnumGui.class;
    }

}
