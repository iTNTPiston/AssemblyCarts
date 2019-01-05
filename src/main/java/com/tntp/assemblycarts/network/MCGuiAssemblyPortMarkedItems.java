package com.tntp.assemblycarts.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class MCGuiAssemblyPortMarkedItems extends MANBT1<MCGuiAssemblyPortMarkedItems> {
  protected int windowID;

  public MCGuiAssemblyPortMarkedItems(int windowID, ItemStack[] marked) {
    super(toTag(marked));
    this.windowID = windowID;
  }

  private static NBTTagCompound toTag(ItemStack[] is) {
    NBTTagCompound wrapper = new NBTTagCompound();
    NBTTagList markedList = new NBTTagList();
    for (int i = 0; i < is.length; i++) {
      if (is[i] != null) {
        NBTTagCompound entry = new NBTTagCompound();
        entry.setInteger("slot", i);
        NBTTagCompound stack = new NBTTagCompound();
        is[i].writeToNBT(stack);
        entry.setTag("item", stack);
        markedList.appendTag(entry);
      }
    }
    wrapper.setTag("wrapped", markedList);
    return wrapper;
  }

  static ItemStack[] toStacks(NBTTagCompound wrapper) {
    ItemStack[] is = new ItemStack[9];
    NBTTagList markedList = wrapper.getTagList("wrapped", NBT.TAG_COMPOUND);
    for (int i = 0; i < markedList.tagCount(); i++) {
      NBTTagCompound entry = markedList.getCompoundTagAt(i);
      int slot = entry.getInteger("slot");
      ItemStack item = ItemStack.loadItemStackFromNBT(entry.getCompoundTag("item"));
      is[slot] = item;
    }
    return is;
  }

  public MCGuiAssemblyPortMarkedItems() {
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
  public IMessage onMessage(MCGuiAssemblyPortMarkedItems message, MessageContext ctx) {
    return null;
  }

}
