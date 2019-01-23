package com.tntp.assemblycarts.compat;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.minecraftmodapi.compat.ICompat;

public class CompatRailcraft implements ICompat {

    @Override
    public void loadClient() {
        AssemblyCartsMod.log.info("Load ME");
    }

    @Override
    public void loadCommon() {
        AssemblyCartsMod.log.info("Load MEC");

    }

}
