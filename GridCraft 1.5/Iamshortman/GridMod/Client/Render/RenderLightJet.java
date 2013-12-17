package Iamshortman.GridMod.Client.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Iamshortman.GridMod.Client.Forge.ColorHelper;
import Iamshortman.GridMod.Client.Model.ModelLightJet;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightJet;
import Iamshortman.GridMod.Client.Model.TronModel;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Entity.TronEntity;
import Iamshortman.GridMod.Common.Handlers.TronConfig;

public class RenderLightJet extends RenderLightVehicle
{
	public float Yaw = 0;
	public float Pitch = 0;
	public float Roll = 0;

	public void doRender(EntityLightJet entity, double x, double y, double z, float f, float f1)
	{

		ModelLightJet jet = (ModelLightJet) entity.getModel();
		this.updateModel(jet, entity);

		if (entity.getPilot() != null)
		{

			GL11.glPushMatrix();

			EntityPlayer Player = (EntityPlayer) entity.riddenByEntity;

			if (!Player.username.equals(Minecraft.getMinecraft().thePlayer.username))
			{
				float par9 = Player.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
				this.renderPlayerLabel(Player, Player.username, x, y, z, (int) par9);
			}

			this.moveandrotation(jet, x, y, z);

			this.loadDownloadableImageTexture(Player.skinUrl, "/mob/char.png");
			RenderPlayerLightVehicle render = new RenderPlayerLightVehicle(entity);
			render.setRenderManager(renderManager);
			render.doRender(Player, x, y, z, f, f1);
			GL11.glPopMatrix();
		}

		if (TronConfig.SupermanMode)
		{
			return;
		}

		GL11.glPushMatrix();

		this.moveandrotation(jet, x, y, z);

		float ticksNeeded = 150;
		if (entity.ticksExisted < ticksNeeded - 70)
		{
			float time = ticksNeeded - entity.ticksExisted;
			GL11.glPushMatrix();
			float alpha = time / ticksNeeded;
			GL11.glAlphaFunc(516, alpha);
			loadTexture("/mods/GridCraft/textures/Dissolve.png");
			GL11.glColor4f(0, 0, 0, 1.0F);
			jet.render(entity);
			GL11.glAlphaFunc(516, 0.1F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();

		}
		else
		{
			loadTexture("/mods/GridCraft/textures/lightJet/LightJetBase.png");
			jet.render(entity);

		}
		// Renders Glow
		float var4 = 1.0F;
		char var5 = 61680;
		int var6 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
		loadTexture(entity.getGlowTexture());

		int color = entity.GetColor();
		GL11.glColor4f(ColorHelper.getRed(color), ColorHelper.getBlue(color), ColorHelper.getGreen(color), var4);
		jet.render(entity);

		GL11.glPopMatrix();

	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1)
	{
		this.doRender((EntityLightJet) entity, x, y, z, f, f1);
	}

	public static void RenderInMenu(Minecraft mc, int Color, ItemStack item)
	{
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);

		ModelLightJet jet = new ModelLightJet();
		GL11.glPushMatrix();

		mc.renderEngine.bindTexture("/mods/GridCraft/textures/lightJet/LightJetBase.png");
		jet.render(item);

		// Renders Glow
		float var4 = 1.0F;
		char var5 = 61680;
		int var6 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
		mc.renderEngine.bindTexture("/mods/GridCraft/textures/lightJet/LightJetGlow.png");

		GL11.glColor4f(ColorHelper.getRed(Color), ColorHelper.getBlue(Color), ColorHelper.getGreen(Color), var4);
		jet.render(item);

		GL11.glPopMatrix();

	}
}
