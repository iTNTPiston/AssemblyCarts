package com.tntp.assemblycarts.tileentity;

public class TileAssemblyFrame extends STile implements IAssemblyStructure {
  private TileAssemblyManager manager;

  @Override
  public TileAssemblyManager getManager() {
    if (manager != null && !manager.isValidInWorld())
      manager = null;
    return manager;
  }

  @Override
  public void setManager(TileAssemblyManager tile) {
    manager = tile;
  }

}
