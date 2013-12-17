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
import Iamshortman.GridMod.Client.Model.ModelPlayerLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.TronEntity;

public class RenderLightCycle extends RenderLightVehicle
{

	public void doRender(Iamshortman.GridMod.Common.Entity.EntityLightCycle entity, double x, double y, double z, float f, float f1)
	{
		GL11.glPushMatrix();

		TronEntity tronEntity = (TronEntity) entity;
		ModelLightCycle cycle = (ModelLightCycle) tronEntity.Model;
		this.updateModel(cycle, entity);
		
		if (entity.riddenByEntity != null)
		{

			GL11.glPushMatrix();

			EntityPlayer Player = (EntityPlayer) entity.riddenByEntity;

			this.moveandrotation(cycle, x, y - 0.2, z);
			
			if (!Player.username.equals(Minecraft.getMinecraft().thePlayer.username))
			{
				float par9 = Player.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
				this.renderPlayerLabel(Player, Player.username, 0, 0, 0, (int) par9);
			}

			this.loadDownloadableImageTexture(Player.skinUrl, "/mob/char.png");
			RenderPlayerLightVehicle render = new RenderPlayerLightVehicle(entity);
			render.setRenderManager(renderManager);
			render.doRender(Player, x, y, z, f, f1);
			GL11.glPopMatrix();
		}

		this.moveandrotation(cycle, x, y, z);

		float ticksNeeded = 100;
		if (entity.ticksExisted < ticksNeeded - 45)
		{
			float time = ticksNeeded - entity.ticksExisted;
			GL11.glPushMatrix();
			float alpha = time / ticksNeeded;

			GL11.glAlphaFunc(516, alpha);
			loadTexture("/mods/GridCraft/textures/Dissolve.png");
			GL11.glColor4f(0, 0, 0, 1.0F);
			cycle.prerender(entity);
			GL11.glAlphaFunc(516, 0.1F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();

			// Renders Glow
			GL11.glBlendFunc(1, 1);
			int j = 61680;
			int k = j % 0x10000;
			int l = j / 0x10000;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			loadTexture(tronEntity.getGlowTexture());
			int color = entity.GetColor();
			//System.out.println("Color: " + color + " Red: " + ColorHelper.getRed(color) + " Blue: " + ColorHelper.getBlue(color) + " Green: " + ColorHelper.getGreen(color));
			GL11.glColor4f(ColorHelper.getRed(color), ColorHelper.getBlue(color), ColorHelper.getGreen(color), 1.0F);
			cycle.prerender(entity);
		}
		else
		{
			// render base
			loadTexture(entity.getBaseTexture());
			new ModelLightCycle().prerender(entity);

			// Renders Glow
			GL11.glBlendFunc(1, 1);
			int j = 61680;
			int k = j % 0x10000;
			int l = j / 0x10000;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			loadTexture(tronEntity.getGlowTexture());
			int color = entity.GetColor();
			//System.out.println("Color: " + color + " Red: " + ColorHelper.getRed(color) + " Blue: " + ColorHelper.getBlue(color) + " Green: " + ColorHelper.getGreen(color));
			GL11.glColor4f(ColorHelper.getRed(color), ColorHelper.getBlue(color), ColorHelper.getGreen(color), 1.0F);
			cycle.render(entity);
		}

		GL11.glPopMatrix();
	}


	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1)
	{
		this.doRender((EntityLightCycle) entity, x, y, z, f, f1);
	}

	public static void RenderInMenu(Minecraft mc, int color, ItemStack item)
	{
		ModelLightCycle cycle = new ModelLightCycle();
		// render base
		mc.renderEngine.bindTexture("/mods/GridCraft/textures/lightCycle/LightCycleBase.png");
		new ModelLightCycle().renderInMenu(null);

		// Renders Glow
		GL11.glBlendFunc(1, 1);
		int j = 61680;
		int k = j % 0x10000;
		int l = j / 0x10000;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/GridCraft/textures/lightCycle/LightCycleGlow.png");
		GL11.glColor4f(ColorHelper.getRed(color), ColorHelper.getBlue(color), ColorHelper.getGreen(color), 1.0F);
		cycle.renderInMenu(item);
	}
}
