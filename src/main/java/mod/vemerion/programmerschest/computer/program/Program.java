package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import net.minecraft.entity.player.PlayerEntity;

public abstract class Program {
	
	protected String[] args;
	
	public Program(String[] args) {
		this.args = args;
	}

	public abstract boolean isClientOnlyProgram();

	public abstract void run(Console console, FileSystem fileSystem, PlayerEntity user);

}
