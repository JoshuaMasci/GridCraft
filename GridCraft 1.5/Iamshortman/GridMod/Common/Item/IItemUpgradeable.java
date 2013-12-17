package Iamshortman.GridMod.Common.Item;

import java.util.List;

import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

import net.minecraft.item.ItemStack;

public interface IItemUpgradeable
{

	public List<Upgrade> getUpgradeList(ItemStack item, List list);

}
