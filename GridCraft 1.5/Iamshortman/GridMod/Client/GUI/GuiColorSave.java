package Iamshortman.GridMod.Client.GUI;

import net.minecraft.client.gui.Gui;

public class GuiColorSave extends Gui
{
	public int Color;
	public String Name;
	
	public GuiColorSave(String name, int color)
	{
		super();
		this.Name = name;
		this.Color = color;
	}
	
	public int GetColor()
	{
		return this.Color;
	}
	
	public String GetName()
	{
		return this.Name;
	}
	
	
}
