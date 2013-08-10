package Iamshortman.GridMod.Common.Entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.Forge.ClientProxy;
import Iamshortman.GridMod.Common.util.Vector3;

public class EntityLightJet extends TronEntity
{

	private int rocketCount, rocketCount2;
	private boolean rocketswitch = false;
	private boolean shouldTrail = false;

	// all vars that can be changed by command in-game
	public static int MAXSPEED = 60;
	public static boolean canTrail = true;
	public static int TrailTime = 500;

	// vehicle upgrades
	public boolean quadGunUpgrade = false;
	public boolean quadWingUpgrade = false;
	
	
	// amount of vehicle motion to transfer upon projectile launch
	float MOMENTUM = 0.6f;

	float pilotExitDelay;

	Vector3 TrailPoint = new Vector3();
	Vector3 thrust = new Vector3();
	Vector3 velocity = new Vector3();

	private EntityLightTrail preSpawnTrail;

	private float driveSpeed = 0;
	private int trailWait = 0;
	private int startUpTime = 35;

	public EntityLightJet(World world)
	{
		super(world);
		setSize(1.8f, 2f);
		yOffset = .8f;
	}

	@Override
	public void fall(float par1)
	{
		if (this.ticksExisted < startUpTime)
		{
			super.fall(par1);
			if (par1 < 6)
			{
				this.setDead();
			}
			return;
		}
	}

	public EntityLightJet(World world,ItemStack item , double x, double y, double z, float yaw)
	{
		this(world);
		
		setPositionAndRotation(x, y + yOffset, z, yaw, 0f);
	}

	public Entity getPilot()
	{
		return riddenByEntity;
	}

	@Override
	public void onUpdate() // entrypoint; called by minecraft each tick
	{
		super.onUpdate();
		if (this.onGround && !this.worldObj.isRemote && this.ticksExisted < startUpTime)
		{
			this.setDead();
		}

		if (riddenByEntity != null)
		{
			onUpdateWithPilot();
			updateTrail();
		} else
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
    public void readDataWatcher()
    {
    	super.readDataWatcher();
    	byte temp = this.dataWatcher.getWatchableObjectByte(21);
    	if(temp == 3)
    	{
    		this.quadWingUpgrade = true;
    		this.quadGunUpgrade = true;
    	}
    	else if(temp == 2)
    	{
    		this.quadWingUpgrade = true;
    		this.quadGunUpgrade = false;
    	}
    	else if(temp == 1)
    	{
    		this.quadGunUpgrade = true;
    		this.quadWingUpgrade = false;
    	}
    	else
    	{
    		this.quadGunUpgrade = false;
    		this.quadWingUpgrade = false;
    	}
    	
    }
	
    @Override
    public void updateDataWatcher()
    {
    	super.updateDataWatcher();
    	if(this.quadWingUpgrade && this.quadGunUpgrade)
    	{
       		this.dataWatcher.updateObject(21, (byte) 3);
    	}
    	else if(this.quadWingUpgrade)
    	{
    		this.dataWatcher.updateObject(21, (byte) 2);
    	}
    	else if(this.quadGunUpgrade)
    	{
    		this.dataWatcher.updateObject(21, (byte) 1);
    	}
    	else
    	{
    		this.dataWatcher.updateObject(21, (byte) 0);
    	}
    	
    }
	
    @Override
    public void entityInit()
    {
    	super.entityInit();
    	this.dataWatcher.addObject(21, new Byte((byte)0));
    }
    
	private void updateTrail()
	{
		float R = 2F;
		float Rotation = -Math.abs(rotationPitch);
		float DymaicRadius = R + (float) (Math.sin((Rotation / 360) * Math.PI) * R);

		if (rotationPitch > 0)
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
		} else
		{
			this.updateMotion();

			if (this.trailWait > 0)
			{
				this.trailWait--;
			}
			if (this.riddenByEntity.isSneaking() && this.trailWait == 0)
			{
				this.shouldTrail = !this.shouldTrail;
				this.trailWait = 15;
			}
			if (this.shouldTrail)
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
		if (player != null)
		{
			if (riddenByEntity != null)
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
					
					if(this.quadWingUpgrade)
					{
						this.rotationYawSpeed *= 1.4;	
					}
					rotationYaw += (rotationYawSpeed * deltaTime);

					float deltaPitchDeg = player.rotationPitch - rotationPitch;
					while (deltaPitchDeg > 180f)
						deltaPitchDeg -= 360f;
					while (deltaPitchDeg < -180f)
						deltaPitchDeg += 360f;
					rotationYawSpeed = deltaPitchDeg * 3f;
					if (rotationYawSpeed > 90f)
						rotationPitchSpeed = 90f;
					if (rotationYawSpeed < -90f)
						rotationPitchSpeed = -90f;
					
					if(this.quadWingUpgrade)
					{
						this.rotationYawSpeed *= 1.4;	
					}
					rotationPitch += (rotationYawSpeed * deltaTime);
				}
			}

			// PILOT EXIT
			pilotExitDelay -= deltaTime;
			if (ClientProxy.Exitkey.pressed && this.riddenByEntity != null)
			{
				pilotExitDelay = .5f;
				pilotExit();
			}

			if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed)
			{
				this.driveSpeed = 0.9F;
			} else if (Minecraft.getMinecraft().gameSettings.keyBindBack.pressed)
			{
				this.driveSpeed = 0.3F;
			} else
			{
				this.driveSpeed = 0.5F;
			}

		}

	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, int damageAmount)
	{
		if (this.riddenByEntity == null)
		{
			this.setDead();
			return false;
		}

		if (damageSource.getEntity() != null && damageSource.getEntity().equals(this.riddenByEntity))
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
			((EntityPlayerMP) pilot).playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.setDead();
		}

	}

	@Override
	public double getMountedYOffset()
	{
		return -1.0;
	}

	void fireLaser()
	{
		if (worldObj.isRemote)
			return;

		rocketCount++;

		float leftRightAmount = .75f;
		float leftRight = (rocketCount % 2 == 0) ? leftRightAmount : -leftRightAmount;

		// starting position of rocket relative to helicopter, out in front a
		// bit to avoid collision
		float offsetX = (side.x * leftRight) + (fwd.x * 2.5f) + (up.x * -.5f);
		float offsetY = (side.y * leftRight) + (fwd.y * 2.5f) + (up.y * -.5f);
		float offsetZ = (side.z * leftRight) + (fwd.z * 2.5f) + (up.z * -.5f);

		float yaw = rotationYaw;
		float pitch = rotationPitch;

		Entity newOwner = this;
		EntityLaser newRocket = new EntityLaser(newOwner, posX + offsetX, posY + offsetY, posZ + offsetZ, motionX * MOMENTUM, motionY * MOMENTUM, motionZ * MOMENTUM, yaw, pitch);
		worldObj.spawnEntityInWorld(newRocket);
	}

	void fireLaser2()
	{
		if (worldObj.isRemote)
			return;

		rocketCount2++;

		float leftRightAmount = 2f;
		float leftRight = (rocketCount2 % 2 == 0) ? -leftRightAmount : leftRightAmount;

		// starting position of rocket relative to helicopter, out in front a
		// bit to avoid collision
		float offsetX = (side.x * leftRight) + (fwd.x * 2.5f) + (up.x * -.5f);
		float offsetY = (side.y * leftRight) + (fwd.y * 2.5f) + (up.y * -.5f);
		float offsetZ = (side.z * leftRight) + (fwd.z * 2.5f) + (up.z * -.5f);

		float yaw = rotationYaw;
		yaw += -(leftRight * 1.0F);

		float pitch = rotationPitch;

		Entity newOwner = this;
		EntityLaser newRocket = new EntityLaser(newOwner, posX + offsetX, posY + offsetY, posZ + offsetZ, motionX * MOMENTUM, motionY * MOMENTUM, motionZ * MOMENTUM, yaw, pitch);
		worldObj.spawnEntityInWorld(newRocket);
	}

	void fireMissile()
	{
		return;

		/* 
		 float offX = (fwd.x * 2.5f) + (up.x * -.5f); float offY = (fwd.y *
		 2.5f) + (up.y * -.5f); float offZ = (fwd.z * 2.5f) + (up.z * -.5f);
		  
		 // aim with cursor if pilot //float yaw = riddenByEntity != null ?
		 riddenByEntity.rotationYaw : rotationYaw; //float pitch =
		 riddenByEntity != null ? riddenByEntity.rotationPitch :
		 rotationPitch; float yaw = rotationYaw; float pitch = rotationPitch +
		 5f;
		  
		 // pilot is owner to get xp, if no pilot (ai) then helicopter is
		 owner Entity newOwner = riddenByEntity != null ? riddenByEntity :
		 this; ThxEntityMissile newMissile = new ThxEntityMissile(newOwner,
		 posX + offX, posY + offY, posZ + offZ, motionX * MOMENTUM, motionY *
		  MOMENTUM, motionZ * MOMENTUM, yaw, pitch);
		  worldObj.spawnEntityInWorld(newMissile);
		 */
	}

	public void updateMotion()
	{
		if (this.ticksExisted < startUpTime)
		{
			this.motionY -= 0.05D;
			super.moveEntity(motionX, motionY, motionZ);
			return;
		}
		if (riddenByEntity != null)
		{
			float Rotation = -Math.abs(rotationPitch);
			float DymaicRadius = (driveSpeed * (MAXSPEED / 100F) + (float) (Math.sin((Rotation / 360) * Math.PI) * (driveSpeed * (MAXSPEED / 100F))));
			motionZ = Math.cos(rotationYaw * 0.0174532925) * DymaicRadius;
			motionX = Math.sin(rotationYaw * 0.0174532925) * -DymaicRadius;
			if (Rotation < -86)
			{
				motionX = 0;
				motionZ = 0;
			}
			motionY = Math.sin(rotationPitch * 0.0174532925) * -(driveSpeed * (MAXSPEED / 100F));
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
		fireLaser();
		fireLaser();
		if(this.quadGunUpgrade)
		{
			this.fireLaser2();
			this.fireLaser2();
		}
	}


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
		if (riddenByEntity != null && riddenByEntity.entityId == minecraft.thePlayer.entityId)
		{
			// already sending position to server, so ignore server updates
			return;
		}
		super.setPositionAndRotation2(posX, posY, posZ, yaw, pitch, unused);
	}

	/**
	 * the massively complicated part of code that really doesn't work the
	 * greatest, that will spawn light trails
	 */
	private void LightTrail()
	{
		double xd = 0;
		double yd = 0;
		double zd = 0;
		double x = 0, z = 0;
		double trailfrontX = 0;
		double trailfrontZ = 0;
		if (preSpawnTrail == null)// sets points if trail has not spawned
		{
			float R = 5F;
			x = posX + (Math.sin((this.rotationYaw / 180) * Math.PI) * R);
			xd = x - TrailPoint.x;
			z = posZ + (Math.cos((this.rotationYaw / 180) * Math.PI) * -R);
			zd = z - TrailPoint.z;
			yd = 0;
		} else
		{
			// getting front point of last trail
			float R = .5F;
			trailfrontX = this.preSpawnTrail.posX + (Math.sin((this.preSpawnTrail.rotationYaw) * Math.PI) * R);
			trailfrontZ = this.preSpawnTrail.posZ + (Math.cos((this.preSpawnTrail.rotationYaw) * Math.PI) * -R);

			// getting distance between two points
			xd = trailfrontX - TrailPoint.x;
			yd = preSpawnTrail.posY - TrailPoint.y;
			zd = trailfrontZ - TrailPoint.z;
		}
		double D = Math.sqrt(xd * xd + yd * yd + zd * zd);
		if (0.5D <= D && !this.worldObj.isRemote)
		{
			// SpawnLightTrail(this.rotationYaw,this.rotationPitch);
		}
	}

	/**
	 * spawns the light trail
	 */
	private void SpawnLightTrail(float yaw, float pitch)
	{
		EntityLightTrail trail = new EntityLightTrail(worldObj);
		trail.setLocationAndAngles(TrailPoint.x, TrailPoint.y, TrailPoint.z, yaw, pitch);
		worldObj.spawnEntityInWorld(trail);
		preSpawnTrail = trail;
	}

	@Override
	public String getTextureFromColor()
	{
		switch (this.Color)
		{
			case 0:
				return "/Tron/lightJet/LightJetBlue.png";
			case 1:
				return "/Tron/lightJet/LightJetGreen.png";
			case 2:
				return "/Tron/lightJet/LightJetOrange.png";
			case 3:
				return "/Tron/lightJet/LightJetPurple.png";
			case 4:
				return "/Tron/lightJet/LightJetRed.png";
			case 5:
				return "/Tron/lightJet/LightJetWhite.png";
			case 6:
				return "/Tron/lightJet/LightJetYellow.png";
		}
		return "!";
	}

}
