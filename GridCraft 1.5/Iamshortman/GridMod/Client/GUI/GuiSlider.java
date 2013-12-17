package Iamshortman.GridMod.Client.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiSlider
{
	public int xOffset = 0;
	public int yPos = 0;
	public int xPos = 0;
	
	public GuiSlider(int X, int Y)
	{
		this.xPos = X;
		this.yPos = Y;
	}
	
	public void setXPos(int pos)
	{
		if(pos > 100)
		{
			this.xOffset = 100;
		}
		else if(pos < 0)
		{
			this.xOffset = 0;
		}
		else
		{
			this.xOffset = pos;	
		}
	}
	
	public int getXPos()
	{
		return xOffset;
	}

	public int getColorValue()
	{
		float i = this.getXPos() / 100.0F;
		int color = (int) (i * 255);
		return color;
	}
	
	public boolean MouseOnScrollBar(int x, int y)
	{
		if(x >= this.xPos + this.xOffset && x <= this.xPos + this.xOffset + 8)
		{
			if(y >= this.yPos && y <= this.yPos + 14)
			{
				return true;
			}
		}
		return false;
	}
	
	public void DrawScrollBar(GuiContainer gui)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture("/mods/GridCraft/textures/Gui/ColorChangingTable2.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.drawTexturedModalRect(this.xPos + this.getXPos(), this.yPos, 248, 0, 8, 14);
	}
	
}

