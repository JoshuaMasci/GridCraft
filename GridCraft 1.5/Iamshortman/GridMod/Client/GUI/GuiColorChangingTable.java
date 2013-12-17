package Iamshortman.GridMod.Client.GUI;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.network.PacketDispatcher;

import Iamshortman.GridMod.Client.Forge.ColorHelper;
import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Container.ContainerColorChangingTable;
import Iamshortman.GridMod.Common.Item.IItemMuitiColored;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import Iamshortman.GridMod.Common.Network.ItemUpgradePacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GuiColorChangingTable extends GuiContainer
{
	public ContainerColorChangingTable Table;
	
	private GuiSlider Red = new GuiSlider(42, 41 + 54);
	private GuiSlider Blue = new GuiSlider(42, 61 + 54);
	private GuiSlider Green = new GuiSlider(42, 81 + 54);
	
	public GuiColorSave colorSaves[];

	private int isScrolling = 0;

	private float xSize_lo;

	private float ySize_lo;

	public GuiColorChangingTable(InventoryPlayer par1InventoryPlayer)
	{
		super(new ContainerColorChangingTable(par1InventoryPlayer));
		this.Table = (ContainerColorChangingTable) this.inventorySlots;
		this.xSize = 196;
		this.ySize = 234;
	}

	public void initGui()
	{
		super.initGui();
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.guiLeft + 15, this.guiTop + 37, 20, 20, "Apply"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{	
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture("/mods/GridCraft/textures/Gui/ColorChangingTable3.png");
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{	
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Red.DrawScrollBar(this);
		Blue.DrawScrollBar(this);
		Green.DrawScrollBar(this);

		this.fontRenderer.drawString(StatCollector.translateToLocal("Iamshortman.Grid.ColorChanging"), 45, 5, 16777215);
		
		int Y = 55;
		this.fontRenderer.drawString(this.Red.getColorValue() + "", 16, 44 + Y, ColorHelper.getColorFromRBG(255, 0, 0));
		this.fontRenderer.drawString(this.Blue.getColorValue() + "", 16, 64 + Y, ColorHelper.getColorFromRBG(0, 255, 0));
		this.fontRenderer.drawString(this.Green.getColorValue() + "", 16, 84 + Y, ColorHelper.getColorFromRBG(0, 0, 255));
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

	private void RenderModelOnScreen(Minecraft mc, ItemStack stack, int color, float d, float par2, float par3, float par4, float par5)
	{
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(d, par2, 43.0F);
        GL11.glScalef((-par3), par3, par3);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableGUIStandardItemLighting();
        ((IItemMuitiColored)stack.getItem()).RenderModelInColorTable(mc, stack, color);
        RenderHelper.disableStandardItemLighting();
        GL11.glTranslatef(-d, -par2, -43.0F);
        GL11.glPopMatrix();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	private int MouseOnScrollBar(int x, int y)
	{
		boolean bln = Mouse.isButtonDown(0);
		if (!bln)
		{
			return 0;
		}

		int var4 = (this.width - this.xSize) / 2;
		int var5 = (this.height - this.ySize) / 2;
		x -= var4;
		y -= var5;

		if (Red.MouseOnScrollBar(x, y))
		{
			return 1;
		}
		else if (Blue.MouseOnScrollBar(x, y))
		{
			return 2;
		}
		else if (Green.MouseOnScrollBar(x, y))
		{
			return 3;
		}

		return 0;
	}

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();

		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		boolean var4 = Mouse.isButtonDown(0);
		if (!var4)
		{
			this.isScrolling = 0;
		}
		else if (this.isScrolling == 0)
		{
			this.isScrolling = this.MouseOnScrollBar(x, y);
		}

		if (this.isScrolling > 0)
		{
			int tempX = (this.width - this.xSize) / 2;
			int tempY = (this.height - this.ySize) / 2;
			x -= tempX;
			y -= tempY;
			GuiSlider temp = null;
			if (this.isScrolling == 1)
			{
				temp = this.Red;
			}
			else if (this.isScrolling == 2)
			{
				temp = this.Blue;
			}
			else if (this.isScrolling == 3)
			{
				temp = this.Green;
			}

			int sliderPosX = x - (temp.xPos);
			temp.setXPos(sliderPosX);
		}
	}
	
	public int getColor()
	{
		return ColorHelper.getColorFromRBG(this.Red.getColorValue(), this.Blue.getColorValue(), this.Green.getColorValue());
	}
	
	@Override
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        this.xSize_lo = (float)par1;
        this.ySize_lo = (float)par2;
        
		if(Table.hasItem())
		{
			ItemStack stack = Table.tableInventory.getStackInSlot(0);
			Item item = stack.getItem();
			if(IItemMuitiColored.class.isAssignableFrom(item.getClass()))
			{
				GL11.glPopMatrix();
				int Color = this.getColor();
		        int k = this.guiLeft;
		        int l = this.guiTop;
				this.RenderModelOnScreen(mc, stack, Color, k + 75.5F, l + 50, 30, (float)(k + 51) - this.xSize_lo, (float)(l + 75 - 50) - this.ySize_lo);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix();
			}
		}
    }
	
	 protected void actionPerformed(GuiButton par1GuiButton)
	 {
		 if(par1GuiButton.id == 1)
		 {
			 this.Table.UpdateColor(this.getColor());
		 }
	 }
}
