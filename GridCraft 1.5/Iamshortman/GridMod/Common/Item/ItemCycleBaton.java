package Iamshortman.GridMod.Common.Item;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.Render.RenderLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemCycleBaton extends TronItem
{

	private Icon iconColor;

	public ItemCycleBaton(int i)
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
			spawnCycle(par1ItemStack, par2World, Player);
		}
		return par1ItemStack;
	}

	private void spawnCycle(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.ridingEntity != null)
		{
			return;
		}
		EntityLightCycle entityliving = new EntityLightCycle(par2World);
		entityliving.setColor(ItemColorHelper.getItemColor(par1ItemStack));
		entityliving.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0);
		entityliving.speedLevel = ItemUpgradeHelper.doesItemHaveUpgrade(par1ItemStack, Upgrade.Speed);
		par2World.spawnEntityInWorld(entityliving);
		par3EntityPlayer.mountEntity(entityliving);
	}
	
	public boolean isFull3D()
	{
		return true;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}
	
	@Override
	public List getUpgradeList(ItemStack item, List list)
	{
		list.add(Upgrade.Speed);
		return list;
	}
	
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{

		par3List.add(ItemColorHelper.setItemColor(new ItemStack(par1,1,0), 0, 255, 255));
	}
	
	@SideOnly(Side.CLIENT)
	// registers icons
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("GridCraft:BatonColor");
		this.iconColor = par1IconRegister.registerIcon("GridCraft:BatonBase");
	}
	
	@Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
    {
		return pass == 0 ? ItemColorHelper.getItemColor(par1ItemStack) : -1;
    }
	
	@Override
    public Icon getIcon(ItemStack stack, int pass)
    {
        return pass == 1 ? this.iconColor : this.itemIcon;
    }
	
	@Override
    public boolean requiresMultipleRenderPasses()
    {
    	return true;
    }
	
	@Override
	public void RenderModelInColorTable(Minecraft mc, ItemStack item, int Color)
	{
		
        float Rotation = ((System.currentTimeMillis()/ 100) % 1440);
        GL11.glTranslatef(0.75F, 0.25F, 0);
        GL11.glRotatef(Rotation, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180, 0, 0, 1);
		RenderLightCycle.RenderInMenu(mc, Color, item);
        GL11.glTranslatef(-0.75F, 0.0F, 0);
		
	}
}
