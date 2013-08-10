package Iamshortman.GridMod.Common.Entity;

import Iamshortman.GridMod.Client.Model.ModelLaser;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;


public class EntityLaser extends EntityProjectile
{
	public EntityLaser(World world)
    {
        super(world);
        this.lifeSpan = 100;
    }

    public EntityLaser(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }
    
    public EntityLaser(Entity owner, double x, double y, double z, double dx, double dy, double dz, float yaw, float pitch)
    {
        super(owner, x, y, z, dx, dy, dz, yaw, pitch);
    }
    
    
    @Override
    void strikeEntity(Entity entity)
    {
        if (worldObj.isRemote) return; // only applies on server
		if(entity.getClass().isAssignableFrom(EntityLightTrail.class))
		{
			this.setDead();
			return;
		}
        
        
        if(this.owner == null || this.owner.riddenByEntity == null)
        {
        	return;
        }
        
        if(entity.equals(this.owner) || entity.equals(this.owner.riddenByEntity))
        {
        	System.out.println("Hit Owner");
        	return;
        }
        
        int attackStrength = 6;
        entity.attackEntityFrom(new EntityDamageSource("player", owner.riddenByEntity), attackStrength);
    }
    
    @Override
    void createParticles()
    {
    	
    }
    
    @Override
    void onLaunch()
    {   
        super.onLaunch();
    }
    
    @Override
    float getAcceleration()
    {
        return 1.4f;
    }
    
    @Override
    void detonate()
    {   
        this.setDead();
    }

	@Override
	public String getTextureFromColor()
	{
		return "/Tron/laser/Laser.png";
	}
    
}

