package Iamshortman.GridMod.Client.Render;

import org.lwjgl.opengl.GL11;

import Iamshortman.GridMod.Client.Model.ModelPlayerBase;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightCycle;
import Iamshortman.GridMod.Client.Model.ModelPlayerLightJet;
import Iamshortman.GridMod.Common.Entity.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderPlayerLightVehicle extends Render
{

	private ModelPlayerBase mainModel = new ModelPlayerLightJet();
	private ModelPlayerBase renderPassModel = new ModelPlayerLightJet();
	private ModelPlayerBase modelArmor = new ModelPlayerLightJet(0.5F);
	private ModelPlayerBase modelArmorChestplate = new ModelPlayerLightJet(1.0F);
	
	public RenderPlayerLightVehicle(Entity Vehicle)
	{
		if(Vehicle instanceof EntityLightJet)
		{
			mainModel = new ModelPlayerLightJet();
			renderPassModel = new ModelPlayerLightJet();
			modelArmor = new ModelPlayerLightJet(0.5F);
			modelArmorChestplate = new ModelPlayerLightJet(1.0F);
		}
		else if(Vehicle instanceof EntityLightCycle)
		{
			mainModel = new ModelPlayerLightCycle();
			renderPassModel = new ModelPlayerLightCycle();
			modelArmor = new ModelPlayerLightCycle(0.5F);
			modelArmorChestplate = new ModelPlayerLightCycle(1.0F);
		}
	}
	
	public void doRender(Entity var1, float par9)
	{
        float var19;
        int var18;
        float var20;
        float var22;

        this.mainModel.render((EntityPlayer) var1);
        
        for (int var17 = 0; var17 < 4; ++var17)
        {
            var18 = this.shouldRenderPass((EntityLiving) var1, var17);

            if (var18 > 0)
            {
                this.renderPassModel.render((EntityPlayer) var1);

                if ((var18 & 240) == 16)
                {
                    this.func_82408_c((EntityLiving) var1, var17);
                    this.renderPassModel.render((EntityPlayer) var1);
                }

                if ((var18 & 15) == 15)
                {
                    var19 = (float)var1.ticksExisted + par9;
                    this.loadTexture("%blur%/misc/glint.png");
                    GL11.glEnable(GL11.GL_BLEND);
                    var20 = 0.5F;
                    GL11.glColor4f(var20, var20, var20, 1.0F);
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDepthMask(false);

                    for (int var21 = 0; var21 < 2; ++var21)
                    {
                        GL11.glDisable(GL11.GL_LIGHTING);
                        var22 = 0.76F;
                        GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
                        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glLoadIdentity();
                        float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
                        float var24 = 0.33333334F;
                        GL11.glScalef(var24, var24, var24);
                        GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, var23, 0.0F);
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        this.renderPassModel.render((EntityPlayer) var1);
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }

                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
            }
        }

		
	}
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2)
    {
        ItemStack var4 = par1EntityLiving.getCurrentArmor(3 - par2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture(ForgeHooksClient.getArmorTexture(var4, "/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png"));
                ModelPlayerBase var7 = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
                var7.Head.showModel = par2 == 0;
                var7.HeadGear.showModel = par2 == 0;
                var7.Body.showModel = par2 == 1 || par2 == 2;
                var7.ArmRight.showModel = par2 == 1;
                var7.ArmLeft.showModel = par2 == 1;
                var7.LegRight.showModel = par2 == 2 || par2 == 3;
                var7.LegLeft.showModel = par2 == 2 || par2 == 3;
                this.setRenderPassModel(var7);
                
                float var8 = 1.0F;

                if (var6.getArmorMaterial() == EnumArmorMaterial.CLOTH)
                {
                    int var9 = var6.getColor(var4);
                    float var10 = (float)(var9 >> 16 & 255) / 255.0F;
                    float var11 = (float)(var9 >> 8 & 255) / 255.0F;
                    float var12 = (float)(var9 & 255) / 255.0F;
                    GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);

                    if (var4.isItemEnchanted())
                    {
                        return 31;
                    }

                    return 16;
                }

                GL11.glColor3f(var8, var8, var8);

                if (var4.isItemEnchanted())
                {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }
    
    @Override
    protected void loadTexture(String par1Str)
    {
        RenderEngine var2 = Minecraft.getMinecraft().renderEngine;
        var2.bindTexture(var2.getTexture(par1Str));
    }
    
    protected void func_82408_c(EntityLiving par1EntityLiving, int par2)
    {
        ItemStack var4 = par1EntityLiving.getCurrentArmor(3 - par2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture("/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + "_b.png");
                float var7 = 1.0F;
                GL11.glColor3f(var7, var7, var7);
            }
        }
    }
    public static String[] bipedArmorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};

	@Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		EntityPlayer Player;
        if(var1 instanceof EntityPlayer)
        {
        	Player = (EntityPlayer) var1;
        	this.doRender(Player, var9);
        	
        }	
	}
	
    public void setRenderPassModel(ModelPlayerBase par1ModelBase)
    {
        this.renderPassModel = par1ModelBase;
    }
}
