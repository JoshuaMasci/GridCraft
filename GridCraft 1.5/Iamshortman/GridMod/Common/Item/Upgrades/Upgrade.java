package Iamshortman.GridMod.Common.Item.Upgrades;

import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;
import Iamshortman.GridMod.Common.Item.Upgrades.LightJet.UpgradeLightJetAgility;
import Iamshortman.GridMod.Common.Item.Upgrades.LightJet.UpgradeLightJetQuadGun;
import Iamshortman.GridMod.Common.Item.Upgrades.LightJet.UpgradeLightJetTakeOff;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Upgrade
{
	public final int id;
	public String displayName = "none";
	public static Upgrade[] list = new Upgrade[400];

	public static final Upgrade Speed = new UpgradeSpeed(0).setUpgradeName("Speed");
	public static final Upgrade LightJetQuadGun = new UpgradeLightJetQuadGun(1).setUpgradeName("Gun");
	public static final Upgrade LightJetAgility = new UpgradeLightJetAgility(2).setUpgradeName("Agility");
	public static final Upgrade LightJetJump = new UpgradeLightJetTakeOff(3).setUpgradeName("Take-Off");
	public Upgrade(int I)
	{
		this.id = I;
		if (list[I] == null)
		{
			list[I] = this;
		}
		else
		{
			System.out.println("Error Attempting to override an Upgrade at id " + I);
		}
	}

	public Upgrade setUpgradeName(String str)
	{
		this.displayName = str;
		return this;
	}

	public int GetMaxLevel()
	{
		return 3;
	}

	public EnumEnchantmentColor getColor()
	{
		return EnumEnchantmentColor.Yellow;
	}
	
	public int GetCostForLevel(int I)
	{
		return 10;
	}

}
