package Iamshortman.GridMod.Common.Item;

import Iamshortman.GridMod.Common.Entity.EntityLightTank;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemTankPlacer extends Item
{

	public ItemTankPlacer(int par1)
	{
		super(par1);
	}
	
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            int i1 = par3World.getBlockId(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && Block.blocksList[i1] != null && Block.blocksList[i1].getRenderType() == 11)
            {
                d0 = 0.5D;
            }

            EntityLightTank entity = new EntityLightTank(par3World);
            entity.setPositionAndRotation(par4, par5, par6, par2EntityPlayer.rotationYaw, 0);
            entity.rotationYawTurret = entity.rotationYaw;
            par3World.spawnEntityInWorld(entity);
            
            if (entity != null)
            {
                if (!par2EntityPlayer.capabilities.isCreativeMode)
                {
                    --par1ItemStack.stackSize;
                }
            }

            return true;
        }
    }

}
