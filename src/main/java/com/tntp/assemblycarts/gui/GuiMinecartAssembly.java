package com.tntp.assemblycarts.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.entity.EntityMinecartAssembly;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiMinecartAssembly extends SGui {
  private static final ResourceLocation background = new ResourceLocation(AssemblyCartsMod.MODID,
      "textures/guis/process_book.png");

  public GuiMinecartAssembly(IInventory playerInventory, EntityMinecartAssembly cart) {
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
    EntityMinecartAssembly cart = ((ContainerMinecartAssembly) this.inventorySlots).getCart();
    ItemStack main = cart.getRequestManager().getCraftingTarget();

    if (main != null) {
      GL11.glPushMatrix();
      GL11.glTranslatef(15, 28, 0);
      GL11.glScalef(2, 2, 1);
      RenderHelper.enableGUIStandardItemLighting();
      GL11.glDisable(GL11.GL_BLEND);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), main, 0, 0);
      itemRender.renderItemOverlayIntoGUI(fontRendererObj, this.mc.getTextureManager(), main, 0, 0);
      GL11.glEnable(GL11.GL_BLEND);
      RenderHelper.disableStandardItemLighting();
      if (withInRect(mx, my, 15 + guiLeft, 28 + guiTop, 32, 32)) {
        this.setStackTooltip(main);
        tooltipX = mx - guiLeft;
        tooltipY = my - guiTop;
      }
      GL11.glPopMatrix();
    }
    if (withInRect(mx, my, 14 + guiLeft, 27 + guiTop, 34, 34)) {
      GL11.glDisable(GL11.GL_LIGHTING);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      GL11.glColorMask(true, true, true, false);
      this.drawGradientRect(14, 27, 48, 61, -2130706433, -2130706433);
      GL11.glColorMask(true, true, true, true);
      GL11.glEnable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    // GL11.glTranslatef(-guiLeft, -guiTop, 0);
    List<ItemStack> need = cart.getRequestManager().getNeed();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        int index = i * 6 + j;
        if (index < need.size())
          this.drawItemStack(need.get(index), 62 + j * 18, 18 + i * 18, mx, my, Collections.EMPTY_LIST);
        else
          this.drawItemStack(null, 62 + j * 18, 18 + i * 18, mx, my, Collections.EMPTY_LIST);
      }
    }
    RenderHelper.enableGUIStandardItemLighting();

    // GL11.glTranslatef(guiLeft, guiTop, 0);
  }
}
