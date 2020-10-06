package mod.vemerion.programmerschest.console;

import net.minecraft.entity.player.PlayerEntity;

public abstract class Program {
	
	protected String[] args;
	
	public Program(String[] args) {
		this.args = args;
	}

	public abstract boolean isClientOnlyProgram();

	public abstract void run(Console console, PlayerEntity user);

}
