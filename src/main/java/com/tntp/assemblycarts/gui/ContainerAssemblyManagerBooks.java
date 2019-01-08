package com.tntp.assemblycarts.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerAssemblyManagerBooks extends SContainer {
    private IInventory machine;

    public ContainerAssemblyManagerBooks(IInventory playerInventory, IInventory machine) {
        super(playerInventory, 9, machine, 8, 50);
        this.machine = machine;
    }

    @Override
    public void setupMachineSlots(IInventory machine) {
        for (int j = 0; j < 9; j++) {
            Slot bookSlot = new Slot(machine, j, 8 + j * 18, 18);
            this.addSlotToContainer(bookSlot);
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return machine.isUseableByPlayer(player);
    }

}
