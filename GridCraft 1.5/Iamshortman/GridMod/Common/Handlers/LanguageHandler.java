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
		LanguageRegistry.instance().addStringLocalization("Iamshortman.Grid.CodeRevTable", "Code Revision Table");
		LanguageRegistry.instance().addStringLocalization("Iamshortman.Grid.ColorChanging", "Color Changing Table");
	}

	private static void addBlockNames()
	{
		LanguageRegistry.addName(GridCraft.codeRevTable, "Code Revisison Table");
		LanguageRegistry.addName(GridCraft.portal, "Grid Portal");
		LanguageRegistry.addName(GridCraft.colorChangingTable, "Color Changing Table");
		LanguageRegistry.addName(GridCraft.gridRock, "Grid Stone");
		LanguageRegistry.addName(GridCraft.gridTorch, "Grid Torch");
	}

	private static void addItemNames()
	{
		LanguageRegistry.addName(GridCraft.jetBaton, "Baton Light Jet");
		LanguageRegistry.addName(GridCraft.cycleBaton, "Baton Light Cycle");
	}

}
