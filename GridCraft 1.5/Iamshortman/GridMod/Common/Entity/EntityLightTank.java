package Iamshortman.GridMod.Common.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import Iamshortman.GridMod.Client.Forge.GridClientProxy;
import Iamshortman.GridMod.Client.Model.ModelLightTank;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLightTank extends TronEntity
{

	public float pilotExitDelay;
	private float driveSpeed = 0;

	public float rotationYawTurret;
	public float rotationPitchTurret;
	
	public float preRotationYawTurret;
	public float preRotationPitchTurret;
	
	public float rotationYawTurretSpeed;
	public float rotationPitchTurretSpeed;
	
	public EntityLightTank(World world)
	{
		super(world);
		setSize(7.5F, 2F);
		this.stepHeight = 1.0F;
	}

	@Override
	public String getGlowTexture()
	{
		return "/mods/GridCraft/textures/lightTank/LightTankGlow.png";
	}

	@Override
	public String getBaseTexture()
	{
		return "/mods/GridCraft/textures/lightTank/LightTankBase.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void createModel()
	{
		this.Model = new ModelLightTank();
	}

	@Override
	public void onUpdate() // entrypoint; called by minecraft each tick
	{
		super.onUpdate();

		if (this.worldObj.isRemote && (!this.worldObj.blockExists((int) this.posX, 0, (int) this.posZ) || !this.worldObj.getChunkFromBlockCoords((int) this.posX, (int) this.posZ).isChunkLoaded))
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
		}
		else
		{
			onUpdateVacant();
			moveEntity(motionX, motionY, motionZ);
		}

		// on server, update watched values for all cases
		if (!worldObj.isRemote)
			updateDataWatcher();

	}

	private void onUpdateVacant()
	{
		// clear rotation speed to prevent judder
		this.rotationYawSpeed = 0f;
		this.rotationPitchSpeed = 0f;
		this.rotationRollSpeed = 0f;
		this.motionX *= 0.2F;
		this.motionY *= 0.2F;
		this.motionZ *= 0.2F;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return null;
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
			super.moveEntity(motionX, motionY, motionZ);
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

			if (Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed && !Minecraft.getMinecraft().gameSettings.keyBindRight.pressed)
			{
				rotationYawSpeed -= 4f;
			}
			else if (Minecraft.getMinecraft().gameSettings.keyBindRight.pressed && !Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed)
			{
				rotationYawSpeed += 4f;
			}
			else
			{
				rotationYawSpeed *= .75f;
			}

			if (rotationYawSpeed > 40f)
				rotationYawSpeed = 40f;
			if (rotationYawSpeed < -40f)
				rotationYawSpeed = -40f;
			rotationYaw += rotationYawSpeed * deltaTime;

			if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed)
			{
				this.driveSpeed = 1.0F;
			}
			else if (Minecraft.getMinecraft().gameSettings.keyBindBack.pressed)
			{
				this.driveSpeed = -1.0F;
			}
			else
			{
				this.driveSpeed = 0.0F;
			}

			//Turret Rotation
			float deltaYawDeg = player.rotationYaw - rotationYawTurret;
			while (deltaYawDeg > 180f)
				deltaYawDeg -= 360f;
			while (deltaYawDeg < -180f)
				deltaYawDeg += 360f;
			rotationYawTurretSpeed = deltaYawDeg * 10f;
			if (rotationYawTurretSpeed > 90f)
				rotationYawTurretSpeed = 90f;
			if (rotationYawTurretSpeed < -90f)
				rotationYawTurretSpeed = -90f;
			rotationYawTurret += rotationYawTurretSpeed * deltaTime;
			
			float deltaPitchDeg = player.rotationPitch - rotationPitchTurret;
			while (deltaPitchDeg > 180f)
				deltaPitchDeg -= 360f;
			while (deltaPitchDeg < -180f)
				deltaPitchDeg += 360f;
			rotationPitchTurretSpeed = deltaPitchDeg * 10f;
			if (rotationPitchTurretSpeed > 90f)
				rotationPitchTurretSpeed = 90f;
			if (rotationPitchTurretSpeed < -90f)
				rotationPitchTurretSpeed = -90f;
			rotationPitchTurret += rotationPitchTurretSpeed * deltaTime;

			if(rotationPitchTurret < -40)
			{
				this.rotationPitchTurret = -40;
				this.rotationPitchTurretSpeed = 0;
			}
			else if(rotationPitchTurret > 4)
			{
				this.rotationPitchTurret = 4;
				this.rotationPitchTurretSpeed = 0;
			}
			
			
			// PILOT EXIT
			pilotExitDelay -= deltaTime;
			if (GridClientProxy.Exitkey.pressed && this.riddenByEntity != null)
			{
				pilotExitDelay = .5f;
				pilotExit();
			}
		}
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

	@Override
	public void pilotExit()
	{

		if (riddenByEntity == null)
		{
			return;
		}

		Entity pilot = riddenByEntity;
		riddenByEntity.mountEntity(this); // riddenByEntity is now null // no
		// more pilot

		// these should already be null from call to mountEntity above
		riddenByEntity = null;
		if (pilot != null)
		{
			if (pilot.ridingEntity != null)
				pilot.ridingEntity.riddenByEntity = null;

			pilot.ridingEntity = null;
		}

		if (worldObj.isRemote)
		{
			// clear rotation speed to prevent judder
			this.rotationYawSpeed = 0f;
			this.rotationPitchSpeed = 0f;
			this.rotationRollSpeed = 0f;
		}
		else
		{
			double exitDist = 1.9;
			((EntityPlayerMP) pilot).playerNetServerHandler.setPlayerLocation(this.posX, this.posY + 2.0F, this.posZ, pilot.rotationYaw, pilot.rotationPitch);
		}

	}

	@Override
	public void updateRiderPosition()
	{
		if (riddenByEntity == null)
			return;

		prevRotationYaw = rotationYaw;
		prevRotationPitch = rotationPitch;
		float back = 1.6F;
		double backZ = Math.cos(rotationYaw * 0.0174532925) * (back);
		double backX = Math.sin(rotationYaw * 0.0174532925) * -(back);
		
		float right = 1.425F;
		
		riddenByEntity.setPosition(((side.x * right) + posX) - backX, posY + riddenByEntity.getYOffset() + getMountedYOffset(), ((side.z * right) + posZ) - backZ);
	}

	public void updateMotion()
	{
		if (riddenByEntity != null)
		{
			motionZ = Math.cos(rotationYaw * 0.0174532925) * (driveSpeed * 0.4F);
			motionX = Math.sin(rotationYaw * 0.0174532925) * -(driveSpeed * 0.4F);
			super.moveEntity(motionX, motionY, motionZ);
		}
	}
}
