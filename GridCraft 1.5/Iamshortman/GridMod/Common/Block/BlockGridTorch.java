package Iamshortman.GridMod.Common.Block;

import java.util.Random;

import Iamshortman.GridMod.Client.Particles.EntityBluedustFX;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

public class BlockGridTorch extends BlockTorch
{
	public BlockGridTorch(int par1)
	{
		super(par1);
	}

	@Override
	public void registerIcons(IconRegister iconRegistry)
	{
		this.blockIcon = iconRegistry.registerIcon("GridCraft:Torch");
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	Minecraft mc = Minecraft.getMinecraft();
        int l = par1World.getBlockMetadata(par2, par3, par4);
        double d0 = (double)((float)par2 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.2D;
        double d1 = (double)((float)par3 + 0.7F) + (double)(par5Random.nextFloat() - 0.5F) * 0.2D;
        double d2 = (double)((float)par4 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.2D;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;

        if (l == 1)
        {
        	EntityFX FX = new EntityBluedustFX(par1World, d0 - d4, d1 + d3, d2, 0.0F, 0.0F, 0.0F);
        	mc.effectRenderer.addEffect(FX);
        }
        else if (l == 2)
        {
        	EntityFX FX = new EntityBluedustFX(par1World, d0 + d4, d1 + d3, d2, 0.0F, 0.0F, 0.0F);
        	mc.effectRenderer.addEffect(FX);
        }
        else if (l == 3)
        {
        	EntityFX FX = new EntityBluedustFX(par1World, d0, d1 + d3, d2 - d4, 0.0F, 0.0F, 0.0F);
        	mc.effectRenderer.addEffect(FX);
        }
        else if (l == 4)
        {
        	EntityFX FX = new EntityBluedustFX(par1World, d0, d1 + d3, d2 + d4, 0.0F, 0.0F, 0.0F);
        	mc.effectRenderer.addEffect(FX);
        }
        else
        {
        	EntityFX FX = new EntityBluedustFX(par1World, d0, d1, d2, 0.0F, 0.0F, 0.0F);
        	mc.effectRenderer.addEffect(FX);
        }
    }
}
