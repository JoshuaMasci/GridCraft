package Iamshortman.GridMod.Client.GUI;

import Iamshortman.GridMod.Common.Container.ContainerCodeRevTable;
import Iamshortman.GridMod.Common.Container.ContainerColorChangingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GridGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new ContainerCodeRevTable(player.inventory);
		}
		if(ID == 1)
		{
			return new ContainerColorChangingTable(player.inventory);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new GuiCodeRevTable(player.inventory);
		}
		if(ID == 1)
		{
			return new GuiColorChangingTable(player.inventory);
		}
		
		return null;
	}

}
