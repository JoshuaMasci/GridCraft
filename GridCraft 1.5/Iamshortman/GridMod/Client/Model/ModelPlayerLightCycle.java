package Iamshortman.GridMod.Client.Model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelPlayerLightCycle extends ModelPlayerBase
{

	public ModelPlayerLightCycle()
	{
		this(0.0F);
	}

	public ModelPlayerLightCycle(float par1)
	{
		textureWidth = 64;
		textureHeight = 32;

		ArmRight = new ModelRenderer(this, 40, 16);
		ArmRight.addBox(-3F, -2F, -2F, 4, 12, 4, par1);
		ArmRight.setRotationPoint(-5F, 7F, 6F);
		ArmRight.setTextureSize(64, 32);
		setRotation(ArmRight, -0.8585054F, -0.0174533F, 0.4665191F);

		Body = new ModelRenderer(this, 16, 16);
		Body.addBox(-4F, 0F, -2F, 8, 12, 4, par1);
		Body.setRotationPoint(0F, 7F, 4F);
		Body.setTextureSize(64, 32);
		setRotation(Body, 1.343904F, 0F, 0F);

		LegRight = new ModelRenderer(this, 0, 16);
		LegRight.addBox(-2F, 0F, -2F, 4, 12, 4, par1);
		LegRight.setRotationPoint(-2.5F, 10F, 16F);
		LegRight.setTextureSize(64, 32);
		setRotation(LegRight, 1.239184F, -0.5235988F, 0.4624552F);

		LegLeft = new ModelRenderer(this, 0, 16);
		LegLeft.mirror = true;
		LegLeft.addBox(-2F, 0F, -2F, 4, 12, 4, par1);
		LegLeft.setRotationPoint(2.5F, 10F, 16F);
		LegLeft.setTextureSize(64, 32);
		setRotation(LegLeft, 1.239184F, 0.5235988F, -0.4624499F);

		ArmLeft = new ModelRenderer(this, 40, 16);
		ArmLeft.mirror = true;
		ArmLeft.addBox(-1F, -2F, -2F, 4, 12, 4, par1);
		ArmLeft.setRotationPoint(5F, 7F, 6F);
		ArmLeft.setTextureSize(64, 32);
		setRotation(ArmLeft, -0.8585054F, -0.0174533F, -0.4665191F);

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4F, -8F, -4F, 8, 8, 8, par1);
		Head.setRotationPoint(0F, 6F, 4F);
		Head.setTextureSize(64, 32);
		setRotation(Head, 0F, 0F, 0F);

		HeadGear = new ModelRenderer(this, 32, 0);
		HeadGear.addBox(-4F, -8F, -4F, 8, 8, 8, par1 + 0.5F);
		HeadGear.setRotationPoint(0F, 6F, 4F);
		HeadGear.setTextureSize(64, 32);
		setRotation(HeadGear, 0F, 0F, 0F);
	}

}
