package Iamshortman.GridMod.Common.Item;
import java.lang.*;
import java.util.List;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.world.World;

public class ItemCycleBaton extends TronItem
{

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
    	if(!par2World.isRemote)
    	{
        	spawncycle(par1ItemStack, par2World, Player);
    	}
        return par1ItemStack;
    }

    public int getIconFromDamage(int par1)
    {
    	return par1;
    }
    
    private void spawncycle(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(par3EntityPlayer.ridingEntity != null)
    	{
    		return;
    	}
   	 EntityLightCycle entityliving = new EntityLightCycle(par2World);
   	 entityliving.setColor(par1ItemStack.getItemDamage());
   	 entityliving.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw,0);
   	 par2World.spawnEntityInWorld(entityliving);
   	 par3EntityPlayer.mountEntity(entityliving);
    }

    @Override
    public String getTextureFile()
    {
		return GridCraft.items;
    	
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
    
    public boolean isFull3D()
    {
        return true;
    }
      
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
}
