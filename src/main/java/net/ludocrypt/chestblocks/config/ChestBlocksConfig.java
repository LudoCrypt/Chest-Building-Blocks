package net.ludocrypt.chestblocks.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.BoundedDiscrete;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;

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
