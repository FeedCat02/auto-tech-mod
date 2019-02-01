package com.food.autotech.util.handlers;

import com.food.autotech.init.containers.ContainerHotFurnace;
import com.food.autotech.init.gui.GuiHotFurnace;
import com.food.autotech.init.tileentity.TileEntityHotFurnace;
import com.food.autotech.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.GUI_HOT_FURNACE) return new ContainerHotFurnace(player.inventory, (TileEntityHotFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.GUI_HOT_FURNACE) return new GuiHotFurnace(player.inventory, (TileEntityHotFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

}
