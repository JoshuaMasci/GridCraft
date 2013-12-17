package Iamshortman.GridMod.Client.Model;

import Iamshortman.SugerRush.Client.TMT.ModelRendererTurbo;

public class ModelLightTank extends TronModel
{

	private static final int textureX = 512;
	private static final int textureY = 512;
	
	public float rotationYawTurret;
	public float rotationPitchTurret;
	public float rotationYawTurretSpeed;
	public float rotationPitchTurretSpeed;
	
	public ModelRendererTurbo MainBody;
	public ModelRendererTurbo TurretMount;
	public ModelRendererTurbo LeftTread;
	public ModelRendererTurbo LeftTread1;
	public ModelRendererTurbo LeftTread2;
	
	public ModelRendererTurbo RightTread;
	public ModelRendererTurbo RightTread1;
	public ModelRendererTurbo RightTread2;
	
	public ModelRendererTurbo TurretBase0;
	public ModelRendererTurbo TurretBase1;
	public ModelRendererTurbo Cannon0;
	public ModelRendererTurbo Cannon1;
	public ModelRendererTurbo Cannon2;
	public ModelRendererTurbo Cannon3;
	public ModelRendererTurbo Cannon4;
	
	public ModelLightTank()
	{
		this.createBase();
		this.createTurret();
	}
	public void createBase()
	{
		LeftTread = new ModelRendererTurbo(this, 0, 89, textureX, textureY);
		LeftTread.addTrapezoid(56 - 24, -12, -62, 24, 12, 124, 0.0F, -4.0F, LeftTread.MR_BOTTOM);
		
		LeftTread1 = new ModelRendererTurbo(this, 0, 226, textureX, textureY);
		LeftTread1.addBox(56 - 24, -22, -62, 24, 10, 124);
		
		LeftTread2 = new ModelRendererTurbo(this, 0, 363, textureX, textureY);
		LeftTread2.addTrapezoid(56 - 24, -28, -62, 24, 6, 124, 0.0F, -4.0F, LeftTread1.MR_TOP);
		
		RightTread = new ModelRendererTurbo(this, 210, 71, textureX, textureY);
		RightTread.addTrapezoid(-56, -12, -62, 24, 12, 124, 0.0F, -4.0F, RightTread.MR_BOTTOM);
		
		RightTread1 = new ModelRendererTurbo(this, 210, 208, textureX, textureY);
		RightTread1.addBox(-56, -22, -62, 24, 10, 124);
		
		RightTread2 = new ModelRendererTurbo(this, 210, 345, textureX, textureY);
		RightTread2.addTrapezoid(-56, -28, -62, 24, 6, 124, 0.0F, -4.0F, LeftTread1.MR_TOP);
		
		MainBody = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		MainBody.addBox(-42, -25, -30, 84, 8, 80);
		
		TurretMount = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		TurretMount.addBox(-30, -29, 20 -15, 20, 4, 20);
	}
	
	public void renderBase(float scale)
	{
		LeftTread.render(scale);
		LeftTread1.render(scale);
		LeftTread2.render(scale);
		
		RightTread.render(scale);
		RightTread1.render(scale);
		RightTread2.render(scale);
		
		//MainBody.render(scale);
	}
	
	public void createTurret()
	{	
		this.TurretBase0 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		this.TurretBase0.setFlipped(true);
		this.TurretBase0.addCylinder(0, 0, 0, 20, 10, 15);
		this.createCannon0();
		this.createCannon1();
		this.createCannon2();
		this.createTurretBase1();
	}
	
	public void createCannon0()
	{
		float point0[] = {4F, 0F, 0F};
		float point1[] = {4F, 0F, 100F};
		float point2[] = {16F, 0F, 100F};
		float point3[] = {16F, 0F, 0F};
		float point4[] = {0F, 8F, 0F};
		float point5[] = {0F, 8F, 100F};
		float point6[] = {20F, 8F, 100F};
		float point7[] = {20F, 8F, 0F};
		Cannon0 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		Cannon0.addRectShape(point0, point1, point2, point3, point4, point5, point6, point7, 20, 8, 100);
		Cannon0.rotateAngleY = 180 * 0.01745329F;
		Cannon0.setRotationPoint(45F, -5, 0);
	}
	public void createCannon1()
	{
		float point0[] = {0F, 0F, 5F};
		float point1[] = {0F, 0F, 30F};
		float point2[] = {20F, 0F, 30F};
		float point3[] = {20F, 0F, 5F};
		float point4[] = {0F, 10F, 0F};
		float point5[] = {0F, 10F, 30F};
		float point6[] = {20F, 10F, 30F};
		float point7[] = {20F, 10F, 0F};
		Cannon1 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		Cannon1.addRectShape(point0, point1, point2, point3, point4, point5, point6, point7, 20, 10, 30);
		Cannon1.setPosition(25F, -6F, -15F);
	}
	
	public void createCannon2()
	{
		float point0[] = {4F, 0F, 0F};
		float point1[] = {4F, 0F, 40F};
		float point2[] = {16F, 0F, 40F};
		float point3[] = {16F, 0F, 0F};
		float point4[] = {0F, 8F, 0F};
		float point5[] = {0F, 8F, 40F};
		float point6[] = {20F, 8F, 40F};
		float point7[] = {20F, 8F, 0F};
		Cannon2 = new ModelRendererTurbo(this, 0, 0, textureX, textureY);
		Cannon2.addRectShape(point0, point1, point2, point3, point4, point5, point6, point7, 20, 8, 40);
		Cannon2.setRotationPoint(25F, -5, 15);
	}
	
	public void createTurretBase1()
	{
		float point0[] = {0F, 3F, 6F};
		float point1[] = {0F, 3F, 14F};
		float point2[] = {20F, 0F, 20F};
		float point3[] = {20F, 0F, 0F};
		float point4[] = {0F, 8F, 6F};
		float point5[] = {0F, 8F, 14F};
		float point6[] = {20F, 8F, 20F};
		float point7[] = {20F, 8F, 0F};
		TurretBase1 = new ModelRendererTurbo(this, 0, 52, textureX, textureY);
		float test[][] = new float[8][3];
		test[0] = point0;
		test[1] = point1;
		test[2] = point2;
		test[3] = point3;
		test[4] = point4;
		test[5] = point5;
		test[6] = point6;
		test[7] = point7;
		
		TurretBase1.addRectShape(test[0], test[1], test[2], test[3], test[4], test[5], test[6], test[7], 20, 8, 20);
		
		
		TurretBase1.setPosition(10F, -5.2F, -10F);
	}
	
	public void renderTurret(float scale)
	{
		TurretBase0.render(scale);
		TurretBase1.render(scale);
		//Cannon0.rotateAngleX = this.rotationPitchTurret * -0.01745329F ;
		//Cannon0.render(scale);
		//Cannon1.render(scale);
		//Cannon2.render(scale);
	}
	
	@Override
	public void update()
	{
		long time = System.nanoTime();

		if (entityPrevTime > prevTime)
		{
			// if the entity was updated more recently than the model
			// use adjusted delta (leave deltaTime alone so subclass models can
			// update
			// parts that may be independent of entity, e.g. helicopter rotor)
			float adjustedDeltaTime = ((float) (time - entityPrevTime)) / 1000000000f;
			rotationYawTurret += rotationYawTurretSpeed * adjustedDeltaTime;
			rotationPitchTurret += rotationPitchTurretSpeed * adjustedDeltaTime;
		}
		else
		{
			rotationYawTurret += rotationYawTurretSpeed * deltaTime;
			rotationPitchTurret += rotationPitchTurretSpeed * deltaTime;
		}
		
		super.update();
	}
	public float getTurretRotationYawOffset()
	{
		return this.rotationYawTurret - this.rotationYaw;
	}
}
