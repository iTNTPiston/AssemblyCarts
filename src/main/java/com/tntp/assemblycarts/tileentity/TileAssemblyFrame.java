package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.block.IAssemblyStructure;

public class TileAssemblyFrame extends STile implements IAssemblyStructure {
  private TileAssemblyManager manager;

  @Override
  public TileAssemblyManager getManager() {
    if (!manager.isValidInWorld())
      manager = null;
    return manager;
  }

  @Override
  public void setManager(TileAssemblyManager tile) {
    manager = tile;
  }

}
