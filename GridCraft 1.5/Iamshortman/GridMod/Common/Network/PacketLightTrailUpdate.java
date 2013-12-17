package Iamshortman.GridMod.Common.Network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Entity.EntityLightTrail;
import Iamshortman.GridMod.Common.Entity.EntityLightTrailPart;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

public class PacketLightTrailUpdate
{
	public static Packet250CustomPayload createPacket(EntityLightTrailPart part)
	{
		ByteArrayOutputStream baos = null;
		try
		{
		baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		out.writeInt(getPacketID());
		out.writeInt(part.parent.entityId);
		out.writeInt(part.Id);
		out.writeDouble(part.posX);
		out.writeDouble(part.posY);
		out.writeDouble(part.posZ);
		out.writeFloat(part.rotationYaw);
		out.writeFloat(part.rotationPitch);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		
		return new Packet250CustomPayload("TheGrid", baos.toByteArray());
	}

	public static int getPacketID()
	{
		return 3;
	}

	public static void Unpack(Packet250CustomPayload packet, EntityPlayer player)
	{
		try
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));
			int ID = in.readInt();
			int parentID = in.readInt();
			int partID = in.readInt();
			double posX = in.readDouble();
			double posY = in.readDouble();
			double posZ = in.readDouble();
			float Yaw = in.readFloat();
			float Pitch = in.readFloat();
			Entity entity = player.worldObj.getEntityByID(parentID);
			if(entity != null && entity instanceof EntityLightTrail)
			{
				EntityLightTrail trail = (EntityLightTrail) entity;
				trail.Trails[partID] = new EntityLightTrailPart(trail.worldObj, trail, partID);
				trail.Trails[partID].setPosition(posX, posY, posZ);
				trail.Trails[partID].setAngles(Yaw, Pitch);
			}
		}
		catch (IOException e)
		{
		}
	}
}
