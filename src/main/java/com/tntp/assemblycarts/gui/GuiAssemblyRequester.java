package com.tntp.assemblycarts.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiAssemblyRequester extends SGui {
    private static final ResourceLocation background = GuiProcessBook.background;

    public GuiAssemblyRequester(IInventory playerInventory, TileAssemblyRequester tile) {
        super(new ContainerAssemblyRequester(playerInventory, tile), tile.getInventoryName());
        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        tooltips.clear();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        this.drawTexturedModalRect(guiLeft + 25, guiTop + 66, xSize, 0, 18, 18);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        super.drawGuiContainerForegroundLayer(mx, my);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        TileAssemblyRequester tile = ((ContainerAssemblyRequester) this.inventorySlots).getTile();
        ItemStack main = tile.getRequestManager().getCraftingTarget();
        drawBigStack(main, mx, my);
        drawRequestManagerStacks(tile.getRequestManager(), mx, my);
    }

}
