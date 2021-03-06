package net.ludocrypt.chestblocks.block.entity;

import net.ludocrypt.chestblocks.ChestBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ChestBlockBuilderEntity extends LootableContainerBlockEntity {

	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
	public boolean locked = false;
	protected int viewerCount;

	public ChestBlockBuilderEntity(BlockPos blockPos, BlockState blockState) {
		super(ChestBlocks.CHEST_BLOCK_ENTITY, blockPos, blockState);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(tag)) {
			Inventories.readNbt(tag, this.inventory);
		}
		this.locked = tag.getBoolean("locked");
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		if (!this.serializeLootTable(tag)) {
			Inventories.writeNbt(tag, this.inventory);
		}
		tag.putBoolean("locked", this.locked);
		return tag;
	}

	@Override
	public int size() {
		return 27;
	}

	@Override
	protected DefaultedList<ItemStack> getInvStackList() {
		return this.inventory;
	}

	@Override
	protected void setInvStackList(DefaultedList<ItemStack> list) {
		this.inventory = list;
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText(Blocks.CHEST.getTranslationKey());
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
	}

	@Override
	public void onOpen(PlayerEntity player) {
		this.world.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public void onClose(PlayerEntity player) {
		this.world.playSound(null, pos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
	}
}
