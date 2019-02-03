package com.food.autotech.init.blocks;



import java.util.Random;

import com.food.autotech.Main;
import com.food.autotech.init.ModBlocks;
import com.food.autotech.init.tileentity.TileEntityHotFurnace;
import com.food.autotech.util.Reference;
import com.food.autotech.util.compact.top.TOPInfoProvider;
import com.google.common.util.concurrent.Service.State;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class HotFurnace extends BlockBase implements ITileEntityProvider, TOPInfoProvider
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	public static final PropertyInteger HOT = PropertyInteger.create("hot", 0, 5);
	private TileEntityHotFurnace tileentity;
	
	public HotFurnace(String name, Material material) {
		super(name, material);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(1.5F);
		this.setResistance(45F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false).withProperty(HOT, 0));
		
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.HOT_FURNACE);
		
	}
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ModBlocks.HOT_FURNACE);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
		{
			playerIn.openGui(Main.instance, Reference.GUI_HOT_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if(!worldIn.isRemote)
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState east = worldIn.getBlockState(pos.east());
			IBlockState west = worldIn.getBlockState(pos.west());
			EnumFacing face = (EnumFacing)state.getValue(FACING);
			
			if(face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			else if(face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			else if(face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			else if(face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos, int hot)
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(active) worldIn.setBlockState(pos, ModBlocks.HOT_FURNACE.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true).withProperty(HOT, hot),3);
		else worldIn.setBlockState(pos, ModBlocks.HOT_FURNACE.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false).withProperty(HOT, hot),3);
		if(tileentity != null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,ItemStack stack) {
		
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityHotFurnace tileentity = (TileEntityHotFurnace)worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
	}
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {BURNING,FACING,HOT});
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
		return this.getDefaultState().withProperty(FACING, facing);
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityHotFurnace();
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) 
	{
		TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityHotFurnace) {
        	TileEntityHotFurnace dataTileEntity = (TileEntityHotFurnace) te; 
            probeInfo.horizontal()
                    .progress(dataTileEntity.getHeat(), 800, probeInfo.defaultProgressStyle().filledColor(0xffb80000).alternateFilledColor(0xffb80000).suffix("C"));
        }
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
