package com.tntp.assemblycarts.api;

import java.util.Iterator;

import com.tntp.assemblycarts.api.mark.IMarkItem;
import com.tntp.assemblycarts.api.mark.MarkerUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class AssemblyProcess implements IInventory {
    private IMarkItem[] inputs;
    private IMarkItem mainOutput;
    private IMarkItem[] otherOutputs;

    public AssemblyProcess() {
        inputs = new IMarkItem[18];
        otherOutputs = new IMarkItem[18];
    }

    public IMarkItem getMainOutput() {
        return mainOutput;
    }

    public void setMainOutput(IMarkItem mark) {
        mainOutput = mark;
    }

    public IMarkItem getInput(int i) {
        if (i >= 0 && i < inputs.length)
            return inputs[i];
        return null;
    }

    public void setInput(int i, IMarkItem mark) {
        if (i >= 0 && i < inputs.length)
            inputs[i] = mark;
    }

    public IMarkItem getOtherOutput(int i) {
        if (i >= 0 && i < otherOutputs.length)
            return otherOutputs[i];
        return null;
    }

    public void setOtherOutput(int i, IMarkItem mark) {
        if (i >= 0 && i < otherOutputs.length)
            otherOutputs[i] = mark;
    }

    public static AssemblyProcess loadProcessFromNBT(NBTTagCompound tag) {
        AssemblyProcess p = new AssemblyProcess();
        NBTTagCompound main = tag.getCompoundTag("mainOut");
        p.mainOutput = MarkerUtil.readFromNBT(main);
        NBTTagList in = tag.getTagList("inputs", NBT.TAG_COMPOUND);
        for (int i = 0; i < in.tagCount(); i++) {
            NBTTagCompound inStack = in.getCompoundTagAt(i);
            IMarkItem stack = MarkerUtil.readFromNBT(inStack);
            if (stack != null)
                p.inputs[i] = stack;
        }
        NBTTagList out = tag.getTagList("otherOut", NBT.TAG_COMPOUND);
        for (int i = 0; i < out.tagCount(); i++) {
            NBTTagCompound outStack = out.getCompoundTagAt(i);
            IMarkItem stack = MarkerUtil.readFromNBT(outStack);
            if (stack != null)
                p.otherOutputs[i] = stack;
        }
        return p;
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagCompound main = new NBTTagCompound();
        if (mainOutput != null) {
            MarkerUtil.writeToNBT(main, mainOutput);
            tag.setTag("mainOut", main);
        }

        NBTTagList in = new NBTTagList();
        for (IMarkItem mark : inputs) {
            if (mark != null) {
                NBTTagCompound inStack = new NBTTagCompound();
                MarkerUtil.writeToNBT(inStack, mark);
                in.appendTag(inStack);
            }
        }
        tag.setTag("inputs", in);

        NBTTagList out = new NBTTagList();
        for (IMarkItem mark : otherOutputs) {
            if (mark != null) {
                NBTTagCompound outStack = new NBTTagCompound();
                MarkerUtil.writeToNBT(outStack, mark);
                out.appendTag(outStack);
            }
        }
        tag.setTag("otherOut", out);
    }

    public Iterator<IMarkItem> inputIterator() {
        return new InputIterator();
    }

    private class InputIterator implements Iterator<IMarkItem> {
        private int i = 0;

        private InputIterator() {
            findNext();
        }

        private void findNext() {
            while (i < inputs.length && inputs[i] == null)
                i++;
        }

        @Override
        public boolean hasNext() {
            return i < inputs.length;
        }

        @Override
        public IMarkItem next() {
            IMarkItem s = inputs[i];
            i++;
            findNext();
            return s;
        }

    }

    @Override
    public int getSizeInventory() {
        return 37;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (i == 0)
            return MarkerUtil.getDisplayStackSafe(getMainOutput());
        if (i < 19)
            return MarkerUtil.getDisplayStackSafe(getInput(i - 1));
        return MarkerUtil.getDisplayStackSafe(getOtherOutput(i - 19));
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {

    }

    @Override
    public String getInventoryName() {
        return "ac.gui.processbook";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

}
