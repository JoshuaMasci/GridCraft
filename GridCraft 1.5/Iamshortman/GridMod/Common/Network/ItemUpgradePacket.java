package Iamshortman.GridMod.Common.Network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;

import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ItemUpgradePacket
{
	
	public static Packet250CustomPayload createPacket(int windowID, int UpgradeID ,int Level)
	{
		ByteArrayOutputStream baos = null;
		try
		{
		baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		out.writeInt(getPacketID());
		out.writeInt(windowID);
		out.writeInt(UpgradeID);
		out.writeInt(Level);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		
		return new Packet250CustomPayload("TheGrid", baos.toByteArray());
	}

	public static int getPacketID()
	{
		return 1;
	}

	public static void Unpack(Packet250CustomPayload packet, EntityPlayer player)
	{
		try
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));
			int ID = in.readInt();
			int WindowID = in.readInt();
			int UpgradeID = in.readInt();
			int Level = in.readInt();
			
			if (player.openContainer.windowId == WindowID && player.openContainer.isPlayerNotUsingContainer(player))
			{
				if(player.openContainer instanceof ContainerCodeRevTable)
				{
					ContainerCodeRevTable codeTable = (ContainerCodeRevTable) player.openContainer;
					codeTable.upgradeItem(player, Upgrade.list[UpgradeID], Level);
				}
			}
			
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
}
