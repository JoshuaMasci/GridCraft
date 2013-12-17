package Iamshortman.GridMod.Client.Model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelTronArmorBasic extends ModelBiped
{
	
    public ModelTronArmorBasic()
    {
        this(0.0F);
    }

    public ModelTronArmorBasic(float par1)
    {
        super(par1, 0.0F, 64, 32);
        ModelRenderer backPlate = new ModelRenderer(this,0,0);
        backPlate.addBox(-2.5F, 2.0F, 1.0F, 5, 5, 3);
        this.bipedBody.addChild(backPlate);
    }
    
}
