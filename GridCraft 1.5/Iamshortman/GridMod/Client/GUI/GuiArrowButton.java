package Iamshortman.GridMod.Client.GUI;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiArrowButton extends GuiButton
{

	public GuiArrowButton(int par1, int par2, int par3)
	{
		super(par1, par2, par3, 20, 20, null);
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
			FontRenderer var4 = par1Minecraft.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/mods/GridCraft/textures/Gui/ColorChangingTable.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int var5 = this.getHoverState(this.field_82253_i);
			
			if(var5 == 2)
			{
				GL11.glEnable(GL11.GL_BLEND);
				this.drawTexturedModalRect(this.xPosition + 1, this.yPosition + 1, 0, 136, this.width - 2, this.height - 2);
				GL11.glDisable(GL11.GL_BLEND);
			}

			
			this.mouseDragged(par1Minecraft, par2, par3);
		}
	}

}
