package com.food.autotech.init.tileentity;

import java.util.ArrayList;

import com.food.autotech.init.blocks.HotFurnace;
import com.food.autotech.util.Reference;
import com.food.autotech.util.handlers.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityHotFurnace extends TileEntity implements ITickable, ISidedInventory
{
	private ItemStack smelting = ItemStack.EMPTY;
	private String customname;
	private int burnTime;
	private int totalBurnTime;
	private int cookTime;
	private int totalCookTime;
	private float totalHeat = 16.00F;
	private int timePlaced;
	private ItemStackHandler handler = new ItemStackHandler(3);
	private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {2};
    private static final int[] SLOTS_SIDES = new int[] {1};
    private NonNullList<ItemStack> itemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);

    
    private int getState()
    {
    	int heat = (int)(this.totalHeat/100);
    	if (heat < 0) heat = 0;
    	if (heat > 5) heat = 5;
		return heat;
    }
    	
    public int getSizeInventory()
    {
        return this.itemStacks.size();
    }
   
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.itemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }
    public static void registerFixesFurnace(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityFurnace.class, new String[] {"Items"}));
    }
    


    public ItemStack getStackInSlot(int index)
    {
        return this.itemStacks.get(index);
    }


    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.itemStacks, index, count);
    }


    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.itemStacks, index);
    }


    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = this.itemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.itemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.cookTime = 0;
            this.markDirty();
        }
    }


    public String getName()
    {
        return this.hasCustomName() ? this.customname : "container.hot_furnace";
    }

    public boolean hasCustomName()
    {
        return this.customname != null && !this.customname.isEmpty();
    }
	
    public static boolean openSight(BlockPos pos, World world)
    {
    	for(int i=pos.getY()+1;i<=256;i++)
    	{
    		if(world.getBlockState(new BlockPos(pos.getX(),i,pos.getZ())).getBlock()!= Blocks.AIR)
    		{
    			return false;
    		}
    	}
		return true;
    }
    
    public static float heatScale(World world, BlockPos pos, float currentHeat, boolean onMin, float randomTemp)
    {
    	float currentClimate = randomTemp-0.5F;
    	currentClimate*=10;
    	float totalHeat = 0.00F;
    	float biomeTemp = (16.00F*world.getBiome(pos).getTemperature(pos))+currentClimate;
    	float heatChangeBiome = 0.01F;
    	
    	if (currentHeat > biomeTemp)totalHeat-=heatChangeBiome;
    	if (currentHeat < biomeTemp)totalHeat+=heatChangeBiome;
    	Block u = world.getBlockState(pos.up()).getBlock();
    	Block d = world.getBlockState(pos.down()).getBlock();
    	Block n = world.getBlockState(pos.north()).getBlock();
    	Block e = world.getBlockState(pos.east()).getBlock();
    	Block s = world.getBlockState(pos.south()).getBlock();
    	Block w = world.getBlockState(pos.west()).getBlock();
    	for(int i = 0;i<Reference.heatSources.size();i++)
    	{
    		ArrayList list = (ArrayList) Reference.heatSources.get(i);
    		//UP
    		if (list.get(0)==u)
    		{
    			//0 = Block, 1 = Heat Gain, 2 = Max Heat, 3 = Does max matter, 4 = Min Heat, 5 = Does min matter, 6 = Destroyed into block, 7 = Chance it get destroyed
    			if(((((float)list.get(2)>currentHeat)||(!(boolean)list.get(3)))&&(((float)list.get(4)<currentHeat)||(!(boolean)list.get(5)))))
    			{
    				totalHeat+=(float)list.get(1);
    				
    			}		
    			else if(((float)list.get(1))<0&&currentHeat<(float)list.get(4)&&(boolean)list.get(5))totalHeat-=(float)list.get(1);
    			if (onMin)
				{
					double rand = Math.random();
					if (rand <= (float)list.get(7))world.setBlockState(pos.up(), ((Block) list.get(6)).getDefaultState());
				}
    		}
    		//DOWN
    		if (list.get(0)==d)
    		{
    			//0 = Block, 1 = Heat Gain, 2 = Max Heat, 3 = Does max matter, 4 = Min Heat, 5 = Does min matter, 6 = Destroyed into block, 7 = Chance it get destroyed
    			if(((((float)list.get(2)>currentHeat)||(!(boolean)list.get(3)))&&(((float)list.get(4)<currentHeat)||(!(boolean)list.get(5)))))
    			{
    				totalHeat+=(float)list.get(1);
    			}
    			else if(((float)list.get(1))<0&&currentHeat<(float)list.get(4)&&(boolean)list.get(5))totalHeat-=(float)list.get(1);
    			if (onMin)
				{
					double rand = Math.random();
					if (rand <= (float)list.get(7))world.setBlockState(pos.down(), ((Block) list.get(6)).getDefaultState());
				}
    		}
    		//NORTH
    		if (list.get(0)==n)
    		{
    			//0 = Block, 1 = Heat Gain, 2 = Max Heat, 3 = Does max matter, 4 = Min Heat, 5 = Does min matter, 6 = Destroyed into block, 7 = Chance it get destroyed
    			if(((((float)list.get(2)>currentHeat)||(!(boolean)list.get(3)))&&(((float)list.get(4)<currentHeat)||(!(boolean)list.get(5)))))
    			{
    				totalHeat+=(float)list.get(1);
    			}
    			else if(((float)list.get(1))<0&&currentHeat<(float)list.get(4)&&(boolean)list.get(5))totalHeat-=(float)list.get(1);
    			if (onMin)
				{
					double rand = Math.random();
					if (rand <= (float)list.get(7))world.setBlockState(pos.north(), ((Block) list.get(6)).getDefaultState());
				}
    		}
    		//EAST
    		if (list.get(0)==e)
    		{
    			//0 = Block, 1 = Heat Gain, 2 = Max Heat, 3 = Does max matter, 4 = Min Heat, 5 = Does min matter, 6 = Destroyed into block, 7 = Chance it get destroyed
    			if(((((float)list.get(2)>currentHeat)||(!(boolean)list.get(3)))&&(((float)list.get(4)<currentHeat)||(!(boolean)list.get(5)))))
    			{
    				totalHeat+=(float)list.get(1);
    			}
    			else if(((float)list.get(1))<0&&currentHeat<(float)list.get(4)&&(boolean)list.get(5))totalHeat-=(float)list.get(1);
    			if (onMin)
				{
					double rand = Math.random();
					if (rand <= (float)list.get(7))world.setBlockState(pos.east(), ((Block) list.get(6)).getDefaultState());
				}
    		}
    		//SOUTH
    		if (list.get(0)==s)
    		{
    			//0 = Block, 1 = Heat Gain, 2 = Max Heat, 3 = Does max matter, 4 = Min Heat, 5 = Does min matter, 6 = Destroyed into block, 7 = Chance it get destroyed
    			if(((((float)list.get(2)>currentHeat)||(!(boolean)list.get(3)))&&(((float)list.get(4)<currentHeat)||(!(boolean)list.get(5)))))
    			{
    				totalHeat+=(float)list.get(1);
    			}
    			else if(((float)list.get(1))<0&&currentHeat<(float)list.get(4)&&(boolean)list.get(5))totalHeat-=(float)list.get(1);
    			if (onMin)
				{
					double rand = Math.random();
					if (rand <= (float)list.get(7))world.setBlockState(pos.south(), ((Block) list.get(6)).getDefaultState());
				}
    		}
    		//WEST
    		if (list.get(0)==w)
    		{
    			//0 = Block, 1 = Heat Gain, 2 = Max Heat, 3 = Does max matter, 4 = Min Heat, 5 = Does min matter, 6 = Destroyed into block, 7 = Chance it get destroyed
    			if(((((float)list.get(2)>currentHeat)||(!(boolean)list.get(3)))&&(((float)list.get(4)<currentHeat)||(!(boolean)list.get(5)))))
    			{
    				totalHeat+=(float)list.get(1);
    			}
    			else if(((float)list.get(1))<0&&currentHeat<(float)list.get(4)&&(boolean)list.get(5))totalHeat-=(float)list.get(1);
    			if (onMin)
				{
					double rand = Math.random();
					if (rand <= (float)list.get(7))world.setBlockState(pos.west(), ((Block) list.get(6)).getDefaultState());
				}
    		}
    	}
    	if(world.isRaining()&&openSight(pos, world)&&world.getBiome(pos).canRain()) totalHeat -= 0.1;
    	return totalHeat;
    }
    
	public void update() 
	{    
		if (!world.isRemote){
		if (this.totalHeat > ConfigHandler.MAX_HEAT_HOT_FURANCE)
		{
		world.setBlockState(this.pos, Blocks.FLOWING_LAVA.getDefaultState());
		this.invalidate();
		}
		this.timePlaced++;
		if(Reference.randomHeat>1)Reference.randomHeat=1;
		if(Reference.randomHeat<0)Reference.randomHeat=0;
		if (this.timePlaced >= ConfigHandler.HEAT_CONSUMPTION_TIME_HOT_FURNACE)
		{
			double rand = Math.random();
			if (rand > 0.5D)
			{
				double rand1 = Math.random()/10;
				Reference.randomHeat+=rand1;
			}
			else if (rand < 0.5D)
			{
				double rand1 = Math.random()/10;
				Reference.randomHeat-=rand1;
			}
				
		}
		this.totalHeat += heatScale(world, this.pos, this.totalHeat,this.timePlaced>=1200,Reference.randomHeat);
		if (this.timePlaced >= 1200)this.timePlaced-=1200;
		this.totalCookTime = getCookTime(this.itemStacks.get(0));
		if(isBurning())
		{
			if(!this.tileEntityInvalid)HotFurnace.setState(true, this.world, this.pos, getState());
			if(canSmelt())
			{
				this.cookTime++;
				if (this.cookTime >= this.totalCookTime)
				{
					this.cookTime -= this.totalCookTime;
					smeltItem();
				}
			}
			this.burnTime--;
		}
		else
		{
			if(canSmelt()&&isItemFuel(this.itemStacks.get(1)))
			{
				this.totalBurnTime = getItemBurnTime(this.itemStacks.get(1));
				this.burnTime = getItemBurnTime(this.itemStacks.get(1));
				ItemStack itemstack = this.itemStacks.get(1);
				if (!itemstack.isEmpty())
                {
                    Item item = itemstack.getItem();
                    itemstack.shrink(1);

                    if (itemstack.isEmpty())
                    {
                        ItemStack item1 = item.getContainerItem(itemstack);
                        this.itemStacks.set(1, item1);
                    }
                }
			}
			else
			{
				this.cookTime = 0;
				if(!this.tileEntityInvalid)HotFurnace.setState(false, this.world, this.pos, getState());
			}
		}
		}
		}
		



	
	public void setCustomname(String customname) {
		this.customname = customname;
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.customname) : new TextComponentTranslation("container.hot_furnace");
		
	}
	
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.itemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.itemStacks);
        this.burnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.totalHeat = compound.getFloat("HeatAmount");
        

        if (compound.hasKey("CustomName", 8))
        {
            this.customname = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.burnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        compound.setFloat("HeatAmount", (float)this.totalHeat);
        ItemStackHelper.saveAllItems(compound, this.itemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customname);
        }

        return compound;
    }
    public int getInventoryStackLimit()
    {
        return 64;
    }

	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	@SideOnly(Side.CLIENT)
	public static boolean isBurning (IInventory inventory) 
	{
		return inventory.getField(0) > 0;
	}
	
	public int getCookTime(ItemStack input1) 
	{
		if (this.totalHeat < 100) return 16000;
		else return (ConfigHandler.QUICKEST_COOK_TIME_HOT_FURNACE*5/getState());
	}
	private boolean canSmelt()
	{
		if (this.totalHeat<100) return false;
		if (((ItemStack)this.itemStacks.get(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.itemStacks.get(0));

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.itemStacks.get(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        }
    }
	public static int getItemBurnTime(ItemStack fuel)
	{
		if (fuel.isEmpty())
        {
            return 0;
        }
        else
        {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(fuel);
            if (burnTime >= 0) return burnTime;
            else {
            	Item item = fuel.getItem();
            	if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
            	{
            		burnTime = 113;
            	}
            	else if (item == Item.getItemFromBlock(Blocks.WOOL))
            	{
            		burnTime = 75;
            	}
            	else if (item == Item.getItemFromBlock(Blocks.CARPET))
            	{
            		burnTime = 51;
            	}
            	else if (item == Item.getItemFromBlock(Blocks.LADDER))
            	{
            		burnTime = 225;
            	}
            	else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
            	{
            		burnTime = 75;
            	}
            	else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
            	{
            		burnTime = 225;
            	}
            	else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
            	{
            		burnTime = 12000;
            	}
            	else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
            	{
            		burnTime = 150;
            	}	
            	else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
            	{
            		burnTime = 150;
            	}
            	else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
            	{
            		burnTime = 150;
            	}
            	else if (item == Items.STICK)
            	{
            		burnTime = 75;
            	}
            	else if (item != Items.BOW && item != Items.FISHING_ROD)
            	{
                	if (item == Items.SIGN)
                	{
                		burnTime = 150;
                	}
                	else if (item == Items.COAL)
                	{
                		burnTime = 1200;
                	}
                	else if (item == Items.LAVA_BUCKET)
                	{
                		burnTime = 15000;
                	}
                	else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL)
                	{
                    	if (item == Items.BLAZE_ROD)
                    	{
                    		burnTime = 1800;
                    	}
                    	else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
                    	{
                    		burnTime = 150;
                    	}
                    	else
                    	{
                    		burnTime = item instanceof ItemBoat ? 300 : 0;
                    	}
                	}
                	else
                	{
                		burnTime = 75;
                	}
            	}
            	else
            	{
            		burnTime = 225;
            	}
            }
            return (int)(MathHelper.roundUp((int) (burnTime*10*ConfigHandler.FUEL_EFFICIENTCY_HOT_FURNACE), 10)/10);
        }		
	}
	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemBurnTime(fuel) > 0;
	}
	
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 2)
        {
            return false;
        }
        else if (index != 1)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.itemStacks.get(1);
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return SLOTS_BOTTOM;
        }
        else
        {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }


    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public String getGuiID()
    {
        return "autotech:hot_furnace";
    }
    
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.burnTime;
            case 1:
                return this.totalBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.totalBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    public int getFieldCount()
    {
        return 4;
    }
    public void clear()
    {
        this.itemStacks.clear();
    }
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = this.itemStacks.get(0);
            ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
            ItemStack itemstack2 = this.itemStacks.get(2);

            if (itemstack2.isEmpty())
            {
                this.itemStacks.set(2, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }
            itemstack.shrink(1);
        }
    }
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);


    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (index == 2) return true;
		return false;
	}
	public int getHeat()
	{
		return Math.round(this.totalHeat);
	}

	
	
	
	
	
	
	
	
	
	
	
	
}


