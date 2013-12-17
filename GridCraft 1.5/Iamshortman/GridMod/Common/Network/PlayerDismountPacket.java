package Iamshortman.GridMod.Common.Network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Entity.TronEntity;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

public class PlayerDismountPacket
{
	public static Packet250CustomPayload createPacket(EntityPlayer Entity)
	{
		ByteArrayOutputStream baos = null;
		try
		{
		baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		out.writeInt(getPacketID());
		out.writeInt(Entity.riddenByEntity.entityId);
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
			int entityID = in.readInt();
			TronEntity entity = (TronEntity) player.worldObj.getEntityByID(entityID);
			player.unmountEntity(entity);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
