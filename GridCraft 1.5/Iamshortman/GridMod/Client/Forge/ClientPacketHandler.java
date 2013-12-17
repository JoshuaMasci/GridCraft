package Iamshortman.GridMod.Client.Forge;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import Iamshortman.GridMod.Common.Entity.TronEntity;
import Iamshortman.GridMod.Common.Network.EntityPacket250Data;
import Iamshortman.GridMod.Common.Network.ItemUpgradePacket;
import Iamshortman.GridMod.Common.Network.PlayerDismountPacket;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
		DataInputStream dis = new DataInputStream(bis);
		int ID = -1;
		try
		{
			ID = dis.readInt();
			
			if(ID == 0)
			{
		        EntityClientPlayerMP thePlayer = ModLoader.getMinecraftInstance().thePlayer;
		        
			EntityPacket250Data data = new EntityPacket250Data(packet);

				TronEntity entity = (TronEntity) thePlayer.worldObj.getEntityByID(data.entityId);
				if (entity != null)
				{
					if(data.pilotId != thePlayer.entityId)
					{
						entity.lastUpdatePacket = data;
					}
				}
			}
			else if(ID == 1)
			{
				ItemUpgradePacket.Unpack(packet, (EntityPlayer) player);
			}
			else if(ID == 3)
			{
				PlayerDismountPacket.Unpack(packet, (EntityPlayer) player);
			}

		}
		catch(IOException ex)
		{
			
		}
	}

}
