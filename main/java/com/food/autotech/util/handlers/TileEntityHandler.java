package com.food.autotech.util.handlers;

import com.food.autotech.init.tileentity.TileEntityHotFurnace;
import com.food.autotech.util.Reference;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
	public static void registerTileEntities()
	{
		TileEntity.register("hot_furnace", TileEntityHotFurnace.class);
	}
}
