package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.entity.EntityMinecartAssembly;
import com.tntp.assemblycarts.render.RenderAssemblyCarts;
import com.tntp.assemblycarts.render.RenderTrack;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityMinecart;

public class ACRender {
  public static void register() {
    RenderTrack render = new RenderTrack();
    int id = RenderingRegistry.getNextAvailableRenderId();
    RenderTrack.renderID = id;
    RenderingRegistry.registerBlockHandler(id, render);

    // RenderingRegistry.registerEntityRenderingHandler(EntityMinecartAssembly.class,
    // RenderManager.instance.getEntityClassRenderObject(EntityMinecart.class));
  }
}
