package com.tntp.assemblycarts.init;

import com.tntp.assemblycarts.item.ItemAssemblyCart;
import com.tntp.assemblycarts.item.ItemCrowbarAssemblium;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.minecraftmodapi.item.IItemRegisterFactory;
import com.tntp.minecraftmodapi.item.RegItem;

import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraft.item.Item;

@ObjectHolder("assemblycarts")
public class ACItems {
    public static final Item assembly_worker_cart = null;

    public static final Item crowbar_assemblium = null;

    public static final ItemProcessBook process_book = null;

    public static void loadItems() {
        IItemRegisterFactory reg = new RegItem().creativeTabs(ACCreativeTabs.instance);
        reg.of(new ItemAssemblyCart(), "assembly_worker_cart").register();
        reg.of(new ItemCrowbarAssemblium(), "crowbar_assemblium").register();
        reg.of(new ItemProcessBook(), "process_book").register();
    }
}
