package com.tntp.assemblycarts.network;

import com.tntp.assemblycarts.core.AssemblyCartsMod;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ACNetwork {
  public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(AssemblyCartsMod.MODID);
  private static int id = 0;

  public static void loadMessages(boolean clientSide) {

    // regMS all messages received on server side
    try {
      loadServerMessages();

      if (clientSide) {
        // regMCC all messages received on client side
        loadClientMessagesClientSide();

      } else {
        // regMCS all messages received on client side
        loadClientMessagesServerSide();

      }
    } catch (Exception e) {
      throw new RuntimeException("MetalNetworkMainframe-Network: Cannot inst message: " + e.getMessage());
    }

  }

  public static <REQ extends SMessage<REQ>> void regMS(Class<REQ> c) throws Exception {
    registerMessage(c, Side.SERVER);
  }

  public static <REQ extends SMessage<REQ>> void regMCS(Class<REQ> c) throws Exception {
    registerMessage(c, Side.CLIENT);
  }

  public static <REQ extends SMessage<REQ>, REPLY extends IMessage> void regMCC(Class<REQ> c,
      IMessageHandler<REQ, REPLY> handler) {
    network.registerMessage(handler, c, id++, Side.CLIENT);
  }

  public static <REQ extends SMessage<REQ>> void registerMessage(Class<REQ> c, Side side) throws Exception {
    try {
      REQ req = c.newInstance();
      network.registerMessage(req, c, id++, side);
    } catch (Exception e) {
      AssemblyCartsMod.log.error("Cannot instantiate message " + c.getSimpleName() + ", check constructor.");
      throw e;
    }
  }

  private static void loadServerMessages() throws Exception {
    regMS(MSGuiProcessBookSlotClick.class);
    regMS(MSGuiAssemblyPortSlotClick.class);
  }

  private static void loadClientMessagesServerSide() throws Exception {
    regMCS(MCGuiRequestManager.class);
    regMCS(MCGuiProvideManager.class);
    regMCS(MCGuiAssemblyPortMarkedItems.class);
  }

  @SideOnly(Side.CLIENT)
  private static void loadClientMessagesClientSide() {
    regMCC(MCGuiRequestManager.class, new MCGuiRequestManagerHandler());
    regMCC(MCGuiProvideManager.class, new MCGuiProvideManagerHandler());
    regMCC(MCGuiAssemblyPortMarkedItems.class, new MCGuiAssemblyPortMarkedItemsHandler());
  }

}
