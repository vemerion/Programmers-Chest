package mod.vemerion.programmerschest.computer;

import net.minecraft.entity.player.PlayerEntity;

public interface Console {
	void println(String s, PlayerEntity user);

	void close();
}
