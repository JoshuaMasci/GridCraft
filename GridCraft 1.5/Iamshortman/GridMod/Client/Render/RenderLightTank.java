package Iamshortman.GridMod.Client.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Iamshortman.GridMod.Client.Forge.ColorHelper;
import Iamshortman.GridMod.Client.Model.ModelLightCycle;
import Iamshortman.GridMod.Client.Model.ModelLightTank;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightTank;
import Iamshortman.GridMod.Common.Entity.TronEntity;

public class RenderLightTank extends RenderLightVehicle
{

	public void doRender(EntityLightTank entity, double x, double y, double z, float f, float f1)
	{
		GL11.glPushMatrix();

		TronEntity tronEntity = (TronEntity) entity;
		ModelLightTank Tank =  new ModelLightTank();
		this.updateModel(Tank, entity);
		Tank.rotationYawTurret = entity.rotationYawTurret;
		Tank.rotationYawTurretSpeed = entity.rotationYawTurretSpeed;
		Tank.rotationPitchTurret = entity.rotationPitchTurret;
		Tank.rotationPitchTurretSpeed = entity.rotationPitchTurretSpeed;
		
		this.loadTexture("/mods/GridCraft/textures/lightTank/TextureMapBase.png");
		this.moveandrotation(Tank, x, y, z);
		Tank.renderBase(0.0625F);
		this.loadTexture("/mods/GridCraft/textures/lightTank/TextureMapTurret.png");
		//Translates the Turret 
		float transX = -22 * 0.0625F;
		float transY = -35 * 0.0625F;
		float transZ = 25 * 0.0625F;
		
		GL11.glTranslatef(transX, transY, transZ);
        float Rotation = Tank.getTurretRotationYawOffset();
        GL11.glRotatef(Rotation, 0.0F, 1.0F, 0.0F);
		Tank.renderTurret(0.0625F);
		
		GL11.glPopMatrix();
	}


	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1)
	{
		this.doRender((EntityLightTank) entity, x, y, z, f, f1);
	}

	public static void RenderInMenu(Minecraft mc, int color, ItemStack item)
	{

	}
}
