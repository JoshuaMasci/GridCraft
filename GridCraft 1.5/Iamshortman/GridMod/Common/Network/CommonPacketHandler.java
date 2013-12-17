package Iamshortman.GridMod.Common.Network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import Iamshortman.GridMod.Common.Entity.TronEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class CommonPacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		EntityPlayerMP thePlayer = (EntityPlayerMP) player;

		ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
		DataInputStream dis = new DataInputStream(bis);
		int ID = -1;
		try
		{
			ID = dis.readInt();
			
			if(ID == 0)
			{ 
				
				EntityPacket250Data data = new EntityPacket250Data(packet);
				
				TronEntity entity = (TronEntity) thePlayer.worldObj.getEntityByID(data.entityId);
				if (entity != null)
				{
					if(entity.riddenByEntity != null)
					{
						PacketDispatcher.sendPacketToAllPlayers(packet);
					}
					entity.lastUpdatePacket = data;
				}
			}
			else if(ID == 1)
			{
				ItemUpgradePacket.Unpack(packet, (EntityPlayer) player);
			}
			else if(ID == 2)
			{
				ItemColorPacket.Unpack(packet, (EntityPlayer) player);
			}

		}
		catch(IOException ex)
		{
			
		}
		
	}

}
