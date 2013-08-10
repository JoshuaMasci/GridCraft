package Iamshortman.GridMod.Common.TileEntity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityCodeRevTable extends TileEntity
{
	public boolean isOpen = false;
	private float screenLoc = 0.0f;
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			boolean FoundPlayer = false;
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2);
			List<Entity> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb);
				if (entities.size() > 0) {
					for (Entity entity : entities) 
					{
						if (entity instanceof EntityPlayer) 
						{
							FoundPlayer = true;
						}
					}
				}
				if(FoundPlayer)
				{
					this.isOpen = true;
				}
				else
				{
					this.isOpen = false;
				}
		}
		
			if(isOpen && this.screenLoc <= 5F)
			{
				this.screenLoc += 0.2F;
			}
			else if(!isOpen && this.screenLoc > 0F)
			{
				this.screenLoc -= 0.2F;
			}	
	}
	
	
	public float getScreenLoc()
	{
		return screenLoc - 4F;
	}
	public float getScreenRot()
	{
		float deg = this.screenLoc * 17;
		return DegtoRad(deg);
	}
	private float DegtoRad(float Deg)
	{
		float Rad = Deg * 0.0174532925F;
		return Rad;
	}
	
	
}
