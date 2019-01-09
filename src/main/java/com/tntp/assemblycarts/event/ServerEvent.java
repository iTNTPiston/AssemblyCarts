package com.tntp.assemblycarts.event;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;
import com.tntp.assemblycarts.init.ACGuis;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

@Deprecated
public class ServerEvent {
    @SubscribeEvent
    public void onMinecartInteraction(MinecartInteractEvent e) {
        if (e.minecart instanceof EntityMinecartAssemblyWorker) {
            e.setCanceled(true);
            if (!e.player.worldObj.isRemote) {
                e.player.openGui(AssemblyCartsMod.MODID, ACGuis.getGuiID("MinecartAssembly"), e.player.worldObj, (int) (e.minecart.posX), (int) (e.minecart.posY), (int) (e.minecart.posZ));
            }
        }
    }
}
