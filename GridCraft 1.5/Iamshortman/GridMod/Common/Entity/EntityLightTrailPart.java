package Iamshortman.GridMod.Common.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLightTrailPart extends Entity
{
	public EntityLightTrail parent;
	public double offsetX;
	public double offsetY;
	public double offsetZ;
	public int Id;
	
	public EntityLightTrailPart(World par1World, EntityLightTrail trail,int id)
	{
		super(par1World);
		this.setSize(1.0F, 1.0F);
		this.parent = trail;
		this.Id = id;
	}

	@Override
	public void setPosition(double PosX, double PosY, double PosZ)
	{
		super.setPosition(PosX, PosY, PosZ);
		this.offsetX = PosX - this.parent.posX;
		this.offsetY = PosY - this.parent.posY;
		this.offsetZ = PosZ - this.parent.posZ;
	}
	
	@Override
	protected void entityInit()
	{
		
	}
	
    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{

	}

}
