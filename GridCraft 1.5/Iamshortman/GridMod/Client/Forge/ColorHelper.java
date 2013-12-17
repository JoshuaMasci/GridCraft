package Iamshortman.GridMod.Client.Forge;

public class ColorHelper
{
	public static float getRed(int Color)
	{
		float Red = (float)(Color >> 16 & 255) / 255.0F;
		return Red * 1.0F;
	}
	
	public static float getBlue(int Color)
	{
		float Blue = (float)(Color >> 8 & 255) / 255.0F;
		return Blue * 1.0F;
	}
	
	public static float getGreen(int Color)
	{
		float Green = (float)(Color & 255) / 255.0F;
		return Green * 1.0F;
	}
	
	public static int getColorFromRBG(int Red, int Blue, int Green)
	{
		int Color = ( Blue & 0xFF ) + (( Green & 0xff ) << 8) + (( Red & 0xFF ) << 16);
		return Color;
	}
}
