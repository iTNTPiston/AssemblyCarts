package com.tntp.assemblycarts.api;

import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class AssemblyProcess {
    private ItemStack[] inputs;
    private ItemStack mainOutput;
    private ItemStack[] otherOutputs;

    public AssemblyProcess() {
        inputs = new ItemStack[18];
        otherOutputs = new ItemStack[18];
    }

    public ItemStack getMainOutput() {
        return mainOutput;
    }

    public void setMainOutput(ItemStack stack) {
        mainOutput = ItemStack.copyItemStack(stack);
    }

    public ItemStack getInput(int i) {
        if (i >= 0 && i < inputs.length)
            return inputs[i];
        return null;
    }

    public void setInput(int i, ItemStack stack) {
        if (i >= 0 && i < inputs.length)
            inputs[i] = ItemStack.copyItemStack(stack);
    }

    public ItemStack getOtherOutput(int i) {
        if (i >= 0 && i < otherOutputs.length)
            return otherOutputs[i];
        return null;
    }

    public void setOtherOutput(int i, ItemStack stack) {
        if (i >= 0 && i < otherOutputs.length)
            otherOutputs[i] = ItemStack.copyItemStack(stack);
    }

    public static AssemblyProcess loadProcessFromNBT(NBTTagCompound tag) {
        AssemblyProcess p = new AssemblyProcess();
        NBTTagCompound main = tag.getCompoundTag("mainOut");
        p.mainOutput = ItemStack.loadItemStackFromNBT(main);
        NBTTagList in = tag.getTagList("inputs", NBT.TAG_COMPOUND);
        for (int i = 0; i < in.tagCount(); i++) {
            NBTTagCompound inStack = in.getCompoundTagAt(i);
            ItemStack stack = ItemStack.loadItemStackFromNBT(inStack);
            if (stack != null)
                p.inputs[i] = stack;
        }
        NBTTagList out = tag.getTagList("otherOut", NBT.TAG_COMPOUND);
        for (int i = 0; i < out.tagCount(); i++) {
            NBTTagCompound outStack = out.getCompoundTagAt(i);
            ItemStack stack = ItemStack.loadItemStackFromNBT(outStack);
            if (stack != null)
                p.otherOutputs[i] = stack;
        }
        return p;
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagCompound main = new NBTTagCompound();
        if (mainOutput != null)
            mainOutput.writeToNBT(main);
        tag.setTag("mainOut", main);

        NBTTagList in = new NBTTagList();
        for (ItemStack stack : inputs) {
            if (stack != null) {
                NBTTagCompound inStack = new NBTTagCompound();
                stack.writeToNBT(inStack);
                in.appendTag(inStack);
            }
        }
        tag.setTag("inputs", in);

        NBTTagList out = new NBTTagList();
        for (ItemStack stack : otherOutputs) {
            if (stack != null) {
                NBTTagCompound outStack = new NBTTagCompound();
                stack.writeToNBT(outStack);
                out.appendTag(outStack);
            }
        }
        tag.setTag("otherOut", out);
    }

    public Iterator<ItemStack> inputIterator() {
        return new InputIterator();
    }

    private class InputIterator implements Iterator<ItemStack> {
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
        public ItemStack next() {
            ItemStack s = inputs[i];
            i++;
            findNext();
            return s;
        }

    }

}
