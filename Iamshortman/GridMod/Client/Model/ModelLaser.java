package Iamshortman.GridMod.Client.Model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;


public class ModelLaser extends ModelBase
{
    public ModelRenderer missile;
    
    public float rollRadPerSec = 5f;
    
    final public float RAD_PER_DEG = 00.01745329f;
    final public float PI          = 03.14159265f;

    public boolean visible = true;
    
    public boolean paused;
    
    public float deltaTime;
    public long prevTime;
    public long entityPrevTime;
    
    public float rotationYaw;
    public float rotationYawSpeed;
    
    public float rotationPitch;
    public float rotationPitchSpeed;
    
    public float rotationRoll;
    public float rotationRollSpeed;
    
    public String renderTexture;
    
    RenderManager renderManager;
    
    int updateCount;
    
    public ModelLaser()
    {       
        renderTexture = "/Tron/Laser.png";

        float x_length = 32f;
        float z_width  = 3f;
        float y_height = 3f;

        missile = new ModelRenderer(this, 0, 0);
        missile.addBox(-x_length/2f, -y_height/2f, -z_width/2f, (int)x_length, (int)y_height, (int)z_width);
        missile.setRotationPoint(0f, 0f, 0f);
    }

    public void render()
    {
        update();
        
        if (!visible) return;
        
        // spiral
        missile.rotateAngleX += deltaTime * rollRadPerSec;
        if (missile.rotateAngleX > 2*PI) missile.rotateAngleX -= 2*PI;
        
        //float scale = .0625f; // original size
        //float scale = .04f;
        //missile.render(scale);
        missile.render(.04f);
    }
    
    public void update()
    {
        updateCount++;
        
	    long time = System.nanoTime();
        
	    deltaTime = ((float)(time - prevTime)) / 1000000000f; // divide by a billion to convert to sec
	    
	    // this happens when we haven't rendered in awhile (e.g. out of view)
        if (deltaTime > .03f) deltaTime = .03f; // default to ~30 fps
	    
        if (paused)
        {
            // do nothing
        }
        else if (entityPrevTime > prevTime)
        {
            // if the entity was updated more recently than the model
            // use adjusted delta (leave deltaTime alone so subclass models can update
            // parts that may be independent of entity, e.g. helicopter rotor)
            float adjustedDeltaTime = ((float)(time - entityPrevTime)) / 1000000000f; // divide by a billion to convert to sec
            rotationYaw += rotationYawSpeed * adjustedDeltaTime;
            rotationPitch += rotationPitchSpeed * adjustedDeltaTime;
            rotationRoll += rotationRollSpeed * adjustedDeltaTime;
        }
        else 
        {
            rotationYaw   += rotationYawSpeed   * deltaTime;
            rotationPitch += rotationPitchSpeed * deltaTime;
            rotationRoll  += rotationRollSpeed  * deltaTime;
        }
	    prevTime = time;
        
        GL11.glRotatef(-90f - rotationYaw, 0.0f, 1.0f, 0.0f); // why -90 adjustment?
        
        GL11.glRotatef(-rotationPitch, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(-rotationRoll, 1.0f, 0.0f, 0.0f);
        
        GL11.glScalef(-1f, -1f, 1f);
    }
}
