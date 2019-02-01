package com.food.autotech.util.compact.top;

import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler 
{
	public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompat.register();
        }
    }
}
