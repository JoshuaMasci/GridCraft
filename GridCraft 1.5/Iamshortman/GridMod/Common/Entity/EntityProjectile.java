package Iamshortman.GridMod.Common.Entity;

import java.util.List;


import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityProjectile extends TronEntity
{
	boolean enteredWater;
	boolean launched;
	public int lifeSpan = 300;

	public EntityProjectile(World world)
	{
		super(world);

		// setSize(0.25F, 0.25F);
		setSize(.5f, .5f);
	}

	public EntityProjectile(World world, double x, double y, double z)
	{
		this(world);
		setPositionAndRotation(x, y, z, 0f, 0f);
	}

	public EntityProjectile(Entity owner, double x, double y, double z, double dx, double dy, double dz, float yaw, float pitch)
	{
		this(owner.worldObj);

		this.owner = owner;

		setPositionAndRotation(x, y, z, yaw, pitch);

		float acceleration = getAcceleration();

		updateRotation();
		updateVectors();

		motionX = fwd.x * acceleration + dx;
		motionY = fwd.y * acceleration + dy;
		motionZ = fwd.z * acceleration + dz;
	}

	public void onUpdate()
	{

		super.onUpdate();

		if (ticksExisted > this.lifeSpan)
		{
			detonate();
			return;
		}

		if (!launched)
		{
			launched = true;
			onLaunch();
		}

		// Vec3 posStart = Vec3.createVector(posX, posY, posZ); from 1.2.5
		// Vec3 posStart = Vec3.getVec3Pool().getVecFromPool(posX, posY, posZ);
		// // new in 1.3.2
		// Vec3 posEnd = Vec3.getVec3Pool().getVecFromPool(posX + motionX, posY
		// + motionY, posZ + motionZ);
		Vec3 posStart = Vec3.createVectorHelper(posX, posY, posZ); // new in
																	// 1.4.5
		Vec3 posEnd = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

		MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(posStart, posEnd);
		if (movingobjectposition != null)
		{
			posEnd = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		setPosition(posEnd.xCoord, posEnd.yCoord, posEnd.zCoord);


		// check for nearby entities
		Entity entity = null;
		double closest = 1.0;

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 1.0D, 1.0D));
		for (int i = 0; i < list.size(); i++)
		{
			Entity nextEntity = (Entity) list.get(i);
			if (nextEntity == null)
				continue;
			if (!nextEntity.canBeCollidedWith())
				continue;

			float f4 = 0.3F;
			AxisAlignedBB axisalignedbb = nextEntity.boundingBox.expand(f4, f4, f4);
			MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(posStart, posEnd);

			if (movingobjectposition1 == null)
				continue;

			double distanceToEntity = posStart.distanceTo(movingobjectposition1.hitVec);
			if (distanceToEntity < closest)
			{
				entity = nextEntity; // remember closest entity
				closest = distanceToEntity;
			}
		}

		if (entity != null)
		{
			// we hit an entity!
			movingobjectposition = new MovingObjectPosition(entity);

			if (owner != null && (owner.equals(entity.riddenByEntity) || owner.equals(entity)))
			{

			}
			else if (!(entity instanceof EntityLaser))
			{
				strikeEntity(entity);
			}
			return;
		}

		if (movingobjectposition != null) // we hit something besides an entity
		{
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY - 0.2 - (double) yOffset);
			int k = MathHelper.floor_double(posZ);
			int blockId = worldObj.getBlockId(i, j, k);

			String particle = "snowballpoof"; // default particle type
			if (blockId > 0) // we hit a block top, so kick up some specific
								// debris
			{
				int blockData = worldObj.getBlockMetadata(i, j, k);
				particle = (new StringBuilder()).append("tilecrack_").append(blockId).append("_").append(blockData).toString();
			}
			for (int count = 0; count < 4; count++)
			{
				worldObj.spawnParticle(particle, posX + ((double) rand.nextFloat() - 0.5), boundingBox.minY + 0.1, posZ + ((double) rand.nextFloat() - 0.5), 1.0 + ((double) rand.nextFloat() - 0.5),
						1.0 + ((double) rand.nextFloat() - 0.5), 1.0 + ((double) rand.nextFloat() - 0.5));
			}

			detonate();
			return;
		}
	}

	void doSplashDamage(double splashSize, int damage)
	{
		if (worldObj.isRemote)
			return; // only applies on server
		if (ticksExisted < 30)
		{
			return;
		}

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(splashSize, splashSize, splashSize));
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity) list.get(i);
			if (!entity.canBeCollidedWith())
				continue;

			// String idMsg = "[Pilot " + owner.entityId + "] hit [Entity " +
			// entity.entityId + "]";
			String idMsg = "[Pilot " + owner + "] hit [Entity " + entity + "]";

			// entity.attackEntityFrom(new
			// EntityDamageSource("projectile splash damage", owner), damage);
			// // splash damage is same as rocket hit
			entity.attackEntityFrom(new EntityDamageSource(idMsg, owner), damage); // splash
																					// damage
																					// is
																					// same
																					// as
																					// rocket
																					// hit
		}
	}

	@Override
	public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int unused)
	{
		// bypassing check for collision in super method which causing entity to
		// shoot up upon impact
		setPositionAndRotation(posX, posY, posZ, yaw, pitch);
	}

	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.boundingBox;
	}

	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}

	abstract float getAcceleration();

	void onLaunch()
	{
		if (!worldObj.isRemote) // send initial server update packet to all
								// clients to set pitch and yaw
		{
			// to be replaced by dataWatcher
			sendUpdatePacketFromServer();
		}
	}

	abstract void createParticles();

	abstract void strikeEntity(Entity entity);

	abstract void detonate();
}
