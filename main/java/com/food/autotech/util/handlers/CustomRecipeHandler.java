package com.food.autotech.util.handlers;

import java.util.ArrayList;

import net.minecraft.block.Block;

public class CustomRecipeHandler 
{
	public static ArrayList addRecipes()
	{
		ArrayList list = new ArrayList();
		for(int i = 0;i<ConfigHandler.HEAT_SOURCES.length;i++)
		{
			String s = ConfigHandler.HEAT_SOURCES[i];
			String[] split = s.split(";");
			try
			{
				ArrayList temp = new ArrayList();
				Block block = Block.getBlockFromName(split[0]);
				temp.add(block);
				Float change = Float.valueOf(split[1]);
				temp.add(change);
				if (split[2].equals("null"))
				{
					temp.add(0.0F);
					temp.add(false);				
				}
				else
				{
					Float max = Float.valueOf(split[2]);
					temp.add(max);
					temp.add(true);
				}
				if (split[3].equals("null"))
				{
					temp.add(0.0F);
					temp.add(false);				
				}
				else
				{
					Float max = Float.valueOf(split[3]);
					temp.add(max);
					temp.add(true);
				}
				Block block1 = Block.getBlockFromName(split[4]);
				temp.add(block1);
				Float chance = Float.valueOf(split[5]);
				temp.add(chance);
				list.add(temp);	
			}
			catch (Exception e) 
			{
			}
		}
		return list;	
	}
}
