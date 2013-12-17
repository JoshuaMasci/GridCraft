package Iamshortman.GridMod.Common.Item;

import java.util.List;

import Iamshortman.GridMod.Client.Model.ModelPlayerBase;
import Iamshortman.GridMod.Client.Model.ModelTronArmorBasic;
import Iamshortman.GridMod.Common.Item.Upgrades.Upgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemTronArmor extends ItemArmor implements IItemUpgradeable, IItemMuitiColored
{

	private ModelBiped modelArmor;
	private ModelBiped modelArmorChestplate;
	private final float chestplateSize = 0.3F;
	private final float armorSize = 0.2F;
	
	public ItemTronArmor(int par1, int par4)
	{
		super(par1, EnumArmorMaterial.IRON, 0, par4);
		this.modelArmor = new ModelBiped(armorSize);
		this.modelArmorChestplate = new ModelTronArmorBasic(chestplateSize);
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (ItemUpgradeHelper.hasEffect(par1ItemStack))
		{
			NBTTagList ntb = ItemUpgradeHelper.getUpgradeTagList(par1ItemStack);
			if (ntb != null)
			{

				for (int var7 = 0; var7 < ntb.tagCount(); ++var7)
				{
					short var8 = ((NBTTagCompound) ntb.tagAt(var7)).getShort("id");
					short var9 = ((NBTTagCompound) ntb.tagAt(var7)).getShort("lvl");
					if (Upgrade.list[var8] != null)
					{
						String str = Upgrade.list[var8].displayName + " V" + var9;
						par3List.add(str);
					}
				}
			}
		}
	}
	
	@Override
	public List<Upgrade> getUpgradeList(ItemStack item, List list)
	{
		return list;
	}

	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		return "";
    }
	
	@Override
    public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemStack, int armorSlot)
    {
		ModelBiped var7 = armorSlot == 2 ? this.modelArmor : this.modelArmorChestplate;
		this.modelArmor = new ModelBiped(armorSize);
		this.modelArmorChestplate = new ModelTronArmorBasic(chestplateSize);
		var7.bipedHead.showModel = armorSlot == 0;
		var7.bipedHeadwear.showModel = armorSlot == 0;
		var7.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
		var7.bipedRightArm.showModel = armorSlot == 1;
		var7.bipedLeftArm.showModel = armorSlot == 1;
		var7.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
		var7.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;
		var7.isSneak = entityLiving.isSneaking();
		var7.isRiding = entityLiving.isRiding();
        ItemStack itemstack = entityLiving.getHeldItem();

        if (itemstack != null && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer) entityLiving).getItemInUseCount() > 0) )
        {
            EnumAction enumaction = itemstack.getItemUseAction();
            
            if (enumaction == EnumAction.block)
            {
                this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = 3;
            }
            else if (enumaction == EnumAction.bow)
            {
                this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = true;
            }
        }
        else if(itemstack != null)
        {
            this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = 1;
        }

        return var7;
    }

	@Override
	public void RenderModelInColorTable(Minecraft mc, ItemStack item, int Color)
	{
		// TODO Auto-generated method stub
		
	}
	
}
