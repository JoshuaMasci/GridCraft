package Iamshortman.GridMod.Common.Block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Iamshortman.GridMod.GridCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGridWater extends BlockStationary
{
	public BlockGridWater(int par1, Material par2Material)
	{
		super(par1, par2Material);
		
		super.disableStats();
	}
    
	@Override
	public void registerIcons(IconRegister iconRegistry)
	{
		this.theIcon = new Icon[] {iconRegistry.registerIcon("GridCraft:grid_water_flowing"), iconRegistry.registerIcon("GridCraft:grid_water")};
	}
}
