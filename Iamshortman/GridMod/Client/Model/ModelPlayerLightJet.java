package Iamshortman.GridMod.Client.Model;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ModelPlayerLightJet extends ModelPlayerBase
{	
	public ModelPlayerLightJet()
	{
		this(0.0F);
	}
	
	public ModelPlayerLightJet(float par1)
	{
		textureWidth = 64;
		textureHeight = 32;

		float f5 = 0.0625F;

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4F, -8F, -4F, 8, 8, 8, par1);
		Head.setRotationPoint(0F, 4F, 0F);
		Head.setTextureSize(64, 32);
		setRotation(Head, 0F, 0F, 0F);

		LegRight = new ModelRenderer(this, 0, 16);
		LegRight.addBox(-2F, 0F, -2F, 4, 12, 4, par1);
		LegRight.setRotationPoint(-2F, 7.3F, 10.7F);
		LegRight.setTextureSize(64, 32);
		setRotation(LegRight, 1.151917F, -0.3839724F, 0F);

		Body = new ModelRenderer(this, 16, 16);
		Body.addBox(-4F, 0F, -2F, 8, 12, 4, par1);
		Body.setRotationPoint(0F, 4.4F, -1F);
		Body.setTextureSize(64, 32);
		setRotation(Body, 1.343904F, 0F, 0F);

		LegLeft = new ModelRenderer(this, 0, 16);
		LegLeft.mirror = true;
		LegLeft.addBox(-2F, 0F, -2F, 4, 12, 4, par1);
		LegLeft.setRotationPoint(2F, 7.3F, 10.7F);
		LegLeft.setTextureSize(64, 32);
		setRotation(LegLeft, 1.151917F, 0.3839724F, 0F);

		ArmLeft = new ModelRenderer(this, 40, 16);
		ArmLeft.mirror = true;
		ArmLeft.addBox(-1F, -2F, -2F, 4, 11, 4, par1);
		ArmLeft.setRotationPoint(5F, 5F, 0F);
		ArmLeft.setTextureSize(64, 32);
		setRotation(ArmLeft, -1.291544F, 0.1919862F, 0F);

		ArmRight = new ModelRenderer(this, 40, 16);
		ArmRight.addBox(-3F, -2F, -2F, 4, 11, 4, par1);
		ArmRight.setRotationPoint(-5F, 5F, 0F);
		ArmRight.setTextureSize(64, 32);
		setRotation(ArmRight, -1.291544F, -0.1919862F, 0F);

		HeadGear = new ModelRenderer(this, 32, 0);
		HeadGear.addBox(-4F, -8F, -4F, 8, 8, 8,par1 + 0.5F);
		HeadGear.setRotationPoint(0F, 4F, 0F);
		HeadGear.setTextureSize(64, 32);
		setRotation(HeadGear, 0F, 0F, 0F);
	}
}
