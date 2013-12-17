package Iamshortman.GridMod;

import org.lwjgl.input.Keyboard;

import Iamshortman.GridMod.Client.Forge.GridKeyHandler;
import Iamshortman.GridMod.Client.GUI.GridGuiHandler;
import Iamshortman.GridMod.Common.Block.BlockCodeRevTable;
import Iamshortman.GridMod.Common.Block.BlockColorChangingTable;
import Iamshortman.GridMod.Common.Block.BlockGridGrass;
import Iamshortman.GridMod.Common.Block.BlockGridStone;
import Iamshortman.GridMod.Common.Block.BlockGridTorch;
import Iamshortman.GridMod.Common.Block.BlockGridWater;
import Iamshortman.GridMod.Common.Block.BlockGridWaterFlowing;
import Iamshortman.GridMod.Common.Block.BlockPortalTheGrid;
import Iamshortman.GridMod.Common.Block.Material.MaterialGridRock;
import Iamshortman.GridMod.Common.Forge.GridCommonProxy;
import Iamshortman.GridMod.Common.Forge.Event.GridPlayerWatcher;
import Iamshortman.GridMod.Common.Handlers.LanguageHandler;
import Iamshortman.GridMod.Common.Handlers.TronConfig;
import Iamshortman.GridMod.Common.Item.ItemCycleBaton;
import Iamshortman.GridMod.Common.Item.ItemJetBaton;
import Iamshortman.GridMod.Common.Item.ItemTankPlacer;
import Iamshortman.GridMod.Common.Item.ItemTronArmor;
import Iamshortman.GridMod.Common.TheGrid.WorldProviderGrid;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.Mod.Instance;

@Mod(modid = "GridCraft", name = "GridCraft", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
clientPacketHandlerSpec = @SidedPacketHandler(channels =
{
	"TheGrid"
}, packetHandler = Iamshortman.GridMod.Client.Forge.ClientPacketHandler.class), 
serverPacketHandlerSpec = @SidedPacketHandler(channels =
{
	"TheGrid"
}, packetHandler = Iamshortman.GridMod.Common.Network.CommonPacketHandler.class))
public class GridCraft
{
	@Instance(value = "GridCraft")
	public static GridCraft TronInstance;

	public static final String CodeRev = "/mods/GridCraft/textures/blocks/CodeRevisionTable.png";

	@SidedProxy(clientSide = "Iamshortman.GridMod.Client.Forge.GridClientProxy", serverSide = "Iamshortman.GridMod.Common.Forge.GridCommonProxy")
	public static GridCommonProxy Tronproxy;

    public static final Material Gridrock = (new MaterialGridRock(MapColor.stoneColor));
    
	// items
	public static Item jetBaton;
	public static Item cycleBaton;
	public static Item tankPlacer;
	
	// Blocks
	public static Block codeRevTable;
	public static int CodeRevID;
	public static Block colorChangingTable;
	public static Block portal;
	public static Block gridRock;
	public static Block gridTorch;
	public static Block gridGrass;
	public static Block gridWater;
	public static Block gridWaterFlowing;
	
	public static int dimension = 45;

	
	public GridGuiHandler guiHandler = new GridGuiHandler();
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		Tronproxy.preInit(evt);
		Tronproxy.customBlockRender();
		TronConfig.Load(evt);
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{
		Tronproxy.load(evt);
		Tronproxy.RenderStuff();

		//register GUI Handler
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
		
		// items
		jetBaton = new ItemJetBaton(TronConfig.jetBatonID).setUnlocalizedName("LightJetBaton");
		cycleBaton = new ItemCycleBaton(TronConfig.cycleBatonID).setUnlocalizedName("LightCycleBaton");
		tankPlacer = new ItemTankPlacer(500).setUnlocalizedName("LightTankPlacer").setCreativeTab(CreativeTabs.tabTransport);
		
		// blocks and tile-entities
		codeRevTable = new BlockCodeRevTable(TronConfig.codeRevTableID).setUnlocalizedName("CodeRevTable").setHardness(1.0F).setResistance(5.0F);
		portal = new BlockPortalTheGrid(TronConfig.portalID).setUnlocalizedName("portal");
		colorChangingTable = new BlockColorChangingTable(TronConfig.colorChangeTableID).setUnlocalizedName("ColorChangingTable").setHardness(1.0F).setResistance(5.0F);
		gridRock = new BlockGridStone(TronConfig.gridRockID).setUnlocalizedName("GridStone").setCreativeTab(CreativeTabs.tabBlock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep);
		gridTorch = new BlockGridTorch(TronConfig.gridTorchID).setHardness(0.0F).setLightValue(0.9375F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("gridTorch");
		gridGrass = new BlockGridGrass(TronConfig.gridGrassID, Gridrock);
	    gridWater = new BlockGridWaterFlowing(TronConfig.gridWaterID - 1, Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("GridWater").setCreativeTab(CreativeTabs.tabBlock).setLightValue(1F);
	    gridWaterFlowing = new BlockGridWater(TronConfig.gridWaterID, Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("GridWaterFlowing").setCreativeTab(CreativeTabs.tabBlock).setLightValue(1F);
		
		
		
		//Item armor = new ItemTronArmor(3004, 1).setUnlocalizedName("TronArmor");
		
		GameRegistry.registerBlock(codeRevTable, "CodeRevisisonTable");
		GameRegistry.registerTileEntity(TileEntityCodeRevTable.class, "TileEntityCodeRevTable");
		GameRegistry.registerBlock(colorChangingTable, "ColorChangingTable");
		
		GameRegistry.registerBlock(portal, "Portal");
		GameRegistry.registerBlock(gridRock, "GridStone");
		GameRegistry.registerBlock(gridTorch, "Grid Torch");
		GameRegistry.registerBlock(gridGrass, "Grid Grass");
		GameRegistry.registerBlock(gridWater, "Grid Water");
		GameRegistry.registerBlock(gridWaterFlowing, "Grid Water Flowing");
		
		//Dimension 
		//DimensionManager.registerProviderType(dimension, WorldProviderGrid.class, false);
		//DimensionManager.registerDimension(dimension, dimension);
		
		LanguageHandler.load();
		
		//Player Handler Stuff
		GameRegistry.registerPlayerTracker(new GridPlayerWatcher());
		
		//MinecraftForge.EVENT_BUS.register(new GridEventContainer());
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		Tronproxy.postInit(evt);
	}

	@ServerStarted
	public void onServerStart(FMLServerStartedEvent evt)
	{

	}
	
}
