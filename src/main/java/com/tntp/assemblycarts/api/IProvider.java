package com.tntp.assemblycarts.api;

import net.minecraft.item.ItemStack;

public interface IProvider {
  public ItemStack getProviderTarget();

  /**
   * Provide to the target
   * 
   * @param rm
   * @return true if the provide is successful
   */
  public boolean provide(RequestManager rm);
}
