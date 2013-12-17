package Iamshortman.GridMod.Common.Container;

import java.util.ArrayList;
import java.util.List;

import Iamshortman.GridMod.Common.Item.IItemUpgradeable;
import Iamshortman.GridMod.Common.Item.ItemUpgradeHelper;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerCodeRevTable extends Container
{
	public InventoryPlayer PlayerInventory;
	public IInventory tableInventory = new SlotTronTable(this, "Upgrade", 1);
	public List<Upgrade> upgrades;
	public ItemStack item;
	
	public ContainerCodeRevTable(InventoryPlayer par1InventoryPlayer)
	{
		this.PlayerInventory = par1InventoryPlayer;

		this.addSlotToContainer(new SlotCodeRevTable(this.tableInventory, 0, 12, 19));
		
		int var6;

		for (var6 = 0; var6 < 3; ++var6)
		{
			for (int var7 = 0; var7 < 9; ++var7)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 103 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 161));
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
	
	
	@Override
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
	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		if (par1IInventory == this.tableInventory)
		{
			ItemStack var2 = par1IInventory.getStackInSlot(0);
			if(var2 != null)
			{
				Item item = var2.getItem();
				if(item instanceof IItemUpgradeable)
				{
					this.item = var2;
					IItemUpgradeable upgradeItem = (IItemUpgradeable) item;
					List<Upgrade> list = new ArrayList<Upgrade>();
					this.upgrades = upgradeItem.getUpgradeList(var2, list);
				}
			}
			else
			{
				this.upgrades = null;
			}
		}
	}
	
	/**
	 * Returns 1 Higher then the item all ready has.
	 * @param upgrade
	 * @return
	 */
	public int getUpgradeLevel(Upgrade upgrade)
	{
		int Level = ItemUpgradeHelper.doesItemHaveUpgrade(item, upgrade);
		Level += 1;
		return Level;
	}
	
	public boolean canPlayerAfford(Upgrade upgrade, int i)
	{
		EntityPlayer player = PlayerInventory.player;
		if(player.capabilities.isCreativeMode)
		{
			return true;
		}
		int cost = upgrade.GetCostForLevel(i);
		if(player.experienceLevel >= cost)
		{
			return true;
		}
		
		return false;
	}

	public boolean upgradeItem(EntityPlayer player, Upgrade upgrade, int Level)
	{
		ItemUpgradeHelper.addUpgrade(item, upgrade);
		player.experienceLevel -= upgrade.GetCostForLevel(Level);
		return true;
	}

	public boolean canUserBuy(Upgrade upgrade)
	{
		if(upgrade != null)
		{
			int I = this.getUpgradeLevel(upgrade);
			if(I <= upgrade.GetMaxLevel() && this.canPlayerAfford(upgrade, I))
			{
				return true;
			}
		}
		return false;
	}

	public Upgrade getUpgradeInSlot(int i)
	{
		if(this.upgrades == null || this.item == null)
		{
			return null;
		}
		
		if(this.upgrades.size() > i)
		{
		return this.upgrades.get(i);
		}
		return null;
	}
	
	
	
}
