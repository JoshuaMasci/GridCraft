package Iamshortman.GridMod.Common.Item;

import java.lang.*;
import java.util.List;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;
import Iamshortman.GridMod.Client.ItemRender.IColoredEnchentmentEffect;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.world.World;

public class ItemJetBaton extends TronItem
{

	public ItemJetBaton(int i)
	{
		super(i);
		maxStackSize = 1;
		this.hasSubtypes = true;
		this.setCreativeTab(CreativeTabs.tabTransport);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer Player)
	{
		if (!par2World.isRemote)
		{
			spawnjet(par1ItemStack, par2World, Player);
		}
		return par1ItemStack;
	}

	public int getIconFromDamage(int par1)
	{
		return par1;
	}

	private void spawnjet(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.ridingEntity != null || par3EntityPlayer.onGround)
		{
			return;
		}
		EntityLightJet entity = new EntityLightJet(par2World);
		entity.setColor(par1ItemStack.getItemDamage());
		entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, par3EntityPlayer.rotationPitch);
		
		if(ItemUpgradeHelper.doesItemHaveUpgrade(par1ItemStack, Upgrade.LightJetQuadGun) != 0)
		{
			entity.quadGunUpgrade = true;
		}
		if(ItemUpgradeHelper.doesItemHaveUpgrade(par1ItemStack, Upgrade.LightJetAgility) != 0)
		{
			entity.quadWingUpgrade = true;
		}
		
		par2World.spawnEntityInWorld(entity);
		par3EntityPlayer.mountEntity(entity);
	}

	public boolean isFull3D()
	{
		return true;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}

	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 5));
		par3List.add(new ItemStack(par1, 1, 6));
	}
}
