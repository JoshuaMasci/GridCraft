package Iamshortman.GridMod.Common.Item;

import Iamshortman.GridMod.Client.Forge.ColorHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemColorHelper
{
	
	public static int getItemColor(ItemStack item)
	{
		if(item.stackTagCompound != null && item.stackTagCompound.hasKey("tColor"))
		{
			return item.stackTagCompound.getInteger("tColor");
		}
		return 0;
	}
	
	public static ItemStack setItemColor(ItemStack item, int Red, int Blue, int Green)
	{
		if(item.stackTagCompound == null)
		{
			item.stackTagCompound = new NBTTagCompound();
		}
		int color = ColorHelper.getColorFromRBG(Red, Blue, Green);
		item.stackTagCompound.setInteger("tColor", color);
		return item;
	}
	
	public static ItemStack setItemColor(ItemStack item, int color)
	{
		if(item.stackTagCompound == null)
		{
			item.stackTagCompound = new NBTTagCompound();
		}
		item.stackTagCompound.setInteger("tColor", color);
		return item;
	}

	
}
