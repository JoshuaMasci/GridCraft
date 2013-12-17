package Iamshortman.GridMod.Client.Render;

import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Client.Model.ModelLightJet;
import Iamshortman.GridMod.Client.Model.ModelLightTrail;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightJet;
import Iamshortman.GridMod.Common.Entity.EntityLightTrail;
import Iamshortman.GridMod.Common.Entity.EntityLightTrailPart;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderLightTrail extends Render
{

	public void doLightTrailRender(EntityLightTrail entity, double x, double y, double z, float f, float f1)
	{
		// following translation is relative to player position

		
		for(int i = 0; i < entity.Trails.length; i++)
		{
			GL11.glPushMatrix();
			if(entity.Trails[i] != null)
			{
				renderTrail(x, y, z, entity.Trails[i]);
			}
			GL11.glPopMatrix();
		}
		

		GL11.glPopMatrix();

	}

	private void renderTrail(double x, double y, double z, EntityLightTrailPart entityLightTrailPart)
	{
		double correctX = x + entityLightTrailPart.posX;
		double correctY = y + entityLightTrailPart.posY;
		double correctZ = z + entityLightTrailPart.posZ;
		
		GL11.glTranslatef((float) correctX, (float) correctY, (float) correctZ);

		GL11.glRotatef(entityLightTrailPart.rotationYaw, 0.0F, -1.0F, 0.0F);
		GL11.glRotatef(entityLightTrailPart.rotationPitch + 180, 1.0F, 0.0F, 0.0F);
		
		float var4 = 1.0F;
		char var5 = 61680;
		int var6 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
		new ModelLightTrail().render();
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
		this.doLightTrailRender((EntityLightTrail) entity, d0, d1, d2, f, f1);
	}

}
