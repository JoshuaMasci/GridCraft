package Iamshortman.GridMod.Common.Block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockGridWaterFlowing extends BlockFlowing
{
	public BlockGridWaterFlowing(int par1, Material par2Material)
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
