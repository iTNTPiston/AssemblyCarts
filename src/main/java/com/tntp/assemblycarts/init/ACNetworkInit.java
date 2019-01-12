package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.network.*;
import com.tntp.minecraftmodapi.network.MCChatMsg;
import com.tntp.minecraftmodapi.network.RegMessage;

public class ACNetworkInit {
    public static void loadNetwork() {
        RegMessage reg = new RegMessage().injectTo(ACNtwk.class);
        reg.of(MCChatMsg.class).register();
        reg.of(MCGuiProvideManager.class).register();
        reg.of(MCGuiRequestManager.class).register();
        reg.of(MSGuiSlotClick.class).register();
    }
}
