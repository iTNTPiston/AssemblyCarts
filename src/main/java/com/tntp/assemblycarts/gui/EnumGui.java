package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.core.AssemblyCartsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public enum EnumGui {
    UNKNOWN, AssemblyManager;
    private Class<? extends SContainer> container;

    @SideOnly(Side.CLIENT)
    private Class<? extends SGui> gui;

    public SContainer buildContainer() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    private void injectGui() {
        if (this != UNKNOWN) {
            String fullName = "com.tntp." + AssemblyCartsMod.MODID + ".gui.Gui" + this.name();
            try {
                Class<?> guiClass = Class.forName(fullName);
                gui = (Class<? extends SGui>) guiClass;
                AssemblyCartsMod.log.info("[Gui Injecter] Injected " + fullName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                AssemblyCartsMod.log.error("[Gui Injecter] Cannot Inject GUI: " + fullName);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initGuis() {
        AssemblyCartsMod.log.info("Loading Guis");
        for (EnumGui e : EnumGui.values())
            e.injectGui();
        AssemblyCartsMod.log.info("Loaded Guis");
    }

    @SuppressWarnings("unchecked")
    private void injectContainer() {
        if (this != UNKNOWN) {
            String fullName = "com.tntp." + AssemblyCartsMod.MODID + ".gui.container.Container" + this.name();
            try {
                Class<?> containerClass = Class.forName(fullName);
                container = (Class<? extends SContainer>) containerClass;
                AssemblyCartsMod.log.info("[Container Injecter] Injected " + fullName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                AssemblyCartsMod.log.error("[Container Injecter] Cannot Inject Container: " + fullName);
            }
        }
    }

    public static void initContainers() {
        AssemblyCartsMod.log.info("Loading Gui Containers");
        for (EnumGui e : EnumGui.values())
            e.injectContainer();
        AssemblyCartsMod.log.info("Loaded Gui Containers");
    }
}
