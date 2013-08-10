package Iamshortman.GridMod.Common.Handlers;

import Iamshortman.GridMod.GridCraft;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class LanguageHandler
{
	public static void load()
	{
		addItemNames();
		addBlockNames();
		addOtherNames();
	}

	private static void addOtherNames()
	{

	}

	private static void addBlockNames()
	{
		LanguageRegistry.addName(GridCraft.CodeRevTable,"Code Revisison Table [WIP]");
		
	}

	private static void addItemNames()
	{
		LanguageRegistry.addName(GridCraft.JetBaton, "Baton Light Jet");
		LanguageRegistry.addName(GridCraft.CycleBaton, "Baton Light Cycle");
	}
	
}
