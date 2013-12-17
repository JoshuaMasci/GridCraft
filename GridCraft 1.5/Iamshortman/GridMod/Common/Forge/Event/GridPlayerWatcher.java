package Iamshortman.GridMod.Common.Forge.Event;

import cpw.mods.fml.common.IPlayerTracker;
import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class GridPlayerWatcher implements IPlayerTracker
{
	@ForgeSubscribe
	public void OnPlayerInteract(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		if(player.getCurrentEquippedItem().getItem() != null && player.getCurrentEquippedItem().getItem() == Item.bed && event.action == event.action.RIGHT_CLICK_BLOCK && player.dimension == GridCraft.dimension)
		{
			event.setCanceled(true);
		}
	}

	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player)
	{
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player)
	{
		
	}
}
