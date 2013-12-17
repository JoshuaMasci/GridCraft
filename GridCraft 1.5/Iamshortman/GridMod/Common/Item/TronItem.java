package Iamshortman.GridMod.Common.Item;

import java.util.List;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;
import Iamshortman.GridMod.Client.ItemRender.IColoredEnchentmentEffect;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;

public class TronItem extends Item implements IColoredEnchentmentEffect, IItemUpgradeable , IItemMuitiColored
{

	public TronItem(int par1)
	{
		super(par1);
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (ItemUpgradeHelper.hasEffect(par1ItemStack))
		{
			NBTTagList ntb = ItemUpgradeHelper.getUpgradeTagList(par1ItemStack);
			if (ntb != null)
			{

				for (int var7 = 0; var7 < ntb.tagCount(); ++var7)
				{
					short var8 = ((NBTTagCompound) ntb.tagAt(var7)).getShort("id");
					short var9 = ((NBTTagCompound) ntb.tagAt(var7)).getShort("lvl");
					if (Upgrade.list[var8] != null)
					{
						String str = Upgrade.list[var8].displayName + " V" + var9;
						par3List.add(str);
					}
				}
			}
		}
	}

	@Override
	public List getUpgradeList(ItemStack item , List list)
	{
		return list;
	}

	@Override
	public void RenderModelInColorTable(Minecraft mc, ItemStack item, int Color)
	{
		//Base Item Does Nothing
	}

}
