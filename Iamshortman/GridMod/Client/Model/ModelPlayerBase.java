package Iamshortman.GridMod.Client.Model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelPlayerBase extends ModelBase
{
	public ModelRenderer Head;
	public ModelRenderer LegRight;
	public ModelRenderer Body;
	public ModelRenderer LegLeft;
	public ModelRenderer ArmLeft;
	public ModelRenderer ArmRight;
	public ModelRenderer HeadGear;
	
	public void render(Entity entity)
	{
		float f5 = 0.0625F;
		EntityPlayer player = (EntityPlayer) entity;
		
		if(player.username.equals(Minecraft.getMinecraft().thePlayer.username) && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
		{

		}
		else
		{
			Head.render(f5);
			HeadGear.render(f5);
		}
		LegRight.render(f5);
		Body.render(f5);
		LegLeft.render(f5);
		ArmLeft.render(f5);
		ArmRight.render(f5);
	}
	
	public void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
