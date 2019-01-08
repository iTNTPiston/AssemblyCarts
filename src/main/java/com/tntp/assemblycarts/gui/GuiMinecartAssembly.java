package com.tntp.assemblycarts.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiMinecartAssembly extends SGui {
    private static final ResourceLocation background = GuiProcessBook.background;

    public GuiMinecartAssembly(IInventory playerInventory, EntityMinecartAssemblyWorker cart) {
        super(new ContainerMinecartAssembly(playerInventory, cart), cart.getInventoryName());
        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        tooltips.clear();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        super.drawGuiContainerForegroundLayer(mx, my);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        EntityMinecartAssemblyWorker cart = ((ContainerMinecartAssembly) this.inventorySlots).getCart();
        ItemStack main = cart.getRequestManager().getCraftingTarget();
        if (main == null)
            main = cart.getProvideManager().getProvideTarget();

        drawBigStack(main, mx, my);
        drawRequestManagerStacks(cart.getRequestManager(), mx, my);
    }
}
