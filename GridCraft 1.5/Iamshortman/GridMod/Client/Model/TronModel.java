package Iamshortman.GridMod.Client.Model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;

public abstract class TronModel extends ModelBase
{
	float deltaTime;
	public long prevTime;
	public long entityPrevTime;

	public float rotationYaw;
	public float rotationYawSpeed;

	public float rotationPitch;
	public float rotationPitchSpeed;

	public float rotationRoll;
	public float rotationRollSpeed;

	int updateCount;
	public void update()
	{
		updateCount++;

		long time = System.nanoTime();

		deltaTime = ((float) (time - prevTime)) / 1000000000f; 
		// this happens when we haven't rendered in awhile (e.g. out of view)
		if (deltaTime > .03f)
			deltaTime = .03f; // default to ~30 fps

		if (entityPrevTime > prevTime)
		{
			// if the entity was updated more recently than the model
			// use adjusted delta (leave deltaTime alone so subclass models can
			// update
			// parts that may be independent of entity, e.g. helicopter rotor)
			float adjustedDeltaTime = ((float) (time - entityPrevTime)) / 1000000000f;
			rotationYaw += rotationYawSpeed * adjustedDeltaTime;
			rotationPitch += rotationPitchSpeed * adjustedDeltaTime;
			rotationRoll += rotationRollSpeed * adjustedDeltaTime;
		}
		else
		{
			rotationYaw += rotationYawSpeed * deltaTime;
			rotationPitch += rotationPitchSpeed * deltaTime;
			rotationRoll += rotationRollSpeed * deltaTime;
		}
		prevTime = time;
	}
}
