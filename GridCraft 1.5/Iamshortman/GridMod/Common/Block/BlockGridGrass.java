package Iamshortman.GridMod.Common.Block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import Iamshortman.GridMod.Client.GridBiomeColorHelper;
import Iamshortman.GridMod.Client.Forge.ColorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockGridGrass extends Block
{
	
	public BlockGridGrass(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public void registerIcons(IconRegister iconRegistry)
	{
		this.blockIcon = iconRegistry.registerIcon("GridCraft:Grass");
	}
	
	@Override
    public int getRenderColor(int par1)
    {
		return ColorHelper.getColorFromRBG(0, 0, 255);
    }
	
	
    @SideOnly(Side.CLIENT)

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
                int i2 = GridBiomeColorHelper.getBiomoeColor(par1IBlockAccess, par2 + l1, par4 + k1);
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }
}
