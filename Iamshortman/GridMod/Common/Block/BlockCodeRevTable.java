package Iamshortman.GridMod.Common.Block;


import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.Item.ItemJetBaton;
import Iamshortman.GridMod.Common.Item.ItemUpgradeHelper;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import Iamshortman.GridMod.Common.Item.Upgrades.LightJet.UpgradeLightJetSpeed;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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
		super(par1, Material.iron);
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

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		/*if(par1World.isRemote)
		{
			return true;
		}
		
		if(par5EntityPlayer.getCurrentEquippedItem() != null)
		{
			ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
			if(item.getItem().getClass().isAssignableFrom(ItemJetBaton.class) && (par5EntityPlayer.experienceLevel >= 5)|| par5EntityPlayer.capabilities.isCreativeMode)
			{
				if(!par5EntityPlayer.capabilities.isCreativeMode)
				{
					par5EntityPlayer.experienceLevel -= 5;
				}

					ItemUpgradeHelper.addUpgrade(item, Upgrade.LightJetAgility);
			}
		}*/
		return true;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
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

		par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);

	}

}
