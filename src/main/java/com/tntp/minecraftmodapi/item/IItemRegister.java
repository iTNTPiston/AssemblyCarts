package com.tntp.minecraftmodapi.item;

import com.tntp.minecraftmodapi.IRegister;
import com.tntp.minecraftmodapi.block.IBlockRegister;

public interface IItemRegister extends IRegister {
    /**
     * Set the texture name (Only when different from item name)
     * 
     * @param name
     * @return
     */
    IItemRegister texture(String name);

    /**
     * Hide from creative tab
     * 
     * @return
     */
    IItemRegister hide();
}
