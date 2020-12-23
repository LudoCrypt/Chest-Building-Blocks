package net.ludocrypt.chestblocks.block.entity;

import net.ludocrypt.chestblocks.ChestBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class EnderChestBlockBuilderEntity extends BlockEntity {

	public boolean locked = false;
	public boolean latched = true;

	public EnderChestBlockBuilderEntity() {
		super(ChestBlocks.ENDER_CHEST_BLOCK_ENTITY);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		this.locked = tag.getBoolean("locked");
		this.latched = tag.getBoolean("latched");
		super.fromTag(state, tag);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag.putBoolean("locked", this.locked);
		tag.putBoolean("latched", this.latched);
		return super.toTag(tag);
	}

}
