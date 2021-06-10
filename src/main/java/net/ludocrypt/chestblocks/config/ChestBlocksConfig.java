package net.ludocrypt.chestblocks.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "chestblocks")
public class ChestBlocksConfig implements ConfigData {

	@BoundedDiscrete(min = 1, max = 8)
	public int expensiveRendering = 1;

	public static void init() {
		AutoConfig.register(ChestBlocksConfig.class, GsonConfigSerializer::new);
	}

	public static ChestBlocksConfig getInstance() {
		return AutoConfig.getConfigHolder(ChestBlocksConfig.class).getConfig();
	}

}
