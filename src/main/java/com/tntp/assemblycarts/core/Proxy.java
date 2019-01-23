package com.tntp.assemblycarts.core;

import com.tntp.assemblycarts.gui.ACEnumGui;
import com.tntp.assemblycarts.gui.ACGuiHandler;
import com.tntp.assemblycarts.init.ACBlocks;
import com.tntp.assemblycarts.init.ACCompats;
import com.tntp.assemblycarts.init.ACEntities;
import com.tntp.assemblycarts.init.ACEvents;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.init.ACMarks;
import com.tntp.assemblycarts.init.ACNetworkInit;
import com.tntp.minecraftmodapi.gui.EnumGuiInjector;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Proxy {

    public void preInit(FMLPreInitializationEvent event) {
        ACBlocks.loadBlocks();
        ACItems.loadItems();
        ACEntities.loadEntities();

        AssemblyCartsMod.log.info("Registering Guis");
        EnumGuiInjector.injectContainer("com.tntp." + AssemblyCartsMod.MODID + ".gui.container.Container", ACEnumGui.values());
        ACGuiHandler.registerGuiHandler();

        ACMarks.registerMarkClasses();
    }

    public void init(FMLInitializationEvent event) {
        ACBlocks.validateInjection();
        ACItems.validateInjection();
        AssemblyCartsMod.log.info("Registering Messages");
        ACNetworkInit.loadNetwork();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ACCompats.loadCompats(this instanceof ClientProxy);
        ACEvents.loadServerEvents();

    }

}
