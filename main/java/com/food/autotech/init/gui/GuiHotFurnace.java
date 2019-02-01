package com.food.autotech.init.gui;

import javax.annotation.Resource;

import com.food.autotech.init.containers.ContainerHotFurnace;
import com.food.autotech.init.tileentity.TileEntityHotFurnace;
import com.food.autotech.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiHotFurnace extends GuiContainer
{

	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/hot_furnace.png");
	private final InventoryPlayer player;
	private final TileEntityHotFurnace tileentity;
	public GuiHotFurnace(InventoryPlayer player,TileEntityHotFurnace tileentity) {
		super(new ContainerHotFurnace(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
				
	}
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2-this.fontRenderer.getStringWidth(tileName)/2)-3, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize-96+2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (TileEntityHotFurnace.isBurning(this.tileentity))
        {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }
	private int getCookProgressScaled(int pixels)
    {
		System.out.println(new int[] {this.tileentity.getField(2),this.tileentity.getField(3)});
        int i = this.tileentity.getField(2);
        int j = this.tileentity.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
	}

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.tileentity.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.tileentity.getField(0) * pixels / i;
    }
}
