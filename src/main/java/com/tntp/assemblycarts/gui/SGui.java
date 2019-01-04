package com.tntp.assemblycarts.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.util.LocalUtil;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class SGui extends GuiContainer {

  private String unlocalizedTitle;

  protected int tooltipX;
  protected int tooltipY;
  protected List<String> tooltips;

  public SGui(Container container, String title) {
    super(container);

    tooltips = new ArrayList<String>();
    unlocalizedTitle = title;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mx, int my) {
    super.drawGuiContainerForegroundLayer(mx, my);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.fontRendererObj.drawString(getLocalizedTitle(unlocalizedTitle), 8, 6, 0);

  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
    tooltips.clear();
  }

  @Override
  public void drawScreen(int mx, int my, float p_73863_3_) {
    super.drawScreen(mx, my, p_73863_3_);
    GL11.glDisable(GL11.GL_LIGHTING);
    GL11.glDisable(GL11.GL_DEPTH_TEST);
    RenderHelper.disableStandardItemLighting();
    GL11.glPushMatrix();
    GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
    drawGuiContainerTopLayer(mx, my);
    GL11.glPopMatrix();
    GL11.glEnable(GL11.GL_LIGHTING);
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    RenderHelper.enableStandardItemLighting();
  }

  protected void drawGuiContainerTopLayer(int mx, int my) {
    if (!tooltips.isEmpty()) {
      drawHoveringText(tooltips, tooltipX, tooltipY, fontRendererObj);
    }
  }

  public boolean withIn(int c1, int w1, int c2, int w2) {
    return (c2 >= c1 && c2 <= c1 + w1) || (c2 + w2 >= c1 && c2 + w2 <= c1 + w1);
  }

  public boolean withInRect(int x, int y, int fromX, int fromY, int w, int h) {
    return x >= fromX && y >= fromY && x <= fromX + w && y <= fromY + h;
  }

  protected void drawItemStack(ItemStack stack, int x, int y, int mx, int my, List<String> extraTooltip) {
    if (stack != null) {
      RenderHelper.enableGUIStandardItemLighting();
      GL11.glDisable(GL11.GL_BLEND);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), stack, x, y);
      itemRender.renderItemOverlayIntoGUI(fontRendererObj, this.mc.getTextureManager(), stack, x, y);
      GL11.glEnable(GL11.GL_BLEND);
      RenderHelper.disableStandardItemLighting();
      if (withInRect(mx, my, x + guiLeft, y + guiTop, 16, 16)) {
        this.setStackTooltip(stack);
        tooltips.addAll(extraTooltip);
        tooltipX = mx - guiLeft;
        tooltipY = my - guiTop;
      }
    }
    if (withInRect(mx, my, x + guiLeft, y + guiTop, 16, 16)) {
      drawHighlightRect(x, y);
    }
  }

  protected void drawHighlightRect(int x, int y) {
    GL11.glDisable(GL11.GL_LIGHTING);
    GL11.glDisable(GL11.GL_DEPTH_TEST);
    GL11.glColorMask(true, true, true, false);
    this.drawGradientRect(x, y, x + 16, y + 16, -2130706433, -2130706433);
    GL11.glColorMask(true, true, true, true);
    GL11.glEnable(GL11.GL_LIGHTING);
    GL11.glEnable(GL11.GL_DEPTH_TEST);
  }

  protected void drawCenteredStringNoShadow(FontRenderer fr, String text, int x, int y, int color) {
    fr.drawString(text, x - fr.getStringWidth(text) / 2, y, color);
  }

  protected void playButtonSound() {
    mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
  }

  /**
   * Add wheel events.
   */
  @Override
  public void handleMouseInput() {
    super.handleMouseInput();
    int delta = Mouse.getEventDWheel();
    if (delta != 0) {
      int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
      int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
      mouseWheeled(x, y, (int) Math.signum(delta));
    }
  }

  protected void mouseWheeled(int x, int y, int wheel) {

  }

  protected String getLocalizedTitle(String unlocalizedTitle) {
    return LocalUtil.localize(unlocalizedTitle);
  }

  @SuppressWarnings("rawtypes")
  protected void setStackTooltip(ItemStack stack) {
    List list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

    for (int k = 0; k < list.size(); ++k) {
      if (k == 0) {
        tooltips.add(stack.getRarity().rarityColor + (String) list.get(k));
      } else {
        tooltips.add(EnumChatFormatting.GRAY + (String) list.get(k));
      }
    }
  }

}
