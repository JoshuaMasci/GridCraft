package Iamshortman.GridMod.Client.GUI;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Item.ItemUpgradeHelper;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import Iamshortman.GridMod.Common.Network.ItemUpgradePacket;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

public class GuiCodeRevTable extends GuiContainer
{
	public ContainerCodeRevTable Table;

	/**
	 * pointing to the frist upgrade displayed on the top of the list. used to
	 * scroll though the list
	 */
	public int pointer = 0;

	public boolean isScrolling = false;
	
	public int YScrollValue = 0;
	
	public final float maxSliderMovement = 64F;
	
	public GuiCodeRevTable(InventoryPlayer par1InventoryPlayer)
	{
		super(new ContainerCodeRevTable(par1InventoryPlayer));
		this.Table = (ContainerCodeRevTable) this.inventorySlots;
		this.xSize = 174;
		this.ySize = 184;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(StatCollector.translateToLocal("Iamshortman.Grid.CodeRevTable"), 37, 6, 16777215);
		if (this.inventorySlots instanceof ContainerCodeRevTable)
		{
			
			List<Upgrade> list = Table.upgrades;
			if (list == null)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.renderEngine.bindTexture("/mods/GridCraft/textures/Gui/CodeRevTable.png");
				this.drawTexturedModalRect(147, 19, 244, 0, 12, 15);
				return;
			}
			else
			{
				
				if(this.needsScrollBars())
				{
					if(!this.isScrolling)
					{
						float percentage = (float)this.pointer / (float)this.getMaxPointerValue();
						this.YScrollValue =(int) (percentage * this.maxSliderMovement);
					}
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.renderEngine.bindTexture("/mods/GridCraft/textures/Gui/CodeRevTable.png");
					this.drawTexturedModalRect(147, 19 + this.YScrollValue, 232, 0, 12, 15);
				}
				else
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.renderEngine.bindTexture("/mods/GridCraft/textures/Gui/CodeRevTable.png");
					this.drawTexturedModalRect(147, 19, 244, 0, 12, 15);
				}
			}
			
			for (int i = 0; i < 4; i++)
			{
				if (list.size() > i + pointer)
				{
					Upgrade upgrade = list.get(i + pointer);
					if (upgrade != null)
					{
						this.drawUpgrade(upgrade, i);
					}
				}
			}
		}
	}

	public void drawUpgrade(Upgrade upgrade, int i)
	{
		// 0 223 108 19
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture("/mods/GridCraft/textures/Gui/CodeRevTable.png");
		int var5 = 34;
		int var6 = 21;
		var6 += (i * 19);

		String str = upgrade.displayName;
		int Level = this.Table.getUpgradeLevel(upgrade);
		int Color = 16777215;
		if (Level > upgrade.GetMaxLevel())
		{
			Level = upgrade.GetMaxLevel();
			this.drawTexturedModalRect(var5, var6, 0, 204, 108, 19);
			Color = 0;
		}
		else
		{
			if (Table.canPlayerAfford(upgrade, Level))
			{
				this.drawTexturedModalRect(var5, var6, 0, 223, 108, 19);
			}
			else
			{
				this.drawTexturedModalRect(var5, var6, 0, 204, 108, 19);
			}

			int price = upgrade.GetCostForLevel(Level);
			if (price != -1)
			{
				String cost = "" + price;
				this.fontRenderer.drawStringWithShadow(cost, var5 + 105 - this.fontRenderer.getStringWidth(cost), var6 + 8, 8453920);
			}
		}
		String displayStr = str + " V" + Level;
		this.fontRenderer.drawString(displayStr, var5 + 2, var6 + 2, Color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture("/mods/GridCraft/textures/Gui/CodeRevTable.png");
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		int var4 = (this.width - this.xSize) / 2;
		int var5 = (this.height - this.ySize) / 2;

		 if(this.MouseInScrollBar(par1,par2))
		 {
				this.isScrolling = true;
		 }
		
		
		for (int var6 = 0; var6 < 4; ++var6)
		{
			int var7 = par1 - (var4 + 35);
			int var8 = par2 - (var5 + 24 + 19 * var6);

			if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19)
			{
				Upgrade upgrade = this.Table.getUpgradeInSlot(var6 + pointer);
				if (upgrade == null)
				{
					continue;
				}
				if (this.Table.canUserBuy(upgrade))
				{
					PacketDispatcher.sendPacketToServer(ItemUpgradePacket.createPacket(this.Table.windowId, upgrade.id, 0));
					this.Table.upgradeItem(this.mc.thePlayer, upgrade, 1);
				}
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Slider based Stuff

	@Override
	public void handleMouseInput()
	{
		super.handleMouseInput();

		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		
		boolean var4 = Mouse.isButtonDown(0);
		if(!var4)	
		{
			this.isScrolling = false;
		}
		
		if(this.isScrolling && this.needsScrollBars())
		{
			int offsetY = (this.height - this.ySize) / 2;
			int mY = y - offsetY;
			this.YScrollValue = mY - 19;
			if(this.YScrollValue > 64)
			{
				this.YScrollValue = 64;
			}
			else if(this.YScrollValue < 0)
			{
				this.YScrollValue = 0;
			}	
			float percentage = this.YScrollValue / this.maxSliderMovement;
			float tempPointer = percentage * this.getMaxPointerValue();
			this.pointer = (int) tempPointer;
		}
		
		if (this.needsScrollBars())
		{
			int var1 = Mouse.getEventDWheel();
			if (var1 != 0)
			{
				if (var1 > 0)
				{
					var1 = 1;
				}

				if (var1 < 0)
				{
					var1 = -1;
				}
				this.pointer -= var1;
				if (this.pointer < 0)
				{
					this.pointer = 0;
				}
				if (this.pointer > getMaxPointerValue())
				{
					this.pointer = getMaxPointerValue();
				}

			}
		}
		else
		{
			this.pointer = 0;
		}
	}

	private boolean MouseInScrollBar(int x, int y)
	{
		boolean bln = Mouse.isButtonDown(0);
		if(!bln)
		{
			return false;
		}
		
		int var4 = (this.width - this.xSize) / 2;
		int var5 = (this.height - this.ySize) / 2;
		
		int mX = x - var4;
		int mY = y - var5;
		
		if(x >= var4 + 147 && x <= var4 + 158)
		{
			if(y >= var5 + 19 && y <= var5 + 97)
			{
				return true;
			}
		}
		return false;
	}

	private int getMaxPointerValue()
	{
		if (this.Table.item != null && this.Table.upgrades != null)
		{
			return this.Table.upgrades.size() - 4;
		}
		return 0;

	}

	private boolean needsScrollBars()
	{
		if (this.Table.item != null && this.Table.upgrades != null && this.Table.upgrades.size() > 4)
		{
			return true;
		}
		return false;
	}

}
