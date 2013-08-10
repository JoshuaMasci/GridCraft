package Iamshortman.GridMod.Client.Render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Client.Model.ModelLaser;
import Iamshortman.GridMod.Common.Entity.TronEntity;

public class RenderLaser extends Render
{
	public ModelLaser model;
	public RenderLaser()
	{
		model = new ModelLaser();

	}
	
    @Override
    public void doRender(Entity entityArg, double x, double y, double z, float yaw, float deltaTime)
    {
        TronEntity entity = (TronEntity) entityArg;
        
        GL11.glPushMatrix();
        
        // following translation is relative to player position
        GL11.glTranslatef((float)x, (float)y, (float)z);
        
        
        //model.paused = entity.sidedHelper.isPaused();
        
        if (entity.prevTime > model.entityPrevTime)
        {
            // entity was updated (usually at ~20 fps)
            model.entityPrevTime = entity.prevTime;
	        
	        model.rotationYaw = entity.rotationYaw;
	        model.rotationYawSpeed = entity.rotationYawSpeed;
	        
	        model.rotationPitch = entity.rotationPitch;
	        model.rotationPitchSpeed = entity.rotationPitchSpeed;
	        
	        model.rotationRoll  = entity.rotationRoll;
	        model.rotationRollSpeed  = entity.rotationRollSpeed;
        }
        else if (model.prevTime - entity.prevTime > 100000000) // .1 sec
        {
            // entity is not updating, game may be paused
	        model.rotationYawSpeed = 0f;
	        model.rotationPitchSpeed = 0f;
	        model.rotationRollSpeed = 0f;
        }
        
        TronEntity tronEntity = (TronEntity) entity;
        loadTexture(tronEntity.getTextureFromColor());
        
		//Renders Glow
		GL11.glBlendFunc(1, 1);
		int j = 61680;
		int k = j % 0x10000;
		int l = j / 0x10000;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,(float)k / 1.0F ,(float)l / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        model.render();
        
        GL11.glPopMatrix();
    }
}

