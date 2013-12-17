package Iamshortman.GridMod.Common.Item.Upgrades.LightJet;

import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;

public class UpgradeLightJetAgility extends UpgradeLightJet
{
	public UpgradeLightJetAgility(int I)
	{
		super(I);
	}

	@Override
	public int GetMaxLevel()
	{
		return 1;
	}

	@Override
	public EnumEnchantmentColor getColor()
	{
		return EnumEnchantmentColor.Green;
	}
	
	@Override
	public int GetCostForLevel(int I)
	{
		return 5;
	}
}
