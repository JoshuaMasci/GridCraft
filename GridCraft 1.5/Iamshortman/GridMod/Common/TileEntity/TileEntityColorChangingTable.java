package Iamshortman.GridMod.Common.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityColorChangingTable extends TileEntity
{
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setInteger("x", this.xCoord);
		par1NBTTagCompound.setInteger("y", this.yCoord);
		par1NBTTagCompound.setInteger("z", this.zCoord);
	}
	
}
