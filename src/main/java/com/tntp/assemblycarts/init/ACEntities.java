package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ACEntities {
  public static void loadEntities() {
    EntityRegistry.registerModEntity(EntityMinecartAssemblyWorker.class, "entityMinecartAssembly", 0, "assemblycarts", 64, 1,
        true);
  }
}
