package Iamshortman.GridMod.Common.TheGrid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Common.TheGrid.Biome.BiomeGenGridBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderGrid extends WorldProvider
{
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunckManagerGrid(this.worldObj.getSeed(),WorldType.DEFAULT);
		this.dimensionId = GridCraft.dimension;
	}
	
	@Override
	public String getDimensionName() 
	{
		return "TheGrid";
	}
	
    @SideOnly(Side.CLIENT)
    /**
     * the y level at which clouds are rendered.
     */
    public float getCloudHeight()
    {
        return 200.0F;
    }
	
	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.35F;
	}
	
    protected void generateLightBrightnessTable()
    {
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }
	
	
	@Override
	public Vec3 getFogColor(float par1, float par2)
	{
		return this.worldObj.getWorldVec3Pool().getVecFromPool(0D, 0D, 0D);
	}
	
	
	@Override
	public boolean canRespawnHere()
	{
		return true;
	}
	
	@Override
	public boolean isSkyColored()
	{
		return true;
	}
	
	@Override
	public ChunkCoordinates getEntrancePortalLocation()
	{
		return new ChunkCoordinates(0,0,0);
	}
	
	@Override
	public String getDepartMessage()
	{
		return "Leaving the Grid";
	}
	
	@Override
	public String getWelcomeMessage()
	{
		return "Entering the Grid";
	}
	
	@Override
	public boolean canSnowAt(int x, int y, int z)
	{
		return false;
	}
	
	@Override
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
	{
		Vec3Pool vecPool = new Vec3Pool(300, 2000);
		return vecPool.getVecFromPool((double) 0, (double) 0, (double) 0);
	}
	
	@Override
	public boolean isDaytime()
	{
		return false;
	}
	
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk)
	{
		return false;
	}
	
	@Override
	public float getStarBrightness(float par1)
	{
		return 0.0F;
	}
	
	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderGrid(worldObj, worldObj.getSeed(), true);
	}
}