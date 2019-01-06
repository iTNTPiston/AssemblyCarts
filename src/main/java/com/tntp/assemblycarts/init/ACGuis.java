package com.tntp.assemblycarts.init;

import java.util.ArrayList;
import java.util.Hashtable;

import com.tntp.assemblycarts.core.AssemblyCartsMod;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ACGuis {
  private static int nextID = 0;
  private static Hashtable<String, Integer> identifierToGuiId = new Hashtable<String, Integer>();
  private static ArrayList<String> guiIdToIdentifier = new ArrayList<String>();
  @SidedProxy(clientSide = "com.tntp.assemblycarts.gui.HandlerClient", serverSide = "com.tntp.assemblycarts.gui.HandlerServer")
  private static IGuiHandler guiHandler;

  private static int getNextGuiID() {
    return nextID++;
  }

  public static void assignGuiID(String identifier) {
    identifierToGuiId.put(identifier, getNextGuiID());
    guiIdToIdentifier.add(identifier);
  }

  public static void loadGuis() {
    // don't use class here to prevent server side from loading those classes
    assignGuiID("ProcessBook");
    assignGuiID("MinecartAssembly");
    assignGuiID("AssemblyRequester");
    assignGuiID("AssemblyPort");
    assignGuiID("AssemblyManagerBooks");
    assignGuiID("AssemblyManager");

    NetworkRegistry.INSTANCE.registerGuiHandler(AssemblyCartsMod.MODID, guiHandler);

  }

  public static int getGuiID(String identifier) {
    return identifierToGuiId.get(identifier);
  }

  public static String getGui(int id) {
    return guiIdToIdentifier.get(id);
  }

}
