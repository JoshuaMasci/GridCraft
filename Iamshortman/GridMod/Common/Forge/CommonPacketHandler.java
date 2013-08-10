package Iamshortman.GridMod.Common.Forge;

import Iamshortman.GridMod.Common.Entity.TronEntity;
import Iamshortman.GridMod.Common.Network.EntityPacket250Data;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class CommonPacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
        EntityPlayerMP thePlayer = (EntityPlayerMP) player;
        
        EntityPacket250Data data = new EntityPacket250Data(packet);
        
        TronEntity entity = (TronEntity) thePlayer.worldObj.getEntityByID(data.entityId);
        if (entity != null)
        {
            entity.lastUpdatePacket = data;
            
        }
	}

}
