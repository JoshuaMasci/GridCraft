package Iamshortman.GridMod.Common.Container;

import cpw.mods.fml.common.network.PacketDispatcher;
import Iamshortman.GridMod.Common.Item.IItemMuitiColored;
import Iamshortman.GridMod.Common.Item.ItemColorHelper;
import Iamshortman.GridMod.Common.Network.ItemColorPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerColorChangingTable extends Container
{
	public InventoryPlayer PlayerInventory;
	public IInventory tableInventory = new SlotTronTable(this, "Item", 1);
	
	public int Color = -1;
	
	public ContainerColorChangingTable(InventoryPlayer par1InventoryPlayer)
	{
		this.PlayerInventory = par1InventoryPlayer;

		this.addSlotToContainer(new SlotColorChanging(this.tableInventory, 0, 17, 14));
		
		int var6;

		for (var6 = 0; var6 < 3; ++var6)
		{
			for (int var7 = 0; var7 < 9; ++var7)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 18 + var7 * 18, 151 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 18 + var6 * 18, 210));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);

		if (!par1EntityPlayer.worldObj.isRemote)
		{
			ItemStack var2 = this.tableInventory.getStackInSlotOnClosing(0);

			if (var2 != null)
			{
				par1EntityPlayer.dropPlayerItem(var2);
			}
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 == 0)
			{
				if (!this.mergeItemStack(var5, 1, 37, true))
				{
					return null;
				}
			}
			else
			{
				if (((Slot) this.inventorySlots.get(0)).getHasStack() || !((Slot) this.inventorySlots.get(0)).isItemValid(var5))
				{
					return null;
				}

				if (var5.hasTagCompound() && var5.stackSize == 1)
				{
					((Slot) this.inventorySlots.get(0)).putStack(var5.copy());
					var5.stackSize = 0;
				}
				else if (var5.stackSize >= 1)
				{
					((Slot) this.inventorySlots.get(0)).putStack(new ItemStack(var5.itemID, 1, var5.getItemDamage()));
					--var5.stackSize;
				}
			}

			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			}
			else
			{
				var4.onSlotChanged();
			}

			if (var5.stackSize == var3.stackSize)
			{
				return null;
			}

			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}
		return var3;
		
	}
	
	public void SetColor(int i)
	{
		if(this.hasItem())
		{
			ItemStack itemStack = this.tableInventory.getStackInSlot(0);
			Item item = itemStack.getItem();
			if(item instanceof IItemMuitiColored)
			{
				ItemColorHelper.setItemColor(itemStack, i);
				System.out.println(i);
			}
		}
	}
	
	public boolean hasItem()
	{
		if(this.tableInventory.getStackInSlot(0) != null)
		{
			return true;
		}
		return false;
	}
	
	public void UpdateColor(int color)
	{
		if(this.hasItem())
		{
			ItemStack itemStack = this.tableInventory.getStackInSlot(0);
			Item item = itemStack.getItem();
			if(item instanceof IItemMuitiColored)
			{
				ItemColorHelper.setItemColor(itemStack, color);
				PacketDispatcher.sendPacketToServer(ItemColorPacket.createPacket(this.windowId, color));
			}
		}
	}
	
	public int GetColor()
	{
		int i = this.Color;
		this.Color = -1;
		return i;
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		if(this.hasItem())
		{
			ItemStack itemStack = this.tableInventory.getStackInSlot(0);
			Item item = itemStack.getItem();
			if(item instanceof IItemMuitiColored)
			{
				this.Color = ItemColorHelper.getItemColor(itemStack);
			}
		}
	}
	
}
