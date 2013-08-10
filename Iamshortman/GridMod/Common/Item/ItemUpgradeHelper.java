package Iamshortman.GridMod.Common.Item;

import Iamshortman.GridMod.Client.ItemRender.EnumEnchantmentColor;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemUpgradeHelper
{
	public static boolean hasEffect(ItemStack item)
	{
		if(item == null)
		{
			return false;
		}
        return item.stackTagCompound != null && item.stackTagCompound.hasKey("upgrd");
	}
	
	
	public static void addUpgrade(ItemStack item , Upgrade upgrade)
	{
        if (item.stackTagCompound == null)
        {
            item.setTagCompound(new NBTTagCompound());
        }

        if (!item.stackTagCompound.hasKey("upgrd"))
        {
            item.stackTagCompound.setTag("upgrd", new NBTTagList("upgrd"));
        }

        NBTTagList var3 = (NBTTagList)item.stackTagCompound.getTag("upgrd");

    	
        for (int var7 = 0; var7 < var3.tagCount(); ++var7)
        {
            short var8 = ((NBTTagCompound)var3.tagAt(var7)).getShort("id");
            
            if (var8 == upgrade.id)
            {
                short var9 = ((NBTTagCompound)var3.tagAt(var7)).getShort("lvl");
                
                
            	if(var9 + 1 <= upgrade.GetMaxLevel())
            	{
            		System.out.println("id: " + var8 + " lvl: " + var9);
            		((NBTTagCompound)var3.tagAt(var7)).setShort("lvl",(byte)(var9 + 1));
            	}
            	return;
            }
        }
        
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("id", (short)upgrade.id);
        nbt.setShort("lvl", (short)((byte)1));
        var3.appendTag(nbt);
	}
	
	public static EnumEnchantmentColor getColor(ItemStack item)
	{
		EnumEnchantmentColor color = EnumEnchantmentColor.None;
		if(ItemUpgradeHelper.hasEffect(item))
		{
			int topLevel = 0;
	        if (item.stackTagCompound.hasKey("upgrd"))
	        {
	            NBTTagList var3 = (NBTTagList)item.stackTagCompound.getTag("upgrd");
	            for (int var7 = 0; var7 < var3.tagCount(); ++var7)
	            {
	                short var8 = ((NBTTagCompound)var3.tagAt(var7)).getShort("id");
	                short var9 = ((NBTTagCompound)var3.tagAt(var7)).getShort("lvl");
	                if(topLevel < var9)
	                {
	                	color = Upgrade.list[var8].getColor();
	                }
	                
	            }
	        }
		}
		
		return color;
	}
	
    public static NBTTagList getUpgradeTagList(ItemStack item)
    {
        return item.stackTagCompound == null ? null : (NBTTagList)item.stackTagCompound.getTag("upgrd");
    }
	/**
	 * Returns the level of the upgrade. returns 0 if it has none.
	 * @param item
	 * @param upgrade
	 * @return
	 */
    public static int doesItemHaveUpgrade(ItemStack item, Upgrade upgrade)
    {
    	if(!hasEffect(item))
    	{	
    		return 0;
    	}
    	
        if (item.stackTagCompound.hasKey("upgrd"))
        {
            NBTTagList var3 = (NBTTagList)item.stackTagCompound.getTag("upgrd");
            for (int var7 = 0; var7 < var3.tagCount(); ++var7)
            {
                short var8 = ((NBTTagCompound)var3.tagAt(var7)).getShort("id");
                
                if (var8 == upgrade.id)
                {
                    short var9 = ((NBTTagCompound)var3.tagAt(var7)).getShort("lvl");
                    return var9;
                }
            }
        }
    	return 0;
    }
	
	
}
