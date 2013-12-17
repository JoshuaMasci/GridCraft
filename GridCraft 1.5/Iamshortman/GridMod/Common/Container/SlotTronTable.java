package Iamshortman.GridMod.Common.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class SlotTronTable extends InventoryBasic
{
	final Container container;
	public SlotTronTable(Container table,String par1Str, int par2)
	{
		super(par1Str, false, par2);
		container = table;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		this.container.onCraftMatrixChanged(this);
	}

	
}
