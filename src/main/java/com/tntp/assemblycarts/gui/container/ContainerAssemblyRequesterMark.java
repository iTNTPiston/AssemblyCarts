package com.tntp.assemblycarts.gui.container;

import com.tntp.assemblycarts.api.MarkManager;
import com.tntp.assemblycarts.gui.SContainerMarkManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerAssemblyRequesterMark extends SContainerMarkManager {
    private TileAssemblyRequester tile;
    private InventoryPlayer playerInv;

    public ContainerAssemblyRequesterMark(IInventory playerInventory, IInventory machine) {
        super(playerInventory, 0, machine, 8, 86);
        tile = (TileAssemblyRequester) machine;
        playerInv = (InventoryPlayer) playerInventory;
    }

    @Override
    public MarkManager getMarkManager() {
        return tile.getMarkManager();
    }

    @Override
    public void setupMachineSlots(IInventory machine) {

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }

    @Override
    public void processSlotClick(int slotID, int mouseButton) {
        if (slotID >= 0 && slotID < 9) {
            ItemStack mark = this.processMarkSlotClick(mouseButton, playerInv.getItemStack(), tile.getMarkManager().getMarkedItem(slotID));
            tile.getMarkManager().setMarkedItem(slotID, mark);
        }
    }

}
