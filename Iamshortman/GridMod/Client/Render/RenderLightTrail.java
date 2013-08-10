package Iamshortman.GridMod.Client.Render;

import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Client.Model.ModelLightJet;
import Iamshortman.GridMod.Client.Model.ModelLightTrail;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightJet;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderLightTrail extends Render
{

	public void doRender(Entity entity, double x, double y, double z,float f, float f1)
    {
        GL11.glPushMatrix();
        
        // following translation is relative to player position
        GL11.glTranslatef((float)x, (float)y, (float)z);
        
        loadTexture("/Tron/LightTrail1.png");
		new ModelLightTrail().render(entity);
		
		GL11.glPopMatrix();
		
    }

}
