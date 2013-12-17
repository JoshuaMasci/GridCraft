package Iamshortman.GridMod.Common.Item.Upgrades;

import Iamshortman.GridMod.Common.Item.Upgrades.LightJet.UpgradeLightJet;

public class UpgradeSpeed extends UpgradeLightJet
{
	public UpgradeSpeed(int i)
	{
		super(i);
	}

	@Override
	public int GetCostForLevel(int I)
	{
		return 5 * I;
	}
	
	public static float getSpeedModifirer(String string, int Level)
	{
		if(string.equals("LightCycle"))
		{
			switch(Level)
			{
				case 0: return 1.0F;
				case 1: return 1.2F;
				case 2: return 1.4F;
				case 3: return 1.6F;
			}
		}
		
		return 1.0F;
	}
}
