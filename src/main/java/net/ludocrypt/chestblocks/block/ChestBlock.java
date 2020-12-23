package net.ludocrypt.chestblocks.block;

import net.ludocrypt.chestblocks.block.entity.ChestBlockBuilderEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChestBlock extends Block implements Waterloggable, BlockEntityProvider {

	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public ChestBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape Center = createOffsetCuboidShape(1, 1, 1, 15, 15, 15);

		boolean Up = world.getBlockState(pos.up()).getBlock() == this;
		boolean Down = world.getBlockState(pos.down()).getBlock() == this;
		boolean North = world.getBlockState(pos.north()).getBlock() == this;
		boolean East = world.getBlockState(pos.east()).getBlock() == this;
		boolean South = world.getBlockState(pos.south()).getBlock() == this;
		boolean West = world.getBlockState(pos.west()).getBlock() == this;

		boolean NorthEast = world.getBlockState(pos.north().east()).getBlock() == this && North && East;
		boolean SouthEast = world.getBlockState(pos.south().east()).getBlock() == this && South && East;
		boolean NorthWest = world.getBlockState(pos.north().west()).getBlock() == this && North && West;
		boolean SouthWest = world.getBlockState(pos.south().west()).getBlock() == this && South && West;

		boolean NorthUp = world.getBlockState(pos.north().up()).getBlock() == this && North && Up;
		boolean EastUp = world.getBlockState(pos.east().up()).getBlock() == this && East && Up;
		boolean SouthUp = world.getBlockState(pos.south().up()).getBlock() == this && South && Up;
		boolean WestUp = world.getBlockState(pos.west().up()).getBlock() == this && West && Up;

		boolean NorthDown = world.getBlockState(pos.north().down()).getBlock() == this && North && Down;
		boolean EastDown = world.getBlockState(pos.east().down()).getBlock() == this && East && Down;
		boolean SouthDown = world.getBlockState(pos.south().down()).getBlock() == this && South && Down;
		boolean WestDown = world.getBlockState(pos.west().down()).getBlock() == this && West && Down;

		boolean NorthEastUp = world.getBlockState(pos.north().east().up()).getBlock() == this && NorthEast && Up;
		boolean SouthEastUp = world.getBlockState(pos.south().east().up()).getBlock() == this && SouthEast && Up;
		boolean NorthWestUp = world.getBlockState(pos.north().west().up()).getBlock() == this && NorthWest && Up;
		boolean SouthWestUp = world.getBlockState(pos.south().west().up()).getBlock() == this && SouthWest && Up;

		boolean NorthEastDown = world.getBlockState(pos.north().east().down()).getBlock() == this && NorthEast && Down;
		boolean SouthEastDown = world.getBlockState(pos.south().east().down()).getBlock() == this && SouthEast && Down;
		boolean NorthWestDown = world.getBlockState(pos.north().west().down()).getBlock() == this && NorthWest && Down;
		boolean SouthWestDown = world.getBlockState(pos.south().west().down()).getBlock() == this && SouthWest && Down;

		if (North) {
			VoxelShape NorthShape = createOffsetCuboidShape(1, 1, 0, 15, 15, 1);
			Center = VoxelShapes.union(Center, NorthShape);
		}
		if (East) {
			VoxelShape EastShape = createOffsetCuboidShape(15, 1, 1, 16, 15, 15);
			Center = VoxelShapes.union(Center, EastShape);
		}
		if (South) {
			VoxelShape SouthShape = createOffsetCuboidShape(1, 1, 15, 15, 15, 16);
			Center = VoxelShapes.union(Center, SouthShape);
		}
		if (West) {
			VoxelShape WestShape = createOffsetCuboidShape(0, 1, 1, 1, 15, 15);
			Center = VoxelShapes.union(Center, WestShape);
		}

		if (NorthEast) {
			VoxelShape NorthEastShape = createOffsetCuboidShape(15, 1, 0, 16, 15, 1);
			Center = VoxelShapes.union(Center, NorthEastShape);
		}
		if (SouthEast) {
			VoxelShape SouthEastShape = createOffsetCuboidShape(15, 1, 15, 16, 15, 16);
			Center = VoxelShapes.union(Center, SouthEastShape);
		}
		if (NorthWest) {
			VoxelShape NorthWestShape = createOffsetCuboidShape(0, 1, 0, 14, 15, 14);
			Center = VoxelShapes.union(Center, NorthWestShape);
		}
		if (SouthWest) {
			VoxelShape SouthWestShape = createOffsetCuboidShape(0, 1, 15, 1, 15, 16);
			Center = VoxelShapes.union(Center, SouthWestShape);
		}

		if (Up) {
			VoxelShape UpShape = createOffsetCuboidShape(1, 15, 1, 15, 16, 15);
			Center = VoxelShapes.union(Center, UpShape);
		}

		if (NorthUp) {
			VoxelShape NorthUpShape = createOffsetCuboidShape(1, 15, 0, 15, 16, 1);
			Center = VoxelShapes.union(Center, NorthUpShape);
		}
		if (EastUp) {
			VoxelShape EastUpShape = createOffsetCuboidShape(15, 15, 1, 16, 16, 15);
			Center = VoxelShapes.union(Center, EastUpShape);
		}
		if (SouthUp) {
			VoxelShape SouthUpShape = createOffsetCuboidShape(1, 15, 15, 15, 16, 16);
			Center = VoxelShapes.union(Center, SouthUpShape);
		}
		if (WestUp) {
			VoxelShape WestUpShape = createOffsetCuboidShape(0, 15, 1, 1, 16, 15);
			Center = VoxelShapes.union(Center, WestUpShape);
		}

		if (NorthEastUp) {
			VoxelShape NorthEastUpShape = createOffsetCuboidShape(15, 15, 0, 16, 16, 0);
			Center = VoxelShapes.union(Center, NorthEastUpShape);
		}
		if (SouthEastUp) {
			VoxelShape SouthEastUpShape = createOffsetCuboidShape(15, 15, 15, 16, 16, 16);
			Center = VoxelShapes.union(Center, SouthEastUpShape);
		}
		if (NorthWestUp) {
			VoxelShape NorthWestUpShape = createOffsetCuboidShape(0, 15, 0, 1, 16, 1);
			Center = VoxelShapes.union(Center, NorthWestUpShape);
		}
		if (SouthWestUp) {
			VoxelShape SouthWestUpShape = createOffsetCuboidShape(0, 15, 15, 1, 16, 16);
			Center = VoxelShapes.union(Center, SouthWestUpShape);
		}

		if (Down) {
			VoxelShape DownShape = createOffsetCuboidShape(1, 0, 1, 15, 1, 15);
			Center = VoxelShapes.union(Center, DownShape);
		}

		if (NorthDown) {
			VoxelShape NorthDownShape = createOffsetCuboidShape(1, 0, 0, 15, 1, 1);
			Center = VoxelShapes.union(Center, NorthDownShape);
		}
		if (EastDown) {
			VoxelShape EastDownShape = createOffsetCuboidShape(15, 0, 1, 16, 1, 15);
			Center = VoxelShapes.union(Center, EastDownShape);
		}
		if (SouthDown) {
			VoxelShape SouthDownShape = createOffsetCuboidShape(1, 0, 15, 15, 1, 16);
			Center = VoxelShapes.union(Center, SouthDownShape);
		}
		if (WestDown) {
			VoxelShape WestDownShape = createOffsetCuboidShape(0, 0, 1, 1, 1, 15);
			Center = VoxelShapes.union(Center, WestDownShape);
		}

		if (NorthEastDown) {
			VoxelShape NorthEastDownShape = createOffsetCuboidShape(15, 0, 0, 16, 1, 0);
			Center = VoxelShapes.union(Center, NorthEastDownShape);
		}
		if (SouthEastDown) {
			VoxelShape SouthEastDownShape = createOffsetCuboidShape(15, 0, 15, 16, 1, 16);
			Center = VoxelShapes.union(Center, SouthEastDownShape);
		}
		if (NorthWestDown) {
			VoxelShape NorthWestDownShape = createOffsetCuboidShape(0, 0, 0, 1, 1, 1);
			Center = VoxelShapes.union(Center, NorthWestDownShape);
		}
		if (SouthWestDown) {
			VoxelShape SouthWestDownShape = createOffsetCuboidShape(0, 0, 15, 1, 1, 16);
			Center = VoxelShapes.union(Center, SouthWestDownShape);
		}

		return Center;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new ChestBlockBuilderEntity();
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	public static VoxelShape createOffsetCuboidShape(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		return Block.createCuboidShape(xMin, yMin - 1, zMin, xMax, yMax - 1, zMax);
	}

	@Override
	public String getTranslationKey() {
		return Blocks.CHEST.getTranslationKey();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

		ChestBlockBuilderEntity chest = (ChestBlockBuilderEntity) world.getBlockEntity(pos);

		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			NamedScreenHandlerFactory screenHandlerFactory = chest;
			if (screenHandlerFactory != null && !chest.locked) {
				player.openHandledScreen(screenHandlerFactory);
				player.incrementStat(this.getOpenStat());
				PiglinBrain.onGuardedBlockInteracted(player, true);
			} else {
				player.playSound(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			return ActionResult.CONSUME;
		}

	}

	protected Stat<Identifier> getOpenStat() {
		return Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ChestBlockBuilderEntity) {
				ItemScatterer.spawn(world, pos, (ChestBlockBuilderEntity) blockEntity);
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

}
