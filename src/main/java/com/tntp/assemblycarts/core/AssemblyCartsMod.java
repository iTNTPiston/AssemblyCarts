package com.tntp.assemblycarts.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AssemblyCartsMod.MODID, version = AssemblyCartsMod.VERSION)
public class AssemblyCartsMod {
  public static final String MODID = "assemblycarts";
  public static final String VERSION = "1.7.10-1.0.0";
  @SidedProxy(clientSide = "com.tntp.assemblycarts.core.ClientProxy", serverSide = "com.tntp.assemblycarts.core.Proxy")
  public static Proxy proxy;
  public static Logger log = LogManager.getLogger("Assembly Carts");

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
