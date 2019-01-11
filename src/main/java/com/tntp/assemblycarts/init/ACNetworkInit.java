package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.network.ACNetwork;

@Deprecated
public class ACNetworkInit {
    public static void loadNetwork(boolean clientSide) {
        ACNetwork.loadMessages(clientSide);
    }
}
