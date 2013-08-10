package Iamshortman.GridMod.Client.Forge;

import Iamshortman.GridMod.Common.Entity.TronEntity;
import Iamshortman.GridMod.Common.Network.EntityPacket250Data;
import net.minecraft.client.entity.EntityClientPlayerMP;
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
        EntityClientPlayerMP thePlayer = ModLoader.getMinecraftInstance().thePlayer;
        
        EntityPacket250Data data = new EntityPacket250Data(packet);
        
        TronEntity entity = (TronEntity) thePlayer.worldObj.getEntityByID(data.entityId);
        if (entity != null)
        {
            entity.lastUpdatePacket = data;
        }
	}

}
