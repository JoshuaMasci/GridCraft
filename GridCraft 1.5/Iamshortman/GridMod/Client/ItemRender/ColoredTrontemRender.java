package Iamshortman.GridMod.Client.ItemRender;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.Item.ItemUpgradeHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;

public class ColoredTrontemRender implements IItemRenderer
{

	public double zLevel;

	public RenderEngine RenderEngine = Minecraft.getMinecraft().renderEngine;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		if(type == type.FIRST_PERSON_MAP)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if (type == type.INVENTORY)
		{
			this.renderIteminInventory(item);
			if (ItemUpgradeHelper.hasEffect(item))
			{
				this.renderInventoryEnchantment(item, ItemUpgradeHelper.getColor(item));
			}
			return;
		}

		Tessellator var5 = Tessellator.instance;
		

		for (int i = 0; i < 2; i++)
		{
			Icon icon = item.getItem().getIcon(item, i);
			if (icon == null)
			{
				GL11.glPopMatrix();
				return;
			}

			Tessellator tessellator = Tessellator.instance;
			float f = icon.getMinU();
			float f1 = icon.getMaxU();
			float f2 = icon.getMinV();
			float f3 = icon.getMaxV();
			float f4 = 0.0F;
			float f5 = 0.3F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			float f6 = 1.5F;

			ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), 0.0625F);
		}
        
		if (ItemUpgradeHelper.hasEffect(item))
		{
			this.renderEnchantmentEffect(item, ItemUpgradeHelper.getColor(item));
		}
	}

	protected void loadTexture(String f1Str)
	{
		RenderEngine var2 = Minecraft.getMinecraft().renderEngine;
		var2.bindTexture(f1Str);
	}

	protected void renderEnchantmentEffect(ItemStack item, EnumEnchantmentColor color)
	{
		Tessellator tessellator = Tessellator.instance;
		RenderEngine RenderEngine = Minecraft.getMinecraft().renderEngine;
		if (item != null)
		{
			 GL11.glDepthFunc(GL11.GL_EQUAL);
             GL11.glDisable(GL11.GL_LIGHTING);
             RenderEngine.bindTexture("%blur%/misc/glint.png");
             GL11.glEnable(GL11.GL_BLEND);
             GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
             float f7 = 0.76F;
             this.ShiftColorEffect(color);
             GL11.glMatrixMode(GL11.GL_TEXTURE);
             GL11.glPushMatrix();
             float f8 = 0.125F;
             GL11.glScalef(f8, f8, f8);
             float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
             GL11.glTranslatef(f9, 0.0F, 0.0F);
             GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
             ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
             GL11.glPopMatrix();
             GL11.glPushMatrix();
             GL11.glScalef(f8, f8, f8);
             f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
             GL11.glTranslatef(-f9, 0.0F, 0.0F);
             GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
             ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
             GL11.glPopMatrix();
             GL11.glMatrixMode(GL11.GL_MODELVIEW);
             GL11.glDisable(GL11.GL_BLEND);
             GL11.glEnable(GL11.GL_LIGHTING);
             GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

	public void ShiftColorEffect(EnumEnchantmentColor color)
	{
		float var21 = 0.76F;
		if (color == EnumEnchantmentColor.Yellow)
		{
			GL11.glColor4f(0.8F * var21, 0.8F * var21, 0.0F, 1.0F);
		}
		else if (color == EnumEnchantmentColor.Green)
		{
			GL11.glColor4f(0.1F * var21, 0.9F * var21, 0.1F * var21, 1.0F);
		}
		else if (color == EnumEnchantmentColor.Red)
		{
			GL11.glColor4f(0.8F * var21, 0.1F * var21, 0.1F * var21, 1.0F);
		}
		else if (color == EnumEnchantmentColor.Blue)
		{
			GL11.glColor4f(0.1F * var21, 0.1F * var21, 0.8F * var21, 1.0F);
		}
		else if (color == EnumEnchantmentColor.Orange)
		{
			GL11.glColor4f(1.0F * var21, 0.5F * var21, 0.1F * var21, 1.0F);
		}

	}

	/*
	 * *************************************************************************************************************************************************************************************************************
	 */

	public void renderIteminInventory(ItemStack item)
	{
		int var9;
		int var6 = item.itemID;
		int var7 = item.getItemDamage();
		
		for (int i = 0; i < 2; i++)
		{
			Icon icon = item.getItem().getIcon(item, i);
			if (icon == null)
			{
				GL11.glPopMatrix();
				return;
			}

			Tessellator tessellator = Tessellator.instance;
			float f = icon.getMinU();
			float f1 = icon.getMaxU();
			float f2 = icon.getMinV();
			float f3 = icon.getMaxV();
			float f4 = 0.0F;
			float f5 = 0.3F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			float f6 = 1.5F;

			ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), 0.0625F);
		}
		
		Icon icon = item.getIconIndex();

		if(icon == null)
		{
			GL11.glPopMatrix();
			return;
		}
		
        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float f4 = 0.0F;
        float f5 = 0.3F;
        //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //GL11.glTranslatef(-f4, -f5, 0.0F);
        float f6 = 15.0F;
        GL11.glScalef(f6, f6, f6);
        //GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        //GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), 0.0625F);
	}

	public void renderInventoryEnchantment(ItemStack item, EnumEnchantmentColor color)
	{
		Tessellator tessellator = Tessellator.instance;
		if (item != null)
		{
			 GL11.glDepthFunc(GL11.GL_EQUAL);
             GL11.glDisable(GL11.GL_LIGHTING);
             RenderEngine.bindTexture("%blur%/misc/glint.png");
             GL11.glEnable(GL11.GL_BLEND);
             GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
             float f7 = 0.76F;
             this.ShiftColorEffect(color);
             GL11.glMatrixMode(GL11.GL_TEXTURE);
             GL11.glPushMatrix();
             float f8 = 0.125F;
             GL11.glScalef(f8, f8, f8);
             float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
             GL11.glTranslatef(f9, 0.0F, 0.0F);
             GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
             ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
             GL11.glPopMatrix();
             GL11.glPushMatrix();
             GL11.glScalef(f8, f8, f8);
             f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
             GL11.glTranslatef(-f9, 0.0F, 0.0F);
             GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
             ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
             GL11.glPopMatrix();
             GL11.glMatrixMode(GL11.GL_MODELVIEW);
             GL11.glDisable(GL11.GL_BLEND);
             GL11.glEnable(GL11.GL_LIGHTING);
             GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

}
