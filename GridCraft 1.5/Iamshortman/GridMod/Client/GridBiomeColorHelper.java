package Iamshortman.GridMod.Client;

import Iamshortman.GridMod.Client.Forge.ColorHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

public class GridBiomeColorHelper
{

	public static int getBiomoeColor(IBlockAccess par1IBlockAccess, int par2, int par4)
	{
		BiomeGenBase Biome = par1IBlockAccess.getBiomeGenForCoords(par2, par4);
		if(Biome == Biome.plains)
		{
			return ColorHelper.getColorFromRBG(0, 0, 255);
		}
		else if(Biome == Biome.extremeHills || Biome == Biome.extremeHillsEdge)
		{
			return ColorHelper.getColorFromRBG(160, 240, 32);
		}
		else if(Biome == Biome.beach || Biome == Biome.desert)
		{
			return ColorHelper.getColorFromRBG(255, 0, 255);
		}
		else if(Biome == Biome.desertHills)
		{
			return ColorHelper.getColorFromRBG(255, 0, 102);
		}
		else if(Biome == Biome.river || Biome == Biome.frozenRiver)
		{
			return ColorHelper.getColorFromRBG(0, 255, 191);
		}
		else if(Biome == Biome.taiga || Biome == Biome.taigaHills)
		{
			return ColorHelper.getColorFromRBG(255, 255, 255);
		}
		else if(Biome == Biome.forest || Biome == Biome.forestHills)
		{
			return ColorHelper.getColorFromRBG(0, 0, 150);
		}
		else if(Biome == Biome.ocean || Biome == Biome.frozenOcean)
		{
			return ColorHelper.getColorFromRBG(200, 0, 0);
		}
		else
		{
			return Biome.getBiomeGrassColor();
		}
	}

}
