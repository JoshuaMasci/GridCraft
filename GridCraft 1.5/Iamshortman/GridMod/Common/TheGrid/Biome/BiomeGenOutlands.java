package Iamshortman.GridMod.Common.TheGrid.Biome;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenOutlands extends BiomeGenGridBase
{

	public BiomeGenOutlands(int par1)
	{
		super(par1);
		this.setMinMaxHeight(0.3F, 1.5F);
		this.setBiomeName("Outlands");
		this.topBlock = (byte) Block.obsidian.blockID;
	}

}
