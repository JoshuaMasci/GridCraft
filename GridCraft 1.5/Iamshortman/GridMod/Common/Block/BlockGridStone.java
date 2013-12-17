package Iamshortman.GridMod.Common.Block;

import Iamshortman.GridMod.GridCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockGridStone extends Block
{
	public BlockGridStone(int ID)
	{
		super(ID, GridCraft.Gridrock);
	}

	@Override
	public void registerIcons(IconRegister iconRegistry)
	{
		this.blockIcon = iconRegistry.registerIcon("GridCraft:stone");
	}
	
}
