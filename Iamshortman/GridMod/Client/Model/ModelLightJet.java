
package Iamshortman.GridMod.Client.Model;
import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Common.Entity.EntityLightJet;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;


public class ModelLightJet extends ModelBase
{
	public Entity Jet;
	
  //fields
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer BackBodyleft;
    ModelRenderer BackBodyright;
    ModelRenderer wingmountleft;
    ModelRenderer wingmountright;
    ModelRenderer rightwing;
    ModelRenderer leftwing;
    ModelRenderer rightwing1;
    ModelRenderer leftwing1;
    ModelRenderer backrightwingbottom;
    ModelRenderer backleftwing;
    ModelRenderer backhandleleft;
    ModelRenderer Fronthandleleft;
    ModelRenderer Fronthandleright;
    ModelRenderer backhandleright;
    ModelRenderer WingEndleftFront;
    ModelRenderer WingEndleftback;
    ModelRenderer WingEndrightFront;
    ModelRenderer WingEndrightback;
    ModelRenderer backplate;
    ModelRenderer backleftwingbottom;
    ModelRenderer WingEndleftbackbottom;
    ModelRenderer backrightwing;
    ModelRenderer WingEndrightbackbottom;
    ModelRenderer LeftWingGun;
    ModelRenderer RightWingGun;
  
  public ModelLightJet()
  {
    textureWidth = 256;
    textureHeight = 128;
    
      body1 = new ModelRenderer(this, 0, 0);
      body1.addBox(-3F, 0F, 0F, 6, 8, 12);
      body1.setRotationPoint(0F, 11F, -8F);
      body1.setTextureSize(256, 128);
      body1.mirror = true;
      setRotation(body1, 0F, 0F, 0F);
      
      body2 = new ModelRenderer(this, 49, 0);
      body2.addBox(-3F, -8F, 0F, 6, 7, 10);
      body2.setRotationPoint(0F, 19.5F, 4.5F);
      body2.setTextureSize(256, 128);
      setRotation(body2, 0.4712389F, 0F, 0F);
      
      body3 = new ModelRenderer(this, 98, 0);
      body3.addBox(-3F, 0F, 0F, 6, 10, 17);
      body3.setRotationPoint(0F, 5F, 9F);
      body3.setTextureSize(256, 128);
      setRotation(body3, 0F, 0F, 0F);
      
      BackBodyleft = new ModelRenderer(this, 151, 10);
      BackBodyleft.addBox(0F, -1F, 0F, 6, 2, 8);
      BackBodyleft.setRotationPoint(2F, 8F, 16F);
      BackBodyleft.setTextureSize(256, 128);
      setRotation(BackBodyleft, 0F, 0F, -0.3316126F);
      
      BackBodyright = new ModelRenderer(this, 151, 10);
      BackBodyright.mirror = true;
      BackBodyright.addBox(-6F, -1F, 0F, 6, 2, 8);
      BackBodyright.setRotationPoint(-2F, 8F, 16F);
      BackBodyright.setTextureSize(256, 128);
      setRotation(BackBodyright, 0F, 0F, 0.3316126F);
      
      wingmountleft = new ModelRenderer(this, 0, 50);
      wingmountleft.mirror = true;
      wingmountleft.addBox(-3F, 0F, 0F, 3, 3, 45);
      wingmountleft.setRotationPoint(-7F, 5F, -19F);
      wingmountleft.setTextureSize(256, 128);
      setRotation(wingmountleft, 0F, 0F, 0F);
      
      wingmountright = new ModelRenderer(this, 0, 50);
      wingmountright.addBox(0F, 0F, 0F, 3, 3, 45);
      wingmountright.setRotationPoint(7F, 5F, -19F);
      wingmountright.setTextureSize(256, 128);
      setRotation(wingmountright, 0F, 0F, 0F);
      
      rightwing = new ModelRenderer(this, 0, 116);
      rightwing.mirror = true;
      rightwing.addBox(-33F, 0F, 0F, 33, 2, 10);
      rightwing.setRotationPoint(-16F, 5F, -19F);
      rightwing.setTextureSize(256, 128);
      setRotation(rightwing, 0F, 0F, 0F);
      
      leftwing = new ModelRenderer(this, 0, 116);
      leftwing.addBox(0F, 0F, 0F, 33, 2, 10);
      leftwing.setRotationPoint(16F, 5F, -19F);
      leftwing.setTextureSize(256, 128);
      setRotation(leftwing, 0F, 0F, 0F);
      rightwing1 = new ModelRenderer(this, 0, 103);
      
      rightwing1.mirror = true;
      rightwing1.addBox(-19F, 0F, -10F, 19, 2, 10);
      rightwing1.setRotationPoint(-9F, 5F, -5F);
      rightwing1.setTextureSize(256, 128);
      setRotation(rightwing1, 0F, -0.2094395F, 0F);
      
      leftwing1 = new ModelRenderer(this, 0, 103);
      leftwing1.addBox(0F, 0F, -10F, 19, 2, 10);
      leftwing1.setRotationPoint(9F, 5F, -5F);
      leftwing1.setTextureSize(256, 128);
      setRotation(leftwing1, 0F, 0.2094395F, 0F);
      
      backrightwingbottom = new ModelRenderer(this, 151, 0);
      backrightwingbottom.mirror = true;
      backrightwingbottom.addBox(-15F, 0F, -5F, 15, 1, 5);
      backrightwingbottom.setRotationPoint(-9F, 7F, 22F);
      backrightwingbottom.setTextureSize(256, 128);
      setRotation(backrightwingbottom, 0F, -0.2094395F, -0.4014257F);
      
      backleftwing = new ModelRenderer(this, 151, 0);
      backleftwing.addBox(0F, 0F, -5F, 15, 1, 5);
      backleftwing.setRotationPoint(9F, 5F, 22F);
      backleftwing.setTextureSize(256, 128);
      setRotation(backleftwing, 0F, 0.2094395F, -0.4014257F);
      
      backhandleleft = new ModelRenderer(this, 0, 30);
      backhandleleft.addBox(0F, 0F, 0F, 3, 1, 1);
      backhandleleft.setRotationPoint(3F, 12F, 18F);
      backhandleleft.setTextureSize(256, 128);
      setRotation(backhandleleft, 0F, 0F, 0F);
      
      Fronthandleleft = new ModelRenderer(this, 0, 30);
      Fronthandleleft.addBox(0F, 0F, 0F, 3, 1, 1);
      Fronthandleleft.setRotationPoint(4F, 6F, -9F);
      Fronthandleleft.setTextureSize(256, 128);
      setRotation(Fronthandleleft, 0F, 0F, 0F);
      
      Fronthandleright = new ModelRenderer(this, 0, 30);
      Fronthandleright.mirror = true;
      Fronthandleright.addBox(-3F, 0F, 0F, 3, 1, 1);
      Fronthandleright.setRotationPoint(-4F, 6F, -9F);
      Fronthandleright.setTextureSize(256, 128);
      setRotation(Fronthandleright, 0F, 0F, 0F);
      
      backhandleright = new ModelRenderer(this, 0, 30);
      backhandleright.mirror = true;
      backhandleright.addBox(-3F, 0F, 0F, 3, 1, 1);
      backhandleright.setRotationPoint(-3F, 12F, 18F);
      backhandleright.setTextureSize(256, 128);
      setRotation(backhandleright, 0F, 0F, 0F);
      
      WingEndleftFront = new ModelRenderer(this, 27, 23);
      WingEndleftFront.addBox(0F, 0F, 0F, 2, 2, 13);
      WingEndleftFront.setRotationPoint(49F, 5F, -22F);
      WingEndleftFront.setTextureSize(256, 128);
      setRotation(WingEndleftFront, 0F, 0F, 0F);
      
      WingEndleftback = new ModelRenderer(this, 65, 24);
      WingEndleftback.addBox(-2F, 0F, 0F, 2, 2, 9);
      WingEndleftback.setRotationPoint(23F, -1F, 10F);
      WingEndleftback.setTextureSize(256, 128);
      setRotation(WingEndleftback, 0F, 0F, 0F);
      
      WingEndrightFront = new ModelRenderer(this, 27, 23);
      WingEndrightFront.mirror = true;
      WingEndrightFront.addBox(-2F, 0F, 0F, 2, 2, 13);
      WingEndrightFront.setRotationPoint(-49F, 5F, -22F);
      WingEndrightFront.setTextureSize(256, 128);
      setRotation(WingEndrightFront, 0F, 0F, 0F);
      
      WingEndrightback = new ModelRenderer(this, 65, 24);
      WingEndrightback.mirror = true;
      WingEndrightback.addBox(0F, 0F, 0F, 2, 2, 9);
      WingEndrightback.setRotationPoint(-23F, -1F, 10F);
      WingEndrightback.setTextureSize(256, 128);
      setRotation(WingEndrightback, 0F, 0F, 0F);
      
      backplate = new ModelRenderer(this, 208, 0);
      backplate.addBox(-3F, 0F, 0F, 6, 1, 13);
      backplate.setRotationPoint(0F, 2F, 5F);
      backplate.setTextureSize(256, 128);
      setRotation(backplate, -0.1919862F, 0F, 0F);
      
      backleftwingbottom = new ModelRenderer(this, 151, 0);
      backleftwingbottom.addBox(0F, 0F, -5F, 15, 1, 5);
      backleftwingbottom.setRotationPoint(9F, 7F, 22F);
      backleftwingbottom.setTextureSize(256, 128);
      setRotation(backleftwingbottom, 0F, 0.2094395F, 0.4014257F);
      
      WingEndleftbackbottom = new ModelRenderer(this, 65, 24);
      WingEndleftbackbottom.addBox(-2F, 0F, 0F, 2, 2, 9);
      WingEndleftbackbottom.setRotationPoint(23F, 12F, 10F);
      WingEndleftbackbottom.setTextureSize(256, 128);
      setRotation(WingEndleftbackbottom, 0F, 0F, 0F);
      
      backrightwing = new ModelRenderer(this, 151, 0);
      backrightwing.mirror = true;
      backrightwing.addBox(-15F, 0F, -5F, 15, 1, 5);
      backrightwing.setRotationPoint(-9F, 5F, 22F);
      backrightwing.setTextureSize(256, 128);
      setRotation(backrightwing, 0F, -0.2094395F, 0.4014257F);
      
      WingEndrightbackbottom = new ModelRenderer(this, 65, 24);
      WingEndrightbackbottom.mirror = true;
      WingEndrightbackbottom.addBox(0F, 0F, 0F, 2, 2, 9);
      WingEndrightbackbottom.setRotationPoint(-23F, 12F, 10F);
      WingEndrightbackbottom.setTextureSize(256, 128);
      setRotation(WingEndrightbackbottom, 0F, 0F, 0F);
      
      LeftWingGun = new ModelRenderer(this, 117, 44);
      LeftWingGun.addBox(0F, 0F, 0F, 2, 2, 7);
      LeftWingGun.setRotationPoint(30F, 6F, -22F);
      LeftWingGun.setTextureSize(256, 128);
      setRotation(LeftWingGun, 0F, 0F, 0F);
      
      RightWingGun = new ModelRenderer(this, 117, 44);
      RightWingGun.mirror = true;
      RightWingGun.addBox(-2F, 0F, 0F, 2, 2, 7);
      RightWingGun.setRotationPoint(-30F, 6F, -22F);
      RightWingGun.setTextureSize(256, 128);
      setRotation(RightWingGun, 0F, 0F, 0F);
  }
  
  public void render(Entity entity)
  {
	EntityLightJet jet =  (EntityLightJet) entity;
	float f5 = 0.0625F;
    body1.render(f5);
    body2.render(f5);
    body3.render(f5);
    BackBodyleft.render(f5);
    BackBodyright.render(f5);
    wingmountleft.render(f5);
    wingmountright.render(f5);
    rightwing.render(f5);
    leftwing.render(f5);
    rightwing1.render(f5);
    leftwing1.render(f5);
    backleftwing.render(f5);
    backhandleleft.render(f5);
    Fronthandleleft.render(f5);
    Fronthandleright.render(f5);
    backhandleright.render(f5);
    WingEndleftFront.render(f5);
    WingEndleftback.render(f5);
    WingEndrightFront.render(f5);
    WingEndrightback.render(f5);
    backplate.render(f5);
    backrightwing.render(f5);

    if(jet.quadWingUpgrade)
    {
        backleftwingbottom.render(f5);
        backrightwingbottom.render(f5);
        WingEndleftbackbottom.render(f5);
        WingEndrightbackbottom.render(f5);
    }
    if(jet.quadGunUpgrade)
    {
        LeftWingGun.render(f5);
        RightWingGun.render(f5);
    }

  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
