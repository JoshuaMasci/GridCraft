package Iamshortman.GridMod.Common.TheGrid.Biome;

import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenGridBase extends BiomeGenBase
{
	public static BiomeGenGridBase OutLands = new BiomeGenOutlands(0);
	public static BiomeGenGridBase Plains = new BiomeGenGridPlains(1);
	public static BiomeGenGridBase[] list = {OutLands, Plains};
	
	public BiomeGenGridBase(int par1)
	{
		super(par1 + 170);
        this.setDisableRain();
	}
	
	public double getSmoothingValue()
	{
		return 0.0;
	}

}
