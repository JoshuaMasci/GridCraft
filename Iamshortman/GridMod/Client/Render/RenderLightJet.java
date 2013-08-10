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
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Iamshortman.GridMod.Client.Model.ModelLightJet;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightJet;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Entity.TronEntity;
import Iamshortman.GridMod.Common.Handlers.TronConfig;

public class RenderLightJet extends RenderLightVehicle
{
	
	public void doRender(Entity entity, double x, double y, double z,float f, float f1)
    {
        
        ModelLightJet jet = new ModelLightJet();
        TronEntity tronEntity = (TronEntity) entity;
        
		if(entity.riddenByEntity != null)
		{
            
			GL11.glPushMatrix();
            
			EntityPlayer Player = (EntityPlayer) entity.riddenByEntity;
			
        	if(Player.username.equals(Minecraft.getMinecraft().thePlayer.username))
        	{

        	}
        	else
        	{
        		float par9 = Player.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
        		this.renderPlayerLabel(Player, Player.username , x, y, z, (int) par9);
        	}
			
	        // following translation is relative to player position
	        GL11.glTranslatef((float)x, (float)y, (float)z);
	        GL11.glRotatef(entity.rotationYaw, 0.0F, -1.0F ,0.0F);
	        GL11.glRotatef(entity.rotationPitch + 180, 1.0F, 0.0F, 0.0F);
	       
			this.loadDownloadableImageTexture(Player.skinUrl, "/mob/char.png");
			RenderPlayerLightVehicle render = new RenderPlayerLightVehicle(entity);
			render.setRenderManager(renderManager);
			render.doRender(Player, x, y, z, f, f1);
			GL11.glPopMatrix();
		}
        
		if(TronConfig.SupermanMode)
		{
			return;
		}
		
        GL11.glPushMatrix();
        // following translation is relative to player position
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(entity.rotationYaw, 0.0F, -1.0F ,0.0F);
        GL11.glRotatef(entity.rotationPitch + 180, 1.0F, 0.0F, 0.0F);
        
		float ticksNeeded = 150;
		if (entity.ticksExisted < ticksNeeded - 70)
		{
			float time = ticksNeeded - entity.ticksExisted;
			GL11.glPushMatrix();
			float alpha = time / ticksNeeded;
			GL11.glAlphaFunc(516, alpha);
			loadTexture("/Tron/Dissolve.png");
			GL11.glColor4f(0, 0, 0, 1.0F);
			jet.render(entity);
			GL11.glAlphaFunc(516, 0.1F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			
		}
		else
		{
			loadTexture("/Tron/lightJet/LightJetBase.png");
			jet.render(entity);
			
		}
		//Renders Glow
		float var4 = 1.0F;
        char var5 = 61680;
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
        String str = tronEntity.getTextureFromColor();
        if(!str.equals("!"))
        {
    		loadTexture(str);
    		jet.render(entity);
        }
		GL11.glPopMatrix();
			
    }
	
}
