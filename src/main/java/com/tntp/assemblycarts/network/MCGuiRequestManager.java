package com.tntp.assemblycarts.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MCGuiRequestManager extends MANBT1<MCGuiRequestManager> {
  protected int windowID;

  public MCGuiRequestManager(int windowID, NBTTagCompound requestManager) {
    super(requestManager);
    this.windowID = windowID;
  }

  public MCGuiRequestManager() {
    super(null);
  }

  public void toBytes(ByteBuf buf) {
    super.toBytes(buf);
    buf.writeInt(windowID);
  }

  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    windowID = buf.readInt();
  }

  @Override
  public IMessage onMessage(MCGuiRequestManager message, MessageContext ctx) {
    return null;
  }

}
