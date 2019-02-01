package com.food.autotech.util.compact.jei;



import javax.annotation.Nonnull;

import com.food.autotech.init.ModBlocks;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

@JEIPlugin
public class JeiPlugin implements IModPlugin
{
	public static final String HEATFURNACE_ID = "autotech.hotFurnace";
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		final IJeiHelpers helpers = registry.getJeiHelpers();
		final IGuiHelper gui = helpers.getGuiHelper();
	}
	@Override
	public void register(@Nonnull IModRegistry registry) 
	{
		registerHeatFurnaceHandling(registry);
	}
	private void registerHeatFurnaceHandling(@Nonnull IModRegistry registry)
	{
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.HOT_FURNACE), HEATFURNACE_ID, VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.HOT_FURNACE), HEATFURNACE_ID, VanillaRecipeCategoryUid.FUEL);
		
	}
}
