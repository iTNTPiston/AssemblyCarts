package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.event.ServerEvent;

import net.minecraftforge.common.MinecraftForge;

public class ACEvents {
  public static void loadServerEvents() {
    System.out.println("loadevent");
    MinecraftForge.EVENT_BUS.register(new ServerEvent());
  }

  public static void loadClientEvents() {

  }
}
