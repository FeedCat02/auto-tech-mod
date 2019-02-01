package com.food.autotech.init.containers;

import org.apache.http.impl.client.ProxyClient;

import com.food.autotech.init.containers.slots.SlotHotFurnaceFuel;
import com.food.autotech.init.containers.slots.SlotHotFurnaceOutput;
import com.food.autotech.init.tileentity.TileEntityHotFurnace;
import com.food.autotech.proxy.ClientProxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerHotFurnace extends Container
{
	private final IInventory tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	
	public ContainerHotFurnace(InventoryPlayer player, IInventory tileentity)
	{
		this.tileentity = tileentity;
		this.addSlotToContainer(new Slot(tileentity, 0, 56, 17));
		this.addSlotToContainer(new SlotHotFurnaceFuel(tileentity, 1, 56, 53));
		this.addSlotToContainer(new SlotHotFurnaceOutput(player.player,tileentity, 2, 116, 35));
		
		for(int y = 0; y< 3; y++)
		{
			for(int x=0; x< 9; x++)
			{
				this.addSlotToContainer(new Slot(player,x+y*9+9,8+x*18,84+y*18));
			}
		}
		for(int x = 0; x<9; x++)
		{
			this.addSlotToContainer(new Slot(player,x,8+x*18,142));
		}
	}
	public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileentity);
    }

	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.cookTime != this.tileentity.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileentity.getField(2));
            }

            if (this.burnTime != this.tileentity.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileentity.getField(0));
            }

            if (this.currentBurnTime != this.tileentity.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileentity.getField(1));
            }

            if (this.totalCookTime != this.tileentity.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileentity.getField(3));
            }
        }

        this.cookTime = this.tileentity.getField(2);
        this.burnTime = this.tileentity.getField(0);
        this.currentBurnTime = this.tileentity.getField(1);
        this.totalCookTime = this.tileentity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileentity.setField(id, data);
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	    {
	        ItemStack itemstack = ItemStack.EMPTY;
	        Slot slot = (Slot)this.inventorySlots.get(index);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (index == 2)
	            {
	                if (!this.mergeItemStack(itemstack1, 3, 39, true))
	                {
	                    return ItemStack.EMPTY;
	                }

	                slot.onSlotChange(itemstack1, itemstack);
	            }
	            else if (index != 1 && index != 0)
	            {
	                if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
	                {
	                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
	                    {
	                        return ItemStack.EMPTY;
	                    }
	                }
	                else if (TileEntityHotFurnace.isItemFuel(itemstack1))
	                {
	                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
	                    {
	                        return ItemStack.EMPTY;
	                    }
	                }
	                else if (index >= 3 && index < 30)
	                {
	                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
	                    {
	                        return ItemStack.EMPTY;
	                    }
	                }
	                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
	                {
	                    return ItemStack.EMPTY;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
	            {
	                return ItemStack.EMPTY;
	            }

	            if (itemstack1.isEmpty())
	            {
	                slot.putStack(ItemStack.EMPTY);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.getCount() == itemstack.getCount())
	            {
	                return ItemStack.EMPTY;
	            }

	            slot.onTake(playerIn, itemstack1);
	        }

	        return itemstack;
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
