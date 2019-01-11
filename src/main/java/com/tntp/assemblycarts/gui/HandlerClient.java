package com.tntp.assemblycarts.gui;

import java.util.List;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Deprecated
public class HandlerClient extends HandlerServer {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // SGui gui = null;
        System.out.println(ACGuis.getGui(ID));
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == ACGuis.getGuiID("ProcessBook")) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() == ACItems.process_book) {
                AssemblyProcess proc = ItemProcessBook.getProcessFromStack(stack);
                return new GuiProcessBook(player.inventory, proc);
            }
        } else if (ID == ACGuis.getGuiID("MinecartAssembly")) {
            if (y < 0 && z < 0) {
                Entity e = world.getEntityByID(x);
                if (e instanceof EntityMinecartAssemblyWorker) {
                    return new GuiMinecartAssemblyWorker(player.inventory, (EntityMinecartAssemblyWorker) e);
                }
            }
        } else if (ID == ACGuis.getGuiID("AssemblyRequester")) {
            if (tile instanceof TileAssemblyRequester) {
                return new GuiAssemblyRequester(player.inventory, (TileAssemblyRequester) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyRequesterMark")) {
            if (tile instanceof TileAssemblyRequester) {
                return new GuiAssemblyRequesterMark(player.inventory, (TileAssemblyRequester) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyPort")) {
            if (tile instanceof TileAssemblyPort) {
                return new GuiAssemblyPort(player.inventory, (TileAssemblyPort) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyManagerBooks")) {
            if (tile instanceof TileAssemblyManager) {
                return new GuiAssemblyManagerBooks(player.inventory, (TileAssemblyManager) tile);
            }
        } else if (ID == ACGuis.getGuiID("AssemblyManager")) {
            if (tile instanceof TileAssemblyManager) {
                return new GuiAssemblyManager(player.inventory, (TileAssemblyManager) tile);
            }
        }

        return null;
    }
}
