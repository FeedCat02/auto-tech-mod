package com.food.autotech.init.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CopperOre extends BlockBase{

	public CopperOre(String name, Material material) 
	{
		super(name, material);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 1);
		this.setHardness(3.0F);
		this.setResistance(15F);
	}

}
