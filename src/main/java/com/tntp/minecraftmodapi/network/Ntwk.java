package com.tntp.minecraftmodapi.network;

import java.util.HashMap;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Ntwk {
    private static HashMap<String, SimpleNetworkWrapper> modToNetwork = new HashMap<>();

    public static SimpleNetworkWrapper newChannel(String mod) {
        SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(mod);
        modToNetwork.put(mod, wrapper);
        return wrapper;
    }
}
