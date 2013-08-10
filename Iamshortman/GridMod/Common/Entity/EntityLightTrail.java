package Iamshortman.GridMod.Common.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLightTrail extends TronEntity
{
	public int Time = -1;
	
	
	//TODO Create Real light trail
	public EntityLightTrail(World par1World)
	{
		super(par1World);

		this.setSize(1 , 1);
		Time = 1000;
	}

	public EntityLightTrail(World par1World, int time)
	{
		super(par1World);

		Time = time;
	}

	public void onUpdate()
	{
		setDead();
	}

	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound var1)
	{
		super.readEntityFromNBT(var1);
		Time = var1.getInteger("Time");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound var1)
	{
		super.writeEntityToNBT(var1);
		var1.setInteger("Time", Time);
	}
	@Override
	public String getTextureFromColor()
	{
		return "/Tron/null.png";
	}

}
