package net.ludocrypt.chestblocks.block;

import net.minecraft.block.Blocks;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class TrappedChestBlock extends ChestBlock {

	public TrappedChestBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected Stat<Identifier> getOpenStat() {
		return Stats.CUSTOM.getOrCreateStat(Stats.TRIGGER_TRAPPED_CHEST);
	}

	@Override
	public String getTranslationKey() {
		return Blocks.TRAPPED_CHEST.getTranslationKey();
	}

}
