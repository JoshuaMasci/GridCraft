package Iamshortman.GridMod.Client.Render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;


import Iamshortman.GridMod.Client.Model.ModelCodeRevisionTable;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;

import Iamshortman.GridMod.GridCraft;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderCodeRevisionTable extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	public ModelCodeRevisionTable model;

	public RenderCodeRevisionTable()
	{
		model = new ModelCodeRevisionTable();
	}

	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f5)
	{
		int i = tile.getBlockMetadata(); // this is for rotation
		int Rotation = 0;

		if (i == 0)
		{
			Rotation = 0;
		}

		if (i == 1)
		{
			Rotation = 90;
		}

		if (i == 2)
		{
			Rotation = 180;
		}

		if (i == 3)
		{
			Rotation = 270;
		}
		
		TileEntityCodeRevTable TileCode = (TileEntityCodeRevTable) tile;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
		GL11.glRotatef(Rotation, 0.0F, 1.0F, 0.0F); // rotate based on metadata
		GL11.glScalef(1.0F, -1F, -1F); // if you read this comment out this line
		ForgeHooksClient.bindTexture(GridCraft .CodeRev, 0);
		model.render(x, y, z,TileCode);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		int i = metadata;
		int Rotation = 0;

		if (i == 0)
		{
			Rotation = 0;
		}

		if (i == 1)
		{
			Rotation = 90;
		}

		if (i == 2)
		{
			Rotation = 180;
		}

		if (i == 3)
		{
			Rotation = 270;
		}
		int x = 0;
		int y = 0;
		int z = 0;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x + 0.5F, y + 1.4F, z + 0.5F);
		GL11.glRotatef(Rotation, 0.0F, 1.0F, 0.0F); // rotate based on metadata
		GL11.glScalef(1.0F, -1F, -1F); // if you read this comment out this line
		ForgeHooksClient.bindTexture(GridCraft.CodeRev, 0);
		model.render(x, y, z, null);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return GridCraft.CodeRevID;
	}

}
