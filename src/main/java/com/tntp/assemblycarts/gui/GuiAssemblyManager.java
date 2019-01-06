package com.tntp.assemblycarts.gui;

import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.network.ACNetwork;
import com.tntp.assemblycarts.network.MSGuiSlotClick;
import com.tntp.assemblycarts.tileentity.TileAssemblyManager;
import com.tntp.assemblycarts.tileentity.TileAssemblyRequester;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiAssemblyManager extends SGui {
  private static final ResourceLocation background = new ResourceLocation(AssemblyCartsMod.MODID,
      "textures/guis/assembly_manager.png");
  private static final int[] INCREMENTS = { 1, 16, 64, 256 };

  public GuiAssemblyManager(IInventory player, IInventory tile) {
    super(new ContainerAssemblyManager(player, tile), tile.getInventoryName());
    xSize = 176;
    ySize = 222;
  }

  @Override
  public void initGui() {
    super.initGui();
    ((ContainerAssemblyManager) this.inventorySlots).selectNextProcess();
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
    int multiplier = ((ContainerAssemblyManager) this.inventorySlots).getProcessMultiplier();

    AssemblyProcess process = ((ContainerAssemblyManager) this.inventorySlots).getSelectedProcess();
    if (process != null) {
      ItemStack main = ItemStack.copyItemStack(process.getMainOutput());

      if (main != null) {
        main.stackSize *= multiplier;
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
        ItemStack stack = process != null ? ItemStack.copyItemStack(process.getInput(i * 6 + j)) : null;
        if (stack != null)
          stack.stackSize *= multiplier;
        this.drawItemStack(stack, 62 + j * 18, 18 + i * 18, mx, my, Collections.EMPTY_LIST);
      }
    }
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 9; j++) {
        ItemStack stack = process != null ? ItemStack.copyItemStack(process.getOtherOutput(i * 6 + j)) : null;
        if (stack != null)
          stack.stackSize *= multiplier;
        this.drawItemStack(stack, 8 + j * 18, 90 + i * 18, mx, my, Collections.EMPTY_LIST);
      }
    }
    RenderHelper.enableGUIStandardItemLighting();

    // GL11.glTranslatef(guiLeft, guiTop, 0);
  }

  @Override
  protected void mouseClicked(int x, int y, int button) {
    super.mouseClicked(x, y, button);
    x -= guiLeft;
    y -= guiTop;
    if (withInRect(x, y, 14, 27, 32, 32)) {
      this.playButtonSound();
      ((ContainerAssemblyManager) this.inventorySlots).selectNextProcess();
    }

  }

}
