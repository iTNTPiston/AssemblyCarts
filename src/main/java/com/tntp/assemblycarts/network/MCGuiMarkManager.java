package com.tntp.assemblycarts.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class MCGuiMarkManager extends MANBT1<MCGuiMarkManager> {
  protected int windowID;

  public MCGuiMarkManager(int windowID, NBTTagCompound markManager) {
    super(markManager);
    this.windowID = windowID;
  }

  public MCGuiMarkManager() {
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
  public IMessage onMessage(MCGuiMarkManager message, MessageContext ctx) {
    return null;
  }

}
