package com.tntp.assemblycarts.gui;

import java.util.List;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;
import com.tntp.assemblycarts.gui.container.ContainerAssemblyManager;
import com.tntp.assemblycarts.gui.container.ContainerAssemblyManagerBooks;
import com.tntp.assemblycarts.gui.container.ContainerAssemblyPort;
import com.tntp.assemblycarts.gui.container.ContainerAssemblyRequester;
import com.tntp.assemblycarts.gui.container.ContainerAssemblyRequesterMark;
import com.tntp.assemblycarts.gui.container.ContainerMinecartAssembly;
import com.tntp.assemblycarts.gui.container.ContainerProcessBook;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class HandlerServer implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == ACGuis.getGuiID("ProcessBook")) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() == ACItems.process_book) {
                AssemblyProcess proc = ItemProcessBook.getProcessFromStack(stack);
                return new ContainerProcessBook(player.inventory, proc);
            }
        } else if (ID == ACGuis.getGuiID("MinecartAssembly")) {
            if (y < 0 && z < 0) {
                Entity e = world.getEntityByID(x);
                if (e instanceof EntityMinecartAssemblyWorker) {
                    return new ContainerMinecartAssembly(player.inventory, (EntityMinecartAssemblyWorker) e);
                }
            }
        } else if (ID == ACGuis.getGuiID("AssemblyRequester")) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileAssemblyRequester) {
                return new ContainerAssemblyRequester(player.inventory, (TileAssemblyRequester) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyRequesterMark")) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileAssemblyRequester) {
                return new ContainerAssemblyRequesterMark(player.inventory, (TileAssemblyRequester) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyPort")) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileAssemblyPort) {
                return new ContainerAssemblyPort(player.inventory, (TileAssemblyPort) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyManagerBooks")) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileAssemblyManager) {
                return new ContainerAssemblyManagerBooks(player.inventory, (TileAssemblyManager) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyManager")) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileAssemblyManager) {
                return new ContainerAssemblyManager(player.inventory, (TileAssemblyManager) tile);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

}
