package Iamshortman.GridMod.Common.Forge;

import net.minecraft.src.ModLoader;
import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.Forge.ColorHelper;
import Iamshortman.GridMod.Common.Entity.EntityLaser;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Entity.EntityLightTank;
import Iamshortman.GridMod.Common.Entity.EntityLightTrail;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class GridCommonProxy
{

	public void preInit(FMLPreInitializationEvent evt)
	{

	}

	int drawDistance = 160; // typically 160, reduce to 20 for testing
							// spawn/despawn
	int updateFreq = 1; // 20 for 1 second updates, 2 for every other tick
	boolean trackMotion = true;

	public void load(FMLInitializationEvent evt)
	{

		int entityId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLaser.class, "JetLaser", entityId);
		EntityRegistry.instance().registerModEntity(EntityLaser.class, "JetLaser", entityId, GridCraft.TronInstance, drawDistance, updateFreq, trackMotion);

		int entityId1 = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLightJet.class, "LightJet", entityId1);
		EntityRegistry.instance().registerModEntity(EntityLightJet.class, "LightJet", entityId1, GridCraft.TronInstance, drawDistance, updateFreq, trackMotion);

		int entityId2 = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLightCycle.class, "LightCycle", entityId2);
		EntityRegistry.instance().registerModEntity(EntityLightCycle.class, "LightCycle", entityId2, GridCraft.TronInstance, drawDistance, updateFreq, trackMotion);
	
	
		int entityId3 = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLightTrail.class, "LightTrail", entityId3);
		EntityRegistry.instance().registerModEntity(EntityLightTrail.class, "LightTrail", entityId3, GridCraft.TronInstance, drawDistance, updateFreq, trackMotion);
	
		int entityId4 = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLightTank.class, "LightTank", entityId4, ColorHelper.getColorFromRBG(0, 155, 255),0);
		EntityRegistry.instance().registerModEntity(EntityLightTank.class, "LightTank", entityId4, GridCraft.TronInstance, drawDistance, updateFreq, trackMotion);
	}

	public void postInit(FMLPostInitializationEvent evt)
	{

	}

	public void customBlockRender()
	{

	}

	public void RenderStuff()
	{

	}
}
