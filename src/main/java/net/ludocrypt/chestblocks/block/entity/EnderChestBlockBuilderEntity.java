package net.ludocrypt.chestblocks.block.entity;

import net.ludocrypt.chestblocks.ChestBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class EnderChestBlockBuilderEntity extends BlockEntity {

	public boolean locked = false;

	public EnderChestBlockBuilderEntity(BlockPos blockPos, BlockState state) {
		super(ChestBlocks.ENDER_CHEST_BLOCK_ENTITY, blockPos, state);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		this.locked = tag.getBoolean("locked");
		super.readNbt(tag);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		tag.putBoolean("locked", this.locked);
		return super.writeNbt(tag);
	}

}
