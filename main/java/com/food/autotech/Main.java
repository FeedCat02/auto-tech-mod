package com.food.autotech;

import java.io.File;
import java.util.ArrayList;

import com.food.autotech.proxy.CommonProxy;
import com.food.autotech.util.Reference;
import com.food.autotech.util.compact.top.MainCompatHandler;
import com.food.autotech.util.handlers.ConfigHandler;
import com.food.autotech.util.handlers.CustomRecipeHandler;
import com.food.autotech.util.handlers.RegistryHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	@Instance
	public static Main instance;
	public static File config;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		//GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
		MainCompatHandler.registerTOP();
		ConfigHandler.registerConfig(event);
	}
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		RegistryHandler.initRegistries();
		
	}
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		Reference.heatSources=CustomRecipeHandler.addRecipes();
		System.out.println(Reference.heatSources);
	}



	
	
	
	
	
}
