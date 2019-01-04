package com.tntp.assemblycarts.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class Crowbar {
  private static List<Item> crowbar = new ArrayList<Item>();

  public static void addToCrowbar(Item i) {
    crowbar.add(i);
  }

  public static boolean isCrowbar(Item i) {
    return crowbar.contains(i);
  }
}
