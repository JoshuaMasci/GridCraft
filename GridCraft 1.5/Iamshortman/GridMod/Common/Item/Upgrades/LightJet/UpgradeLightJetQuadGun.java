package Iamshortman.GridMod.Common.Item.Upgrades.LightJet;

import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;

public class UpgradeLightJetQuadGun extends UpgradeLightJet
{

	public UpgradeLightJetQuadGun(int I)
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
		return EnumEnchantmentColor.Red;
	}
	@Override
	public int GetCostForLevel(int I)
	{
		return 15;
	}
}
