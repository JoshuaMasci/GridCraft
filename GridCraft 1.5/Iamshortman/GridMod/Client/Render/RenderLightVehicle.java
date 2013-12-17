package Iamshortman.GridMod.Client.Render;

import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Client.Forge.ColorHelper;
import Iamshortman.GridMod.Client.Model.TronModel;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Entity.TronEntity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public abstract class RenderLightVehicle extends Render
{
	public void updateModel(TronModel model, TronEntity entity)
	{
		if (entity.prevTime > model.entityPrevTime)
		{
			// entity was updated (usually at ~20 fps)
			model.entityPrevTime = entity.prevTime;

			model.rotationYaw = entity.rotationYaw;
			model.rotationYawSpeed = entity.rotationYawSpeed;

			model.rotationPitch = entity.rotationPitch;
			model.rotationPitchSpeed = entity.rotationPitchSpeed;

			model.rotationRoll = entity.rotationRoll;
			model.rotationRollSpeed = entity.rotationRollSpeed;
		}
		else if (model.prevTime - entity.prevTime > 100000000) // .1 sec
		{
			// entity is not updating, game may be paused
			model.rotationYawSpeed = 0f;
			model.rotationPitchSpeed = 0f;
			model.rotationRollSpeed = 0f;
		}
		model.update();
	}

	public void shiftColor(int color)
	{
		GL11.glColor4f(ColorHelper.getRed(color), ColorHelper.getBlue(color), ColorHelper.getGreen(color), 1.0F);
	}
	
	public void moveandrotation(TronModel model, double x, double y, double z)
	{
		// following translation is relative to player position
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(model.rotationYaw, 0.0F, -1.0F, 0.0F);
		GL11.glRotatef(model.rotationPitch + 180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(model.rotationRoll, 0.0F, 0.0F, 1.0F);
	}
	
	public void renderPlayerLabel(EntityLiving par1EntityLiving, String par2Str, double x, double y, double z, int dist)
	{
		double var10 = par1EntityLiving.getDistanceSqToEntity(this.renderManager.livingPlayer);

		if (var10 <= (double) (dist * dist))
		{
			FontRenderer var12 = this.getFontRendererFromRenderManager();
			float var13 = 2.0F;
			float var14 = 0.016666668F * var13;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.0F, (float) y + 1.0F, (float) z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-var14, -var14, var14);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Tessellator var15 = Tessellator.instance;
			byte var16 = 0;

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			var15.startDrawingQuads();
			int var17 = var12.getStringWidth(par2Str) / 2;
			var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			var15.addVertex((double) (-var17 - 1), (double) (-1 + var16), 0.0D);
			var15.addVertex((double) (-var17 - 1), (double) (8 + var16), 0.0D);
			var15.addVertex((double) (var17 + 1), (double) (8 + var16), 0.0D);
			var15.addVertex((double) (var17 + 1), (double) (-1 + var16), 0.0D);
			var15.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2, var16, 553648127);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2, var16, -1);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

}
