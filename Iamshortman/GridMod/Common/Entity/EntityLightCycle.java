package Iamshortman.GridMod.Common.Entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.Forge.ClientProxy;
import Iamshortman.GridMod.Common.util.Vector3;

public class EntityLightCycle extends TronEntity
{

	private boolean shouldTrail = false;

	//all vars that can be changed by command in-game
	public static int MAXSPEED = 60;
	public static boolean canTrail = true;
	public static int TrailTime = 500;
	
	
	// amount of vehicle motion to transfer upon projectile launch
	float MOMENTUM = 0.6f;

	float pilotExitDelay;

	Vector3 TrailPoint = new Vector3();
	Vector3 thrust = new Vector3();
	Vector3 velocity = new Vector3();

	
    private EntityLightTrail preSpawnTrail;
	
	private float driveSpeed = 0;
	private int trailWait = 0;

	public EntityLightCycle(World world)
	{
		super(world);
		setSize(2F, 1F);
		yOffset = 1.5f; 
		this.stepHeight = 1.0F;
	}

	public EntityLightCycle(World world, double x, double y, double z, float yaw)
	{
		this(world);
		setPositionAndRotation(x, y, z, yaw, 0f);
	}

	public Entity getPilot()
	{
		return riddenByEntity;
	}

	@Override
	public String getTextureFromColor()
	{
		switch (this.Color)
		{
			case 0:
				return "/Tron/lightCycle/LightCycleBlue.png";
			case 1:
				return "/Tron/lightCycle/LightCycleGreen.png";
			case 2:
				return "/Tron/lightCycle/LightCycleOrange.png";
			case 3:
				return "/Tron/lightCycle/LightCyclePurple.png";
			case 4:
				return "/Tron/lightCycle/LightCycleRed.png";
			case 5:
				return "/Tron/lightCycle/LightCycleWhite.png";
			case 6:
				return "/Tron/lightCycle/LightCycleYellow.png";
		}
		return "!";
	}
	
	@Override
	public void onUpdate() // entrypoint; called by minecraft each tick
	{
		super.onUpdate();
	
        if (this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded))
        {
            if (this.posY > 0.0D)
            {
                this.motionY = -0.1D;
            }
            else
            {
                this.motionY = 0.0D;
            }
        }
        else
        {
            this.motionY -= 0.08D;
        }
        
		if (riddenByEntity != null)
		{
			onUpdateWithPilot();
			updateTrail();
		} 
		else
		{
			if (!worldObj.isRemote)
			{
				this.setDead();
			}
			moveEntity(motionX, motionY, motionZ);
		}

		// on server, update watched values for all cases
		if (!worldObj.isRemote)
			updateDataWatcher();

	}

	@Override
	public void fall(float fall)
	{
		super.fall(fall / 2);
	}
	
	private void updateTrail()
	{
        float R = 2F; 
        float Rotation = -Math.abs(rotationPitch);
        float DymaicRadius =  R + (float)(Math.sin((Rotation / 360) * Math.PI) * R);

        if(rotationPitch > 0)
        {
        	DymaicRadius *= 0.6;
        }
         TrailPoint.x = (float) (posX + (Math.sin((this.rotationYaw / 180) * Math.PI) * DymaicRadius));
         TrailPoint.z = (float) (posZ + (Math.cos((this.rotationYaw / 180) * Math.PI) * -DymaicRadius));
         TrailPoint.y = (float) (posY + (Math.sin((this.rotationPitch / 360) * Math.PI) * 2F));
		
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}
	
	private void onUpdateWithPilot()
	{
		if (worldObj.isRemote)
		{
			if (this.riddenByEntity.entityId == this.minecraft.thePlayer.entityId) 
			{
				
				this.onUpdateWithPilotPlayerInput();
				this.updateMotion();
				this.sendUpdatePacketFromClient();
			}
		} 
		else
		{
			this.updateMotion();
			
			if(this.trailWait > 0)
			{
				this.trailWait--;
			}
			if(this.riddenByEntity.isSneaking() && this.trailWait == 0)
			{
				this.shouldTrail = !this.shouldTrail;
				this.trailWait = 15;
			}
			if(this.shouldTrail)
			{
				this.LightTrail();
			}
			
			if (this.riddenByEntity.isDead)
			{
				this.pilotExit();
			}
		}
	}

	public void onUpdateWithPilotPlayerInput()
	{
		if (this.riddenByEntity.entityId != minecraft.thePlayer.entityId)
		{
			return;
		}
		
		EntityPlayer player = (EntityPlayer) this.riddenByEntity;
		if(player != null)
		{
	        if (riddenByEntity != null)
	        {
				if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed || Minecraft.getMinecraft().gameSettings.keyBindBack.pressed)
				{
					if (this.minecraft.currentScreen == null && minecraft.gameSettings.thirdPersonView != 2)
					{
						float deltaYawDeg = player.rotationYaw - rotationYaw;
						while (deltaYawDeg > 180f)
							deltaYawDeg -= 360f;
						while (deltaYawDeg < -180f)
							deltaYawDeg += 360f;
						rotationYawSpeed = deltaYawDeg * 3f;
						if (rotationYawSpeed > 90f)
							rotationYawSpeed = 90f;
						if (rotationYawSpeed < -90f)
							rotationYawSpeed = -90f;
						rotationYaw += rotationYawSpeed * deltaTime;

						rotationPitch = 0;
					}
				}

				if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed)
				{
					this.driveSpeed = 0.6F;
				} else if (Minecraft.getMinecraft().gameSettings.keyBindBack.pressed)
				{
					this.driveSpeed = -0.2F;
				} else
				{
					this.driveSpeed = 0.0F;
				}
			}
		
		
		// PILOT EXIT
		pilotExitDelay -= deltaTime;
		if (ClientProxy.Exitkey.pressed && this.riddenByEntity != null)
		{
			pilotExitDelay = .5f;
			pilotExit();
		}
        if (Minecraft.getMinecraft().gameSettings.keyBindJump.pressed && this.onGround)
        {
            this.jump();
        }
		}
		
	}

	private void jump()
	{
		this.motionY =+ 0.21999998688697815D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, int damageAmount)
	{
		if(this.riddenByEntity == null)
		{
			this.setDead();
			return false;
		}
		
		if(damageSource.getEntity() != null && damageSource.getEntity().equals(this.riddenByEntity))
		{
			return false;
		}
		return this.riddenByEntity.attackEntityFrom(damageSource, damageAmount);
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		// super.interact returns true if player boards and becomes new pilot
		if (!super.interact(player))
			return true; 

		return true;
	}


	@Override
	void pilotExit()
	{

		if (riddenByEntity == null)
		{
			return;
		}

		Entity pilot = getPilot();
		riddenByEntity.mountEntity(this); // riddenByEntity is now null // no
											// more pilot

		// these should already be null from call to mountEntity above
		riddenByEntity = null;
		if (pilot != null)
		{
			if (pilot.ridingEntity != null)
				pilot.ridingEntity.riddenByEntity = null;
			;
			pilot.ridingEntity = null;
		}

		if (worldObj.isRemote)
		{
			// clear rotation speed to prevent judder
			this.rotationYawSpeed = 0f;
			this.rotationPitchSpeed = 0f;
			this.rotationRollSpeed = 0f;
		} else
		{
			double exitDist = 1.9;
			((EntityPlayerMP) pilot).playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ , this.rotationYaw,this.rotationPitch);
			this.setDead();
		}

	}
	
	@Override
	public double getMountedYOffset()
	{
		return -1.5;
	}
	@Override
	public double getYOffset()
	{
		return 1;
	}


	public void updateMotion()
	{
		if (riddenByEntity != null)
		{
			motionZ = Math.cos(rotationYaw * 0.0174532925) * (driveSpeed * (MAXSPEED / 100F));
			motionX = Math.sin(rotationYaw * 0.0174532925) * -(driveSpeed * (MAXSPEED / 100F));

			super.moveEntity(motionX, motionY, motionZ);
		}
	}

	@Override
	public void updateRiderPosition()
	{
		if (riddenByEntity == null)
			return;
		
		riddenByEntity.setPosition(posX, posY + riddenByEntity.getYOffset() + getMountedYOffset(), posZ);
	}


	@Override
	void interactByPilot()
	{

	}

	/*
	 * @Override public AxisAlignedBB getBoundingBox() { return null; }
	 */

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return this.boundingBox;
	}

	@Override
	public void setVelocity(double dx, double dy, double dz)
	{
		assertClientSideOnly();
		if (riddenByEntity != null && riddenByEntity.entityId == minecraft.thePlayer.entityId)
		{
			// already sending velocity to server, so ignore server updates
			return;
		}
		super.setVelocity(dx, dy, dz);
	}

	@Override
	public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int unused)
	{
		assertClientSideOnly();
		if (riddenByEntity != null 
				&& riddenByEntity.entityId == minecraft.thePlayer.entityId)
		{
			// already sending position to server, so ignore server updates
			return;
		}
		super.setPositionAndRotation2(posX, posY, posZ, yaw, pitch, unused);
	}
	  /**
     * the massively complicated part of code that really doesn't work the greatest, that will spawn light trails
     */
    private void LightTrail() 
    {
    	double xd = 0;
    	double yd = 0;
    	double zd = 0;
    	double x = 0,z = 0;
    	double trailfrontX = 0;
        double trailfrontZ = 0;
    	if(preSpawnTrail == null)//sets points if trail has not spawned
    	{
            float R = 5F; 
    		x = posX + (Math.sin((this.rotationYaw / 180) * Math.PI) * R);
    		xd = x - TrailPoint.x;
            z = posZ + (Math.cos((this.rotationYaw / 180) * Math.PI) * -R);
        	zd = z - TrailPoint.z;
        	yd = 0;
    	}
    	else
    	{
    		//getting front point of last trail
            float R = .5F;
           trailfrontX = this.preSpawnTrail.posX + (Math.sin((this.preSpawnTrail.rotationYaw) * Math.PI) * R);
           trailfrontZ = this.preSpawnTrail.posZ + (Math.cos((this.preSpawnTrail.rotationYaw) * Math.PI) * -R);
	
           //getting distance between two points    		
    	xd = trailfrontX - TrailPoint.x;
    	yd = preSpawnTrail.posY - TrailPoint.y;
    	zd = trailfrontZ - TrailPoint.z;
    	}
    	double D = Math.sqrt(xd*xd + yd*yd + zd*zd);
    if( 0.5D <= D && !this.worldObj.isRemote)
    {
        //SpawnLightTrail(this.rotationYaw,this.rotationPitch);
    }
    }
 
    
   /**
    * spawns the light trail
    */
    private void SpawnLightTrail(float yaw,float pitch) 
    {
        EntityLightTrail trail = new EntityLightTrail(worldObj);
        trail.setLocationAndAngles(TrailPoint.x, TrailPoint.y, TrailPoint.z, yaw, pitch);
        worldObj.spawnEntityInWorld(trail);
		preSpawnTrail = trail;
	}
	
    
}

