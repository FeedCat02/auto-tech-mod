package com.food.autotech.init.containers.slots;

import com.food.autotech.init.tileentity.TileEntityHotFurnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHotFurnaceFuel extends Slot
{

	public SlotHotFurnaceFuel(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityHotFurnace.isItemFuel(stack);
	}
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}
}
