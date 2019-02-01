package com.food.autotech.init;

import java.util.ArrayList;
import java.util.List;


import com.food.autotech.init.blocks.CopperOre;
import com.food.autotech.init.blocks.HotFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block COPPER_ORE = new CopperOre("copper_ore",Material.ROCK);
	public static final Block HOT_FURNACE = new HotFurnace("hot_furnace",Material.ROCK);
}
