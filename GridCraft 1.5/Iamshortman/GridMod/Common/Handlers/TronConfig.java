package Iamshortman.GridMod.Common.Handlers;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;

public class TronConfig
{
	// blocks
	public static int jetBatonID;
	public static int cycleBatonID;

	// blocks
	public static int codeRevTableID;
	public static int colorChangeTableID;
	public static int portalID;
	public static int gridRockID;
	public static int gridTorchID;
	public static int gridGrassID;
	public static int gridWaterID;
	public static int gridWaterFlowingID;
	
	// Render Stuff
	public static boolean SupermanMode;

	public static void Load(FMLPreInitializationEvent evt)
	{
		Configuration config = new Configuration(evt.getSuggestedConfigurationFile());

		config.load();
		jetBatonID = config.getItem("Jet Baton", 4200).getInt();
		cycleBatonID = config.getItem("Cycle Baton", 4201).getInt();
		codeRevTableID = config.getBlock("Code Rev Table", 2000).getInt();
		portalID = config.getBlock("Portal Block", 2001).getInt();
		colorChangeTableID = config.getBlock("Color Changing Table", 2002).getInt();
		gridRockID = config.getBlock("Grid Stone", 2003).getInt();
		gridTorchID = config.getBlock("Grid Torch", 2004).getInt();
		gridGrassID = config.getBlock("Grid Grass", 2005).getInt();
		gridWaterID = config.getBlock("Grid Water", 2007).getInt();
		
		SupermanMode = config.get("Render", "SupermanMode", false).getBoolean(false);
		
		config.save();
	}

}
