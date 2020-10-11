package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import net.minecraft.entity.player.PlayerEntity;

public abstract class ItemProgram extends Program {

	public ItemProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return false;
	}

	protected int getCount(Console console, PlayerEntity user) {
		int count = Integer.MAX_VALUE;
		if (args.length > 2) {
			try {
				count = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				console.println("err: Invalid count", user);
				return -1;
			}
			if (count <= 0) {
				console.println("err: Count must be positive", user);
				return -1;
			}
		}
		return count;
	}

}
