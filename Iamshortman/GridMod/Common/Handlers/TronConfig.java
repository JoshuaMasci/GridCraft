package Iamshortman.GridMod.Common.Handlers;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;

public class TronConfig
{
	//blocks
	public static int JetBatonID;
	public static int CycleBatonID;
	
	//blocks
	public static int CodeRevTableID;
	public static int ColorChangeTableID;
	
	//Render Stuff
	public static boolean SupermanMode;
	
	public static void Load(FMLPreInitializationEvent evt) 
	{
        Configuration config = new Configuration(evt.getSuggestedConfigurationFile());

        config.load();
        JetBatonID = config.getItem("JetBatonID", 4200).getInt();
        CycleBatonID = config.getItem("CycleBatonID", 4201).getInt();
        CodeRevTableID = config.getBlock("CodeRevTableID", 2000).getInt();
        SupermanMode = config.get("Render", "SupermanMode", false).getBoolean(false);
        
        config.save();
	}
	
	
}
