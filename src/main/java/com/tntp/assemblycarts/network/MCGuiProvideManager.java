package com.tntp.assemblycarts.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MCGuiProvideManager extends MANBT1<MCGuiProvideManager> {
  protected int windowID;

  public MCGuiProvideManager(int windowID, NBTTagCompound provideManager) {
    super(provideManager);
    this.windowID = windowID;
  }

  public MCGuiProvideManager() {
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
  public IMessage onMessage(MCGuiProvideManager message, MessageContext ctx) {
    return null;
  }

}
