package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import net.minecraft.entity.player.PlayerEntity;

public class ExitProgram extends Program {

	public ExitProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return true;
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println("Exits the chest", user);
		} else {
			console.close();
		}
	}

}
