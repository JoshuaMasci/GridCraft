package Iamshortman.GridMod.Common.Item.Upgrades.LightJet;

import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;

public class UpgradeLightJetTakeOff extends UpgradeLightJet
{

	public UpgradeLightJetTakeOff(int I)
	{
		super(I);
	}
	
	@Override
	public int GetMaxLevel()
	{
		return 1;
	}
	
	@Override
	public int GetCostForLevel(int I)
	{
		return 30;
	}

	public EnumEnchantmentColor getColor()
	{
		return EnumEnchantmentColor.Orange;
	}
	
}
