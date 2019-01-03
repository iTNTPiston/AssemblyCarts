package com.tntp.assemblycarts.core;

import com.tntp.assemblycarts.init.ACBlocks;
//import com.tntp.assemblycarts.init.MNMCompat;
import com.tntp.assemblycarts.init.ACGuis;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.init.ACNetworkInit;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Proxy {

  public void preInit(FMLPreInitializationEvent event) {
    ACBlocks.loadBlocks();
    ACItems.loadItems();
    ACGuis.loadGuis();
  }

  public void init(FMLInitializationEvent event) {
    ACNetworkInit.loadNetwork(this instanceof ClientProxy);
  }

  public void postInit(FMLPostInitializationEvent event) {
    // MNMCompat.loadCompats(this instanceof ClientProxy);
  }

}
