package Iamshortman.GridMod.Client.Forge;

import org.lwjgl.input.Keyboard;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.ItemRender.ColoredEnchantmentItemRender;
import Iamshortman.GridMod.Client.ItemRender.IColoredEnchentmentEffect;
import Iamshortman.GridMod.Client.Model.ModelLaser;
import Iamshortman.GridMod.Client.Model.ModelLightJet;
import Iamshortman.GridMod.Client.Render.RenderCodeRevisionTable;
import Iamshortman.GridMod.Client.Render.RenderLaser;
import Iamshortman.GridMod.Client.Render.RenderLightCycle;
import Iamshortman.GridMod.Client.Render.RenderLightJet;
import Iamshortman.GridMod.Client.Render.RenderLightTrail;
import Iamshortman.GridMod.Client.RenderAPI.GridCraftRenderPlayer;
import Iamshortman.GridMod.Common.Entity.EntityLaser;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Entity.EntityLightTrail;
import Iamshortman.GridMod.Common.Forge.CommonProxy;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.src.RenderPlayerAPI;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;



public class ClientProxy extends CommonProxy
{
	public static KeyBinding Exitkey = new KeyBinding("Exit Vehicle", Keyboard.KEY_R);
	
	public void preInit(FMLPreInitializationEvent evt)
	{
		super.preInit(evt);
		RenderPlayerAPI.register("TheGrid", GridCraftRenderPlayer.class);
	}

	public void load(FMLInitializationEvent evt)
	{
		super.load(evt);
		KeyBinding[] list = {Exitkey};
		boolean[] repeat = { false };
		KeyBindingRegistry.registerKeyBinding(new GridKeyHandler(list, repeat));
	    MinecraftForgeClient.preloadTexture(GridCraft.items);
	    MinecraftForgeClient.preloadTexture(GridCraft.CodeRev);
	}

	public void postInit(FMLPostInitializationEvent evt)
	{
		super.postInit(evt);
		for(int I = 0; Item.itemsList.length > I; I++)
		{
			if(Item.itemsList[I]!= null && IColoredEnchentmentEffect.class.isAssignableFrom(Item.itemsList[I].getClass()))
			{
				System.out.println("Id: " + I);
				MinecraftForgeClient.registerItemRenderer(I, new ColoredEnchantmentItemRender());
			}
		}
		
		
	}
	
	
	public void customBlockRender()
	{
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCodeRevTable.class, new RenderCodeRevisionTable());
		GridCraft.CodeRevID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(GridCraft.CodeRevID, new RenderCodeRevisionTable());
	}
	
	public void RenderStuff()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());
		RenderingRegistry.registerEntityRenderingHandler(EntityLightJet.class, new RenderLightJet());
		RenderingRegistry.registerEntityRenderingHandler(EntityLightCycle.class, new RenderLightCycle());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityLightTrail.class, new RenderLightTrail());
	}
		
}
