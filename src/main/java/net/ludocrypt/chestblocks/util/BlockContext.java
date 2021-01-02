package net.ludocrypt.chestblocks.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockContext {

	public final boolean Up;
	public final boolean Down;
	public final boolean North;
	public final boolean East;
	public final boolean South;
	public final boolean West;

	public final boolean NorthEast;
	public final boolean SouthEast;
	public final boolean NorthWest;
	public final boolean SouthWest;

	public final boolean NorthUp;
	public final boolean EastUp;
	public final boolean SouthUp;
	public final boolean WestUp;

	public final boolean NorthDown;
	public final boolean EastDown;
	public final boolean SouthDown;
	public final boolean WestDown;

	public BlockContext(World world, BlockPos pos, Block block) {
		Up = world.getBlockState(pos.up()).getBlock() == block;
		Down = world.getBlockState(pos.down()).getBlock() == block;
		North = world.getBlockState(pos.north()).getBlock() == block;
		East = world.getBlockState(pos.east()).getBlock() == block;
		South = world.getBlockState(pos.south()).getBlock() == block;
		West = world.getBlockState(pos.west()).getBlock() == block;

		NorthEast = world.getBlockState(pos.north().east()).getBlock() == block && North && East;
		SouthEast = world.getBlockState(pos.south().east()).getBlock() == block && South && East;
		NorthWest = world.getBlockState(pos.north().west()).getBlock() == block && North && West;
		SouthWest = world.getBlockState(pos.south().west()).getBlock() == block && South && West;

		NorthUp = world.getBlockState(pos.north().up()).getBlock() == block && North && Up;
		EastUp = world.getBlockState(pos.east().up()).getBlock() == block && East && Up;
		SouthUp = world.getBlockState(pos.south().up()).getBlock() == block && South && Up;
		WestUp = world.getBlockState(pos.west().up()).getBlock() == block && West && Up;

		NorthDown = world.getBlockState(pos.north().down()).getBlock() == block && North && Down;
		EastDown = world.getBlockState(pos.east().down()).getBlock() == block && East && Down;
		SouthDown = world.getBlockState(pos.south().down()).getBlock() == block && South && Down;
		WestDown = world.getBlockState(pos.west().down()).getBlock() == block && West && Down;
	}

}
