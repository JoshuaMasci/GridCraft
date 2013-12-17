package Iamshortman.GridMod.Common.Network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Container.ContainerColorChangingTable;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ItemColorPacket
{
	public static final int ID = 2;
	
	public static Packet250CustomPayload createPacket(int windowID, int Color)
	{
		ByteArrayOutputStream baos = null;
		try
		{
		baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		out.writeInt(ID);
		out.writeInt(windowID);
		out.writeInt(Color);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		
		return new Packet250CustomPayload("TheGrid", baos.toByteArray());
	}
	
	public static void Unpack(Packet250CustomPayload packet, EntityPlayer player)
	{
		try
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));
			int ID = in.readInt();
			int WindowID = in.readInt();
			int Color = in.readInt();
			
			if (player.openContainer.windowId == WindowID && player.openContainer.isPlayerNotUsingContainer(player))
			{
				if(player.openContainer instanceof ContainerColorChangingTable)
				{
					ContainerColorChangingTable colorTable = (ContainerColorChangingTable) player.openContainer;
					colorTable.SetColor(Color);
				}
			}
			
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
}
