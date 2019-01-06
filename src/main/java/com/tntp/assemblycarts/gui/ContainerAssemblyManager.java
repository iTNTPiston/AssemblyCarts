package com.tntp.assemblycarts.gui;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerAssemblyManager extends SContainerRequestManager {

  private TileAssemblyManager tile;
  private int selectedProcessID;
  private int processMultiplier;

  public ContainerAssemblyManager(IInventory playerInventory, IInventory machine) {
    super(playerInventory, 0, machine, 8, 198);
    tile = (TileAssemblyManager) machine;
    processMultiplier = 1;
    selectedProcessID = -1;
  }

  public void setupPlayerInventory(IInventory playerInventory, int playerSlotsX, int playerSlotsY) {
    for (int j = 0; j < 9; j++) {
      this.addSlotToContainer(new Slot(playerInventory, j, playerSlotsX + j * 18, playerSlotsY));
    }
  }

  @Override
  public RequestManager getRequestManager() {
    return tile.getRequestManager();
  }

  @Override
  public void setupMachineSlots(IInventory machine) {

  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return tile.isUseableByPlayer(player);
  }

  public TileAssemblyManager getTile() {
    return tile;
  }

  public AssemblyProcess getSelectedProcess() {
    if (selectedProcessID < 0)
      return null;
    return tile.getProcessBySlot(selectedProcessID);
  }

  @SideOnly(Side.CLIENT)
  public void selectNextProcess() {
    for (int i = selectedProcessID + 1; i < 27; i++) {
      if (tile.getProcessBySlot(i) != null) {
        selectedProcessID = i;
        return;
      }
    }
    for (int i = 0; i < selectedProcessID; i++) {
      if (tile.getProcessBySlot(i) != null) {
        selectedProcessID = i;
        return;
      }
    }
    selectedProcessID = -1;
  }

  public void setProcessMultiplier(int m) {
    if (m < 1)
      m = 1;
    processMultiplier = m;
  }

  public int getProcessMultiplier() {
    return processMultiplier;
  }

}
