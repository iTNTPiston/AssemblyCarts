package com.tntp.assemblycarts.block;

import com.tntp.assemblycarts.tileentity.TileAssemblyManager;

public interface IAssemblyStructure {
  public TileAssemblyManager getManager();

  public void setManager(TileAssemblyManager tile);
}
