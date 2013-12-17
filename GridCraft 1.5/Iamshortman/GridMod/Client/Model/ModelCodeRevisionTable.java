package Iamshortman.GridMod.Client.Model;

import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;

public class ModelCodeRevisionTable extends ModelBase
{
	// fields
	ModelRenderer Base1;
	ModelRenderer Base2;
	ModelRenderer Base3;
	ModelRenderer Top1;
	ModelRenderer Top2;
	ModelRenderer Top3;
	ModelRenderer Top4;
	ModelRenderer Top5;
	public ModelRenderer Screen;
	ModelRenderer Top6_1;
	ModelRenderer Top6_2;
	ModelRenderer Top6_3;
	ModelRenderer Top6_4;

	public ModelCodeRevisionTable()
	{
		textureWidth = 64;
		textureHeight = 64;

		Base1 = new ModelRenderer(this, 0, 0);
		Base1.addBox(-6F, 0F, -6F, 12, 2, 12);
		Base1.setRotationPoint(0F, 22F, 0F);
		Base1.setTextureSize(64, 32);
		Base1.mirror = true;
		setRotation(Base1, 0F, 0F, 0F);
		Base2 = new ModelRenderer(this, 0, 14);
		Base2.addBox(-5F, -2F, -5F, 10, 2, 10);
		Base2.setRotationPoint(0F, 22F, 0F);
		Base2.setTextureSize(64, 32);
		Base2.mirror = true;
		setRotation(Base2, 0F, 0F, 0F);
		Base3 = new ModelRenderer(this, 0, 26);
		Base3.addBox(-4F, 0F, -4F, 8, 9, 8);
		Base3.setRotationPoint(0F, 11F, 0F);
		Base3.setTextureSize(64, 32);
		Base3.mirror = true;
		setRotation(Base3, 0F, 0F, 0F);
		Top1 = new ModelRenderer(this, 0, 43);
		Top1.addBox(-6F, 0F, -6F, 12, 2, 12);
		Top1.setRotationPoint(0F, 9F, 0F);
		Top1.setTextureSize(64, 32);
		Top1.mirror = true;
		setRotation(Top1, 0F, 0F, 0F);
		Top2 = new ModelRenderer(this, 48, 0);
		Top2.addBox(-3F, 0F, -3F, 3, 5, 3);
		Top2.setRotationPoint(-4F, 8F, -4F);
		Top2.setTextureSize(64, 32);
		Top2.mirror = true;
		setRotation(Top2, 0F, 0F, 0F);
		Top3 = new ModelRenderer(this, 48, 0);
		Top3.addBox(0F, 0F, -3F, 3, 5, 3);
		Top3.setRotationPoint(4F, 8F, -4F);
		Top3.setTextureSize(64, 32);
		Top3.mirror = true;
		setRotation(Top3, 0F, 0F, 0F);
		Top4 = new ModelRenderer(this, 48, 0);
		Top4.addBox(0F, 0F, 0F, 3, 5, 3);
		Top4.setRotationPoint(4F, 8F, 4F);
		Top4.setTextureSize(64, 32);
		Top4.mirror = true;
		setRotation(Top4, 0F, 0F, 0F);
		Top5 = new ModelRenderer(this, 48, 0);
		Top5.addBox(-3F, 0F, 0F, 3, 5, 3);
		Top5.setRotationPoint(-4F, 8F, 4F);
		Top5.setTextureSize(64, 32);
		Top5.mirror = true;
		setRotation(Top5, 0F, 0F, 0F);
		Screen = new ModelRenderer(this, 32, 34);
		Screen.addBox(-4F, 0F, 0F, 8, 1, 8);
		Screen.setRotationPoint(0F, 9F, -1F);
		Screen.setTextureSize(64, 64);
		Screen.mirror = true;
		setRotation(Screen, 0F, 0F, 0F);
		Top6_1 = new ModelRenderer(this, 0, 58);
		Top6_1.addBox(-4F, 0F, 0F, 8, 2, 0);
		Top6_1.setRotationPoint(0F, 9F, -4F);
		Top6_1.setTextureSize(64, 64);
		Top6_1.mirror = true;
		setRotation(Top6_1, 0F, 0F, 0F);
		Top6_2 = new ModelRenderer(this, 0, 58);
		Top6_2.addBox(-4F, 0F, 0F, 8, 2, 0);
		Top6_2.setRotationPoint(0F, 9F, 4F);
		Top6_2.setTextureSize(64, 64);
		Top6_2.mirror = true;
		setRotation(Top6_2, 0F, 0F, 0F);
		Top6_3 = new ModelRenderer(this, 0, 58);
		Top6_3.addBox(-4F, 0F, 0F, 8, 2, 0);
		Top6_3.setRotationPoint(-4F, 9F, 0F);
		Top6_3.setTextureSize(64, 64);
		Top6_3.mirror = true;
		setRotation(Top6_3, 0F, -1.570796F, 0F);
		Top6_4 = new ModelRenderer(this, 0, 58);
		Top6_4.addBox(-4F, 0F, 0F, 8, 2, 0);
		Top6_4.setRotationPoint(4F, 9F, 0F);
		Top6_4.setTextureSize(64, 64);
		Top6_4.mirror = true;
		setRotation(Top6_4, 0F, -1.570796F, 0F);
	}

	public void render(double x, double y, double z, TileEntityCodeRevTable tileCode)
	{

		Float f5 = 0.0625F;

		Base1.render(f5);
		Base2.render(f5);
		Base3.render(f5);
		Top1.render(f5);
		Top2.render(f5);
		Top3.render(f5);
		Top4.render(f5);
		Top5.render(f5);
		Top6_1.render(f5);
		Top6_2.render(f5);
		Top6_3.render(f5);
		Top6_4.render(f5);
		if (tileCode != null)
		{
			Screen.rotationPointZ = tileCode.getScreenLoc();
			Screen.rotateAngleX = tileCode.getScreenRot();
		}
		Screen.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
