package Iamshortman.GridMod;

import org.lwjgl.input.Keyboard;

import Iamshortman.GridMod.Client.Forge.GridKeyHandler;
import Iamshortman.GridMod.Client.RenderAPI.GridCraftRenderPlayer;
import Iamshortman.GridMod.Common.Block.BlockCodeRevTable;
import Iamshortman.GridMod.Common.Forge.CommonProxy;
import Iamshortman.GridMod.Common.Handlers.LanguageHandler;
import Iamshortman.GridMod.Common.Handlers.TronConfig;
import Iamshortman.GridMod.Common.Item.ItemCycleBaton;
import Iamshortman.GridMod.Common.Item.ItemJetBaton;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderPlayerAPI;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.Mod.Instance;

@Mod(modid = "GridCraft", name = "GridCraft", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels =
{ "TheGrid" }, packetHandler = Iamshortman.GridMod.Client.Forge.ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels =
{ "TheGrid" }, packetHandler = Iamshortman.GridMod.Common.Forge.CommonPacketHandler.class))
public class GridCraft
{
	@Instance(value = "GridCraft")
	public static GridCraft TronInstance;
	
	public static final String items = "/Tron/items.png";
	public static final String CodeRev = "/Tron/CodeRevisionTable.png";
	

	@SidedProxy(clientSide = "Iamshortman.GridMod.Client.Forge.ClientProxy", serverSide = "Iamshortman.GridMod.Common.Forge.CommonProxy")
	public static CommonProxy proxy;

	//items
	public static Item JetBaton;
	public static Item CycleBaton;

	//Blocks
	public static Block CodeRevTable;
	public static int CodeRevID;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		proxy.preInit(evt);
		proxy.customBlockRender();
		TronConfig.Load(evt);
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{	
		proxy.load(evt);
		proxy.RenderStuff();

		//items
		JetBaton = new ItemJetBaton(TronConfig.JetBatonID).setItemName("LightJetBaton");
		CycleBaton = new ItemCycleBaton(TronConfig.CycleBatonID).setItemName("LightCycleBaton");
		
		//blocks and tile-entities
		CodeRevTable = new BlockCodeRevTable(TronConfig.CodeRevTableID).setBlockName("CodeRevTable");
		GameRegistry.registerBlock(CodeRevTable,"CodeRevisisonTable");
		GameRegistry.registerTileEntity(TileEntityCodeRevTable.class, "TileEntityCodeRevTable");
		
		LanguageHandler.load();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		proxy.postInit(evt);

	}

}
