package com.food.autotech.util;


import java.util.ArrayList;

import net.minecraft.init.Blocks;

public class Reference 
{
	public static final String MOD_ID = "autotech";
	public static final String NAME = "AutoTech";
	public static final String VERSION = "1.0.3 BETA";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String CLIENT_PROXY_CLASS = "com.food.autotech.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.food.autotech.proxy.CommonProxy";
	public static final int GUI_HOT_FURNACE = 0;
	public static ArrayList heatSources = new ArrayList();
	public static float randomHeat = (float)Math.random();
}
