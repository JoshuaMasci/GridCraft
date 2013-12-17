package Iamshortman.GridMod.Common.Block;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.TileEntity.TileEntityColorChangingTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockColorChangingTable extends Block
{
	public Icon Top,Side;
	
	
	
	public BlockColorChangingTable(int par1)
	{
		super(par1, Material.wood);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		this.setLightOpacity(0);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	Top = par1IconRegister.registerIcon("GridCraft:ColorChanging_top");
    	Side = par1IconRegister.registerIcon("GridCraft:ColorChanging_side");
    	this.blockIcon = par1IconRegister.registerIcon("GridCraft:ColorChanging_bottom");
    }
	
	@Override
    public Icon getIcon(int par1, int par2)
    {
        if(par1 == 1)
        {
        	return Top;
        }
        else if(par1 == 0)
        {
        	return this.blockIcon;
        }
        else
        {
        	return Side;
        }
    }
	
	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	
	
	
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer Player, int par6, float par7, float par8, float par9)
	{
	        if (par1World.isRemote)
	        {
	            return true;
	        }
	        else
	        {

	            if(Player.isSneaking())
	            {
	            	return false;
	          	}
	            Player.openGui(GridCraft.TronInstance, 1, par1World,par2, par3, par4);

	            return true;
	        }
	}

}
