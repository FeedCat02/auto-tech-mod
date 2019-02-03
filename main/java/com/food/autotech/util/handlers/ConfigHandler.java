package com.food.autotech.util.handlers;

import java.io.File;
import java.util.ArrayList;

import com.food.autotech.Main;
import com.food.autotech.util.Reference;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler 
{
	public static Configuration config;
	public static String[] HEAT_SOURCES = 
	{
	"minecraft:frosted_ice;-0.06;null;-10;minecraft:water;0.4",
	"minecraft:ice;-0.1;null;-20;minecraft:frosted_ice;0.1",
	"minecraft:packed_ice;-0.25;null;-50;minecraft:ice;0.05",
	"minecraft:water;-0.05;null;0;minecraft:water;0",
	"minecraft:flowing_water;-0.08;null;0;minecraft:flowing_water;0",
	"minecraft:flowing_lava;0.2;900;null;minecraft:cobblestone;0.1",
	"minecraft:lava;0.3;1400;null;minecraft:obsidian;0.02",
	"minecraft:fire;0.5;600;null;minecraft:air;0.2",
	"minecraft:torch;0.05;60;null;minecraft:torch;0"
	};
	public static String[] BIOME_HEAT_SOURCES = {};
	public static float MAX_HEAT_HOT_FURANCE = 800;
	public static int HEAT_CONSUMPTION_TIME_HOT_FURNACE = 1200;
	public static float FUEL_EFFICIENTCY_HOT_FURNACE = 1;
	public static int QUICKEST_COOK_TIME_HOT_FURNACE = 150;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		String category;
		category = "Heat";
		config.addCustomCategoryComment(category, "Global heat control");
		HEAT_SOURCES = config.getStringList("Heat Source List", category, HEAT_SOURCES, "Enter like this: Block_ID;Heat_Change(Any float);Max_Heat(Set to null for no max);Min_Heat(Set to null for no min);Block_ID(Of what you want it to be turned into);Chance_It_Changes(From 0-1)\n");
		category = "Heat Furnace";
		config.addCustomCategoryComment(category, "Setting for the heat furnace");
		MAX_HEAT_HOT_FURANCE = config.getFloat("Max Heat", category, MAX_HEAT_HOT_FURANCE, 0, 16000, "Heat when it melts");
		HEAT_CONSUMPTION_TIME_HOT_FURNACE = config.getInt("Heat Souce Consumption", category, HEAT_CONSUMPTION_TIME_HOT_FURNACE, 0, 16000, "Time before it tries to consume a heat source");
		FUEL_EFFICIENTCY_HOT_FURNACE = config.getFloat("Fuel Efficientcy", category, FUEL_EFFICIENTCY_HOT_FURNACE, 0, 16000, "How much more efficient the fuel is");
		QUICKEST_COOK_TIME_HOT_FURNACE = config.getInt("Cook Time", category, QUICKEST_COOK_TIME_HOT_FURNACE, 0, 16000, "The time it takes to smelt 1 item on max heat");
		
		config.save();
	}
	public static void registerConfig(FMLPreInitializationEvent event)
	{
		Main.config = new File(event.getModConfigurationDirectory().getAbsolutePath());
		Main.config.mkdirs();
		init(new File(Main.config.getPath(),Reference.MOD_ID + ".cfg"));
	}
}
