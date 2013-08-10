package Iamshortman.GridMod.Client.RenderAPI;

import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;

public class GridCraftRenderPlayer extends RenderPlayerBase
{
	public GridCraftRenderPlayer(RenderPlayerAPI renderPlayerAPI)
	{
		super(renderPlayerAPI);
	}

	@Override
	public void renderPlayer(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f2)
	{
    	if(entityplayer.ridingEntity != null && isRidingVechicle(entityplayer))
    	{
    		return;
    	}
		super.renderPlayer(entityplayer, d, d1, d2, f, f2);
	}
	
	public void drawFirstPersonHand(EntityPlayer player)
	{
    	if(player.ridingEntity != null && isRidingVechicle(player))
    	{
    		return;
    	}
		super.drawFirstPersonHand(player);
	}
	
    public float getDeathMaxRotation(EntityLiving var1)
    {
    	if(var1.ridingEntity != null && isRidingVechicle(var1))
    	{
    		return 0;
    	}
    	else
    	{
    		return super.getDeathMaxRotation(var1);
    	}  	
    }
    public void renderSpecialItemInHand(EntityPlayer var1, float var2)
    {
    	
    	if(var1.ridingEntity != null && isRidingVechicle(var1))
    	{
    		
    	}
    	else
    	{
    		super.renderSpecialItemInHand(var1, var2);
    	}
    }
    
	public void positionSpecialItemInHand(EntityPlayer var1, float var2, EnumAction var3, ItemStack var4)
	{
    	if(var1.ridingEntity != null && isRidingVechicle(var1))
    	{
   
    	}
    	else
    	{
    		super.positionSpecialItemInHand(var1, var2, var3, var4);
    	}
	}

	public boolean isRidingVechicle(EntityLiving var1)
	{
		if(var1.ridingEntity.getClass().isAssignableFrom(EntityLightJet.class))
		{
			return true;
		}
		else if(var1.ridingEntity.getClass().isAssignableFrom(EntityLightCycle.class))
		{
			return true;
		}
		return false;
	}
	
}
