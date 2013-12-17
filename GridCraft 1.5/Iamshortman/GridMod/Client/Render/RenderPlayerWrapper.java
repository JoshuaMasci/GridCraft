package Iamshortman.GridMod.Client.Render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class RenderPlayerWrapper extends RenderPlayer
{
	RenderPlayer wrappedClass;
	public RenderPlayerWrapper(RenderPlayer render)
	{
		this.wrappedClass = render;
	}
	
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
		if(entity.ridingEntity == null)
		{
			this.wrappedClass.doRender(entity, d0, d1, d2, f, f1);
		}
	}
	
	@Override
    public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
		if(par1Entity.ridingEntity == null)
		{
			this.wrappedClass.doRenderShadowAndFire(par1Entity, par2, par4, par6, par8, par9);
		}
    }

	
	@Override
    public void renderFirstPersonArm(EntityPlayer par1EntityPlayer)
    {
		if(par1EntityPlayer.ridingEntity == null)
		{
			this.wrappedClass.renderFirstPersonArm(par1EntityPlayer);
		}
    }
}
