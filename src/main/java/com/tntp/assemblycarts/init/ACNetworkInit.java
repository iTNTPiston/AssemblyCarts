package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.network.MNMNetwork;

public class ACNetworkInit {
  public static void loadNetwork(boolean clientSide) {
    MNMNetwork.loadMessages(clientSide);
  }
}
