package Iamshortman.GridMod.Client.Forge;

import org.lwjgl.input.Keyboard;

import Iamshortman.GridMod.GridCraft;
import Iamshortman.GridMod.Client.ItemRender.ColoredTrontemRender;
import Iamshortman.GridMod.Client.ItemRender.IColoredEnchentmentEffect;
//import Iamshortman.GridMod.Client.Menu.MenuBaseGrid;
import Iamshortman.GridMod.Client.Model.ModelLaser;
import Iamshortman.GridMod.Client.Model.ModelLightJet;
import Iamshortman.GridMod.Client.Render.RenderCodeRevisionTable;
import Iamshortman.GridMod.Client.Render.RenderLaser;
import Iamshortman.GridMod.Client.Render.RenderLightCycle;
import Iamshortman.GridMod.Client.Render.RenderLightJet;
import Iamshortman.GridMod.Client.Render.RenderLightTank;
import Iamshortman.GridMod.Client.Render.RenderLightTrail;
import Iamshortman.GridMod.Client.Render.RenderPlayerWrapper;
import Iamshortman.GridMod.Common.Entity.EntityLaser;
import Iamshortman.GridMod.Common.Entity.EntityLightCycle;
import Iamshortman.GridMod.Common.Entity.EntityLightJet;
import Iamshortman.GridMod.Common.Entity.EntityLightTank;
import Iamshortman.GridMod.Common.Entity.EntityLightTrail;
import Iamshortman.GridMod.Common.Forge.GridCommonProxy;
import Iamshortman.GridMod.Common.TileEntity.TileEntityCodeRevTable;
//import net.aetherteam.mainmenu_api.MainMenuAPI;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

public class GridClientProxy extends GridCommonProxy
{
	public static KeyBinding Exitkey = new KeyBinding("Exit Vehicle", Keyboard.KEY_R);

	public void preInit(FMLPreInitializationEvent evt)
	{
		super.preInit(evt);
		//MainMenuAPI.registerMenu("GridCraft", MenuBaseGrid.class);
	}

	public void load(FMLInitializationEvent evt)
	{
		super.load(evt);
		KeyBinding[] list =
		{
			Exitkey
		};
		boolean[] repeat =
		{
			false
		};
		KeyBindingRegistry.registerKeyBinding(new GridKeyHandler(list, repeat));
		TickRegistry.registerTickHandler(new ClientTick(), Side.CLIENT);
	}

	public void postInit(FMLPostInitializationEvent evt)
	{
		super.postInit(evt);
		for (int I = 0; Item.itemsList.length > I; I++)
		{
			if (Item.itemsList[I] != null && IColoredEnchentmentEffect.class.isAssignableFrom(Item.itemsList[I].getClass()))
			{
				System.out.println("Id: " + I);
				//MinecraftForgeClient.registerItemRenderer(I, new ColoredTrontemRender());
			}
		}
		
		if(RenderManager.instance.getEntityClassRenderObject(EntityPlayer.class) != null)
		{
			RenderManager.instance.entityRenderMap.put(EntityPlayer.class, new RenderPlayerWrapper((RenderPlayer) RenderManager.instance.getEntityClassRenderObject(EntityPlayer.class)));
		}
		else
		{
			RenderManager.instance.entityRenderMap.put(EntityPlayer.class, new RenderPlayerWrapper(new RenderPlayer()));
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
		RenderingRegistry.registerEntityRenderingHandler(EntityLightTank.class, new RenderLightTank());
	}

}
