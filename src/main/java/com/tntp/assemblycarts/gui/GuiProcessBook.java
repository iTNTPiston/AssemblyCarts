package com.tntp.assemblycarts.gui;

import java.util.Collections;

import org.lwjgl.opengl.GL11;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.network.ACNetwork;
import com.tntp.assemblycarts.network.MSGuiProcessBookSlotClick;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiProcessBook extends SGui {
  private static final ResourceLocation background = new ResourceLocation(AssemblyCartsMod.MODID,
      "textures/guis/process_book.png");

  public GuiProcessBook(IInventory playerInventory, AssemblyProcess proc) {
    super(new ContainerProcessBook(playerInventory, proc), "ac.gui.processbook");
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
    AssemblyProcess process = ((ContainerProcessBook) this.inventorySlots).getProcess();
    ItemStack main = process.getMainOutput();

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
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        this.drawItemStack(process.getInput(i * 6 + j), 62 + j * 18, 18 + i * 18, mx, my, Collections.EMPTY_LIST);
      }
    }
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 9; j++) {
        this.drawItemStack(process.getOtherOutput(i * 6 + j), 8 + j * 18, 90 + i * 18, mx, my, Collections.EMPTY_LIST);
      }
    }
    RenderHelper.enableGUIStandardItemLighting();

    // GL11.glTranslatef(guiLeft, guiTop, 0);
  }

  @Override
  protected void mouseClicked(int x, int y, int button) {
    super.mouseClicked(x, y, button);
    int processSlotID = -1;
    x -= guiLeft;
    y -= guiTop;
    if (withInRect(x, y, 14, 27, 32, 32)) {
      processSlotID = 0;
    } else if (withInRect(x, y, 61, 17, 108, 54)) {
      x -= 61;
      y -= 17;
      int i = y / 18;
      int j = x / 18;
      processSlotID = 1 + i * 6 + j;
    } else if (withInRect(x, y, 7, 89, 162, 36)) {
      x -= 7;
      y -= 89;
      int i = y / 18;
      int j = x / 18;
      processSlotID = 19 + i * 9 + j;
    }
    if (processSlotID != -1) {
      ((ContainerProcessBook) this.inventorySlots).processSlotClick(processSlotID, button);
      ACNetwork.network
          .sendToServer(new MSGuiProcessBookSlotClick(this.inventorySlots.windowId, processSlotID, button));
    }
    // System.out.println(processSlotID);
  }

}
