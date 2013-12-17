package Iamshortman.GridMod.Common.Entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import Iamshortman.GridMod.Client.Model.ModelLightTrail;
import Iamshortman.GridMod.Common.Network.PacketLightTrailUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityLightTrail extends Entity
{
	public EntityLightTrailPart Trails[] = new EntityLightTrailPart[1000];
	int index = 0;
	
	public EntityLightTrail(World world)
	{
		super(world);	
	}
	
	public void addTrail(double posX, double posY, double posZ, float RotationYaw, float RotationPitch)
	{
		if(this.worldObj.isRemote)
		{
			return;
		}
		EntityLightTrailPart trail = new EntityLightTrailPart(this.worldObj, this, index);
		trail.setPosition(posX, posY, posZ);
		trail.setAngles(RotationYaw, RotationPitch);
		Trails[index] = trail;
		PacketDispatcher.sendPacketToAllInDimension(PacketLightTrailUpdate.createPacket(trail), this.worldObj.provider.dimensionId);
		index++;
	}
	
    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }

	public void setColor(int i)
	{
		this.dataWatcher.updateObject(20, i);
	}

	public int GetColor()
	{
		return this.dataWatcher.getWatchableObjectInt(20);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		this.setColor(nbttagcompound.getInteger("Color"));
		for(int i = 0; i < Trails.length; i++)
		{
			NBTTagCompound nbt = nbttagcompound.getCompoundTag("Trail" + i);
			if(nbt != null)
			{
				Trails[i] = new EntityLightTrailPart(this.worldObj, this, i);
				Trails[i].readFromNBT(nbt);
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setInteger("Color", this.GetColor());
		for(int i = 0; i < Trails.length; i++)
		{
			if(Trails[i] != null)
			{
				Trails[i].writeToNBT(nbttagcompound.getCompoundTag("Trail" + i));
			}
		}
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(20, new Integer(0));	
	}


}
