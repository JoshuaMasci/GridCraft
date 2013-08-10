package Iamshortman.GridMod.Client.ItemRender;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.Item.ItemUpgradeHelper;
import Iamshortman.GridMod.Common.Item.TronItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;

public class ColoredEnchantmentItemRender implements IItemRenderer
{

	public double zLevel;

    public RenderEngine RenderEngine = Minecraft.getMinecraft().renderEngine;
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
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
			if(ItemUpgradeHelper.hasEffect(item))
			{
			this.renderEnchantmentEffect(item,ItemUpgradeHelper.getColor(item));
			}
			return;
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(item.getItem().getTextureFile()));

		Tessellator var5 = Tessellator.instance;
		int var6 = item.getIconIndex();
		float var7 = ((float) (var6 % 16 * 16) + 0.0F) / 256.0F;
		float var8 = ((float) (var6 % 16 * 16) + 15.99F) / 256.0F;
		float var9 = ((float) (var6 / 16 * 16) + 0.0F) / 256.0F;
		float var10 = ((float) (var6 / 16 * 16) + 15.99F) / 256.0F;

		ItemRenderer.renderItemIn2D(var5, var8, var9, var7, var10, 0.0625F);
		if(ItemUpgradeHelper.hasEffect(item))
		{
		this.renderEnchantmentEffect(item,ItemUpgradeHelper.getColor(item));
		}
	}

	protected void loadTexture(String par1Str)
	{
		RenderEngine var2 = Minecraft.getMinecraft().renderEngine;
		var2.bindTexture(var2.getTexture(par1Str));
	}

	protected void renderEnchantmentEffect(ItemStack item, EnumEnchantmentColor color)
	{
		Tessellator var8 = Tessellator.instance;
		RenderEngine RenderEngine = Minecraft.getMinecraft().renderEngine;
		if (item != null)
		{
			float var16 = 0.0625F;
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			RenderEngine.bindTexture(RenderEngine.getTexture("%blur%/misc/glint.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			this.ShiftColorEffect(color);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float var22 = 0.125F;
			GL11.glScalef(var22, var22, var22);
			float var23 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(var23, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, var16);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(var22, var22, var22);
			var23 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-var23, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
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
		if(color == EnumEnchantmentColor.Yellow)
		{
			GL11.glColor4f( 0.8F * var21, 0.8F * var21, 0.0F, 1.0F);
		}
		else if(color == EnumEnchantmentColor.Green)
		{
			GL11.glColor4f( 0.1F * var21, 0.9F * var21, 0.1F * var21, 1.0F);
		}
		else if(color == EnumEnchantmentColor.Red)
		{
			GL11.glColor4f( 0.8F * var21, 0.1F * var21, 0.1F * var21, 1.0F);
		}
		else if(color == EnumEnchantmentColor.Blue)
		{
			GL11.glColor4f( 0.1F * var21, 0.1F * var21, 0.8F * var21, 1.0F);
		}
		else if(color == EnumEnchantmentColor.Orange)
		{
			GL11.glColor4f( 1.0F * var21, 0.5F * var21, 0.1F * var21, 1.0F);
		}

	}

	public void func_77018_a(int par1, int par2, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 2; ++var6)
		{
			if (var6 == 0)
			{
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}

			if (var6 == 1)
			{
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}

			float var7 = 0.00390625F;
			float var8 = 0.00390625F;
			float var9 = (float) (Minecraft.getSystemTime() % (long) (3000 + var6 * 1873)) / (3000.0F + (float) (var6 * 1873)) * 256.0F;
			float var10 = 0.0F;
			Tessellator var11 = Tessellator.instance;
			float var12 = 4.0F;

			if (var6 == 1)
			{
				var12 = -1.0F;
			}

			var11.startDrawingQuads();
			var11.addVertexWithUV((double) (par2 + 0), (double) (par3 + par5), (double) this.zLevel, (double) ((var9 + (float) par5 * var12) * var7), (double) ((var10 + (float) par5) * var8));
			var11.addVertexWithUV((double) (par2 + par4), (double) (par3 + par5), (double) this.zLevel, (double) ((var9 + (float) par4 + (float) par5 * var12) * var7), (double) ((var10 + (float) par5) * var8));
			var11.addVertexWithUV((double) (par2 + par4), (double) (par3 + 0), (double) this.zLevel, (double) ((var9 + (float) par4) * var7), (double) ((var10 + 0.0F) * var8));
			var11.addVertexWithUV((double) (par2 + 0), (double) (par3 + 0), (double) this.zLevel, (double) ((var9 + 0.0F) * var7), (double) ((var10 + 0.0F) * var8));
			var11.draw();
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
        int var8 = item.getIconIndex();
        int var10;
        float var12;
        float var13;
        float var16;
        
        if (var8 >= 0)
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            RenderEngine.bindTexture(RenderEngine.getTexture(item.getItem().getTextureFile()));
            this.renderTexturedQuad(0, 0, var8 % 16 * 16, var8 / 16 * 16, 16, 16);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
	}

	public void renderInventoryEnchantment(ItemStack item , EnumEnchantmentColor color)
	{
        if (item != null)
        {
            GL11.glDepthFunc(GL11.GL_GREATER);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            RenderEngine.bindTexture(RenderEngine.getTexture("%blur%/misc/glint.png"));
            this.zLevel -= 50.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
            this.ShiftColorEffect(color);
            this.func_77018_a(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            this.zLevel += 50.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
	}
	
	public void renderTexturedQuad(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV((double) (par1 + 0), (double) (par2 + par6), (double) this.zLevel, (double) ((float) (par3 + 0) * var7), (double) ((float) (par4 + par6) * var8));
		var9.addVertexWithUV((double) (par1 + par5), (double) (par2 + par6), (double) this.zLevel, (double) ((float) (par3 + par5) * var7), (double) ((float) (par4 + par6) * var8));
		var9.addVertexWithUV((double) (par1 + par5), (double) (par2 + 0), (double) this.zLevel, (double) ((float) (par3 + par5) * var7), (double) ((float) (par4 + 0) * var8));
		var9.addVertexWithUV((double) (par1 + 0), (double) (par2 + 0), (double) this.zLevel, (double) ((float) (par3 + 0) * var7), (double) ((float) (par4 + 0) * var8));
		var9.draw();
	}

}
