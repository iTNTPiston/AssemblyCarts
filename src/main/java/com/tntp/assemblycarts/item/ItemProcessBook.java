package com.tntp.assemblycarts.item;

import java.util.List;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.api.mark.IMarkItem;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.gui.EnumGui;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.util.KeyUtil;
import com.tntp.assemblycarts.util.LocalUtil;
import com.tntp.minecraftmodapi.gui.EnumGuiHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemProcessBook extends SItem {
    private IIcon slotIcon;

    public ItemProcessBook() {
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getEmptySlotIcon() {
        return ACItems.process_book.slotIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister reg) {
        super.registerIcons(reg);
        slotIcon = reg.registerIcon(this.getIconString() + "_bg");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                EnumGuiHandler.openGuiCurrentItem(EnumGui.ProcessBook, AssemblyCartsMod.MODID, player, world);
            }
        }
        return stack;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return hasProcess(stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return hasProcess(stack) ? EnumRarity.uncommon : EnumRarity.common;
    }

    public static boolean hasProcess(ItemStack stack) {
        if (stack == null || !stack.hasTagCompound())
            return false;
        NBTTagCompound tag = stack.getTagCompound();
        if (!tag.hasKey("AC|Process"))
            return false;
        return true;
    }

    public static AssemblyProcess getProcessFromStack(ItemStack stack) {
        if (stack == null || !stack.hasTagCompound())
            return new AssemblyProcess();
        NBTTagCompound tag = stack.getTagCompound();
        if (!tag.hasKey("AC|Process"))
            return new AssemblyProcess();
        NBTTagCompound process = tag.getCompoundTag("AC|Process");
        return AssemblyProcess.loadProcessFromNBT(process);
    }

    public static void writeProcessToStack(ItemStack stack, AssemblyProcess proc) {
        if (stack == null)
            return;
        NBTTagCompound stackTag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        NBTTagCompound processTag = new NBTTagCompound();
        proc.writeToNBT(processTag);
        stackTag.setTag("AC|Process", processTag);
        stack.setTagCompound(stackTag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean adv) {
        if (hasProcess(stack)) {
            if (KeyUtil.isShiftDown()) {
                AssemblyProcess process = getProcessFromStack(stack);
                IMarkItem main = process.getMainOutput();
                if (main != null) {
                    tooltip.add(LocalUtil.localize("ac.tooltip.process_book.craft") + " " + LocalUtil.localize("ac.tooltip.process_book.stack_arg_s_d", main.displayName(), main.stacksize()));
                }
                if (KeyUtil.isCtrlDown()) {
                    tooltip.add(LocalUtil.localize("ac.tooltip.process_book.input"));
                    for (int i = 0; i < 18; i++) {
                        IMarkItem in = process.getInput(i);
                        if (in == null)
                            break;
                        tooltip.add(LocalUtil.localize("ac.tooltip.process_book.stack_arg_s_d", in.displayName(), in.stacksize()));
                    }
                    tooltip.add(LocalUtil.localize("ac.tooltip.process_book.other_output"));
                    for (int i = 0; i < 18; i++) {
                        IMarkItem out = process.getOtherOutput(i);
                        if (out == null)
                            break;
                        tooltip.add(LocalUtil.localize("ac.tooltip.process_book.stack_arg_s_d", out.displayName(), out.stacksize()));
                    }
                } else {
                    tooltip.add(LocalUtil.localize("ac.tooltip.hold_ctrl"));
                }
            } else {
                tooltip.add(LocalUtil.localize("ac.tooltip.hold_shift"));
            }
        }
    }
}
