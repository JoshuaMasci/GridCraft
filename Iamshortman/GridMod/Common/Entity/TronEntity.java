package Iamshortman.GridMod.Common.Entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import Iamshortman.GridMod.Common.Network.EntityPacket250Data;
import Iamshortman.GridMod.Common.util.Vector3;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class TronEntity extends Entity
{   
    public Minecraft minecraft;
    
    public int Color = -1;
    
    
    final float RAD_PER_DEG = 00.01745329f;
    final float PI = 03.14159265f;

    public long prevTime;
    float deltaTime;

    double prevMotionX;
    double prevMotionY;
    double prevMotionZ;

    public float rotationRoll;
    float prevRotationRoll;

    float yawRad;
    float pitchRad;
    float rollRad;

    public float rotationYawSpeed;
    public float rotationPitchSpeed;
    public float rotationRollSpeed;

    Vector3 pos = new Vector3(); // position
    Vector3 vel = new Vector3(); // velocity
    Vector3 acc = new Vector3(); // velocity
    Vector3 ypr = new Vector3(); // yaw, pitch, roll
    
    // vectors relative to entity orientation
    Vector3 fwd  = new Vector3(); // straight ahead
    Vector3 side = new Vector3(); // left side perp
    Vector3 up   = new Vector3(); // up
    
    public EntityPacket250Data lastUpdatePacket;
    
    public int cmd_reload;
    public int cmd_create_item;
    public int cmd_exit;

    
    public float damage;
    public float throttle;
    
    public Entity owner;
    
    // total update count
    float timeSinceAttacked;
    float timeSinceCollided;
    
    public TronEntity(World world)
    {
        super(world);

        preventEntitySpawning = true;

        prevTime = System.nanoTime();
        this.ignoreFrustumCheck = true;
        if(world.isRemote)
        {
        	minecraft = Minecraft.getMinecraft();
        }
        
    }
    
    public void setColor(int i)
    {
    	this.Color = i;
    }
    
    public int GetColor()
    {
    	return this.Color;
    }
    
    public abstract String getTextureFromColor();
    
    @Override
    protected void entityInit()
    {
    	this.dataWatcher.addObject(19, new Byte((byte)0));
    	this.dataWatcher.addObject(20, new Byte((byte)0));
    }
    
    void readDataWatcher()
    {
    	 byte temp = this.dataWatcher.getWatchableObjectByte(19);
    	 if(temp == 1)
    	 {
    		 this.onGround = true;
    	 }
    	 else
    	 {
    		 this.onGround = false;
    	 }
    	this.Color = this.dataWatcher.getWatchableObjectByte(20);
    	
        assertClientSideOnly();

    }
    
    void updateDataWatcher()
    {
        assertServerSideOnly();
   	 if(this.onGround)
   	 {
   		this.dataWatcher.updateObject(19, (byte) 1);
   	 }
   	 else
   	 {
   		this.dataWatcher.updateObject(19, (byte) 0);
   	 }
   	 this.dataWatcher.updateObject(20, (byte) Color);
    }
    
    @Override
    public void onUpdate()
    {
    	super.onUpdate();
        ticksExisted++;
        
        long time = System.nanoTime();
        deltaTime = ((float) (time - prevTime)) / 1000000000f; // convert to sec
        //log(String.format("delta time: %6.4f", deltaTime));
        if (deltaTime > .05f) deltaTime = .05f; // 20 ticks per second
        prevTime = time;

        // 
        lastTickPosX = prevPosX = posX;
        lastTickPosY = prevPosY = posY;
        lastTickPosZ = prevPosZ = posZ;

        prevRotationPitch = rotationPitch;
        prevRotationYaw = rotationYaw;
        prevRotationRoll = rotationRoll;
        
        // apply custom update packet 250 if any
        if (lastUpdatePacket != null)
        {
            if (worldObj.isRemote) applyUpdatePacketFromServer(lastUpdatePacket);
            else applyUpdatePacketFromClient(lastUpdatePacket);
            lastUpdatePacket = null; // only apply once
        }
        
        // read updated values from server on client
        if (worldObj.isRemote) readDataWatcher();
        
        inWater = isInWater();
        
        // decrement cooldown timers
        timeSinceAttacked -= deltaTime;
        timeSinceCollided -= deltaTime;
        
        updateRotation();
        updateVectors();
    }
    
    public boolean isInWater()
    {
        // check for contact with water
        return worldObj.isAABBInMaterial(boundingBox.expand(.0, -.4, .0), Material.water);
    }
    
    /*
     * Normalize all rotations to -180 to +180 degrees (typically only yaw is * affected)
     */
    public void updateRotation()
    {
        rotationYaw %= 360f;
        if (rotationYaw > 180f) rotationYaw -= 360f;
        else if (rotationYaw < -180f) rotationYaw += 360f;
        yawRad = rotationYaw * RAD_PER_DEG;

        rotationPitch %= 360f;
        if (rotationPitch > 180f) rotationPitch -= 360f;
        else if (rotationPitch < -180f) rotationPitch += 360f;
        pitchRad = rotationPitch * RAD_PER_DEG;

        rotationRoll %= 360f;
        if (rotationRoll > 180f) rotationRoll -= 360f;
        else if (rotationRoll < -180f) rotationRoll += 360f;
        rollRad = rotationRoll * RAD_PER_DEG;
    }

    public void updateVectors()
    {
        float cosYaw   = (float) MathHelper.cos(-yawRad - PI);
        float sinYaw   = (float) MathHelper.sin(-yawRad - PI);
        float cosPitch = (float) MathHelper.cos(-pitchRad);
        float sinPitch = (float) MathHelper.sin(-pitchRad);
        float cosRoll  = (float) MathHelper.cos(-rollRad);
        float sinRoll  = (float) MathHelper.sin(-rollRad);

        float num = 2.0F;
        fwd.x = (-sinYaw * cosPitch) * num;
        fwd.y = sinPitch * num;
        fwd.z = (-cosYaw * cosPitch) * num;

        side.x = cosYaw * cosRoll;
        side.y = -sinRoll;
        side.z = -sinYaw * cosRoll;

        Vector3.cross(side, fwd, up);

        pos.x = (float) posX;
        pos.y = (float) posY;
        pos.z = (float) posZ;

        vel.x = (float) motionX;
        vel.y = (float) motionY;
        vel.z = (float) motionZ;

        ypr.x = rotationYaw;
        ypr.y = rotationPitch;
        ypr.z = rotationRoll;
    }

    @Override
    public void setDead()
    {
        super.setDead();
    }
    
    
    /* abstract methods from Entity base class */
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        
    }
    
    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    	
    }
    
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " " + entityId + (worldObj.isRemote ? " (client)" : " (server)");
    }

    
    /* subclasses can react to player pilot right click, e.g. helicopter fires missile */
    void interactByPilot()
    {
    }
    
    @Override
    public boolean interact(EntityPlayer player)
    {
        
        if (player.equals(riddenByEntity))
        {
            interactByPilot();
            return false;
        }
        
        if (player.ridingEntity != null) 
        {
            // player is already riding some other entity?
            return false;
        }
        
        
        // new pilot boarding! on server
		if (!worldObj.isRemote) 
        {
            player.mountEntity(this); // boarding, server
        }
        
        owner = player;
        
        updateRiderPosition();
        
        return true;
    }
    
    void pilotExit()
    {
        // vehicle subclasses will override
    }
    
    void takeDamage(float amount)
    {
        damage += amount;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource damageSource, int damageAmount)
    {
        if (worldObj.isRemote) return false; // server only
        
        if (timeSinceAttacked > 0f || isDead || damageSource == null) return false;
        //timeSinceAttacked = .5f; // sec delay before this entity can be attacked again
        timeSinceAttacked = .2f; // sec delay before this entity can be attacked again
        

        Entity attackingEntity = damageSource.getEntity();        
        if (attackingEntity == null) return false; // when is this the case?
        if (attackingEntity.equals(this)) return false; // ignore damage from self?

        return true;
    }
    
    
    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    @Override
    public boolean canBePushed()
    {
        return true;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox()
    {
        return boundingBox;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return entity.boundingBox;
    }

    @Override
    public boolean isInRangeToRenderDist(double d)
    {
        return d < 128.0 * 128.0;
    }

    private Packet250CustomPayload createUpdatePacket()
    {
        return new EntityPacket250Data(this).createPacket250();
    }
    
    public void sendUpdatePacketFromClient()
    {
        assertClientSideOnly();
        PacketDispatcher.sendPacketToServer(createUpdatePacket());
    }
    
    public void applyUpdatePacketFromClient(EntityPacket250Data packet)
    {
        assertServerSideOnly();
        
        setPositionAndRotation(packet.posX, packet.posY, packet.posZ, packet.yaw, packet.pitch);
        //setRotation(packet.yaw, packet.pitch);
        
        rotationRoll = packet.roll % 360f;

        motionX =  packet.motionX;
        motionY =  packet.motionY;
        motionZ =  packet.motionZ;
        
        damage = packet.damage;
        throttle = packet.throttle;

        // server command queue
        cmd_exit        = packet.cmd_exit;
        
        //updateDataWatcher(); // this will send roll and throttle to clients // now called at the end of onUpdate instead
        
        if (packet.pilotId == 0 && riddenByEntity != null)
        {   
            pilotExit(); 
        }
	        
        //int riddenById = riddenByEntity != null ? riddenByEntity.entityId : 0;
        //plog(String.format("end applyUpdatePacket, pilot %d [posX: %6.3f, posY: %6.3f, posZ: %6.3f, yaw: %6.3f, throttle: %6.3f, motionX: %6.3f, motionY: %6.3f, motionZ: %6.3f]", riddenById, posX, posY, posZ, rotationYaw, throttle, motionX, motionY, motionZ));
    }    
    
    public void sendUpdatePacketFromServer()
    {
        assertServerSideOnly();
        
        Packet250CustomPayload packet = createUpdatePacket();
        
        PacketDispatcher.sendPacketToAllPlayers(packet);
    }
    
    public void applyUpdatePacketFromServer(EntityPacket250Data packet)
    {        
        assertClientSideOnly();
        
        setPositionAndRotation(packet.posX, packet.posY, packet.posZ, packet.yaw, packet.pitch);
        
        rotationRoll = packet.roll % 360f;

        motionX =  packet.motionX;
        motionY =  packet.motionY;
        motionZ =  packet.motionZ;
        
        damage = packet.damage;
        throttle = packet.throttle;


        // no or wrong current pilot
        if (packet.pilotId > 0 && (riddenByEntity == null)) // || riddenByEntity.entityId != packet.pilotId))
        {
            Entity pilot = ((WorldClient) worldObj).getEntityByID(packet.pilotId);
            if (pilot != null && !pilot.isDead)
            {
                pilot.mountEntity(this); // boarding
            }
        }
            
        serverPosX = MathHelper.floor_float(packet.posX * 32f);
        serverPosY = MathHelper.floor_float(packet.posY * 32f);
        serverPosZ = MathHelper.floor_float(packet.posZ * 32f);
        
        //int riddenById = riddenByEntity != null ? riddenByEntity.entityId : 0;
        //plog(String.format("end applyUpdatePacket, pilot %d [posX: %6.3f, posY: %6.3f, posZ: %6.3f, yaw: %6.3f, pitch: %6.3f, roll: %6.3f, throttle: %6.3f, motionX: %6.3f, motionY: %6.3f, motionZ: %6.3f]", riddenById, posX, posY, posZ, rotationYaw, rotationPitch, rotationRoll, throttle, motionX, motionY, motionZ));
    }    
    
    @Override
    public void updateRidden()
    {

    }
    
    @Override
    protected void setBeenAttacked()
    {
    	
    }
    
    void assertClientSideOnly()
    {
        if (worldObj.isRemote) return; // OK, we are on client as expected
        
        throw new RuntimeException("Client-side method was called on Server");
    }
    
    void assertServerSideOnly()
    {
        if (!worldObj.isRemote) return; // OK, we are on server as expected
            
        throw new RuntimeException("Server-side method was called on client");
    }
}
