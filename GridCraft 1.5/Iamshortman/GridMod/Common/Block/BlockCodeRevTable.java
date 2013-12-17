package Iamshortman.GridMod.Common.Block;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.Item.ItemJetBaton;
import Iamshortman.GridMod.Common.Item.ItemUpgradeHelper;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import Iamshortman.GridMod.Common.Item.Upgrades.UpgradeSpeed;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCodeRevTable extends BlockContainer
{

	public BlockCodeRevTable(int par1)
	{
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityCodeRevTable();
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return GridCraft.CodeRevID;
	}

	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.blockIcon = par1IconRegister.registerIcon("GridCraft:ColorChanging_bottom");
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
	            Player.openGui(GridCraft.TronInstance, 0, par1World,par2, par3, par4);

	            return true;
	        }
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
		int p = MathHelper.floor_double((double) ((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3; // this
		// equation
		byte byte0 = 3;

		if (p == 0)
		{
			byte0 = 2;
		}
		if (p == 1)
		{
			byte0 = 1;
		}
		if (p == 2)
		{
			byte0 = 4;
		}
		if (p == 3)
		{
			byte0 = 3;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0, 0);

	}

}
