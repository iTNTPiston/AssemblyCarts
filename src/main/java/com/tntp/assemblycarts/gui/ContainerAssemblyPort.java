package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.MarkManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyPort;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAssemblyPort extends SContainerMarkManager {
    private TileAssemblyPort tile;
    private InventoryPlayer playerInv;

    public ContainerAssemblyPort(IInventory playerInventory, IInventory machine) {
        super(playerInventory, machine.getSizeInventory(), machine, 8, 86);
        tile = (TileAssemblyPort) machine;
        playerInv = (InventoryPlayer) playerInventory;
    }

    public TileAssemblyPort getTile() {
        return tile;
    }

    @Override
    public void setupMachineSlots(IInventory machine) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlotToContainer(new Slot(machine, i * 3 + j, 98 + j * 18, 18 + i * 18));
            }
        }

    }

    public void setMarkedItemStack(int i, ItemStack s) {
        tile.setMarkedItemStack(i, s);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }

    @Override
    public void processSlotClick(int slotID, int mouseButton) {
        if (slotID >= 0 && slotID < 9) {
            ItemStack mark = this.processMarkSlotClick(mouseButton, playerInv.getItemStack(), tile.getMarkedItemStack(slotID));
            setMarkedItemStack(slotID, mark);
        }
    }

    @Override
    public MarkManager getMarkManager() {
        return tile.getMarkManager();
    }

}
