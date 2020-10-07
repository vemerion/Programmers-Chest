package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import net.minecraft.entity.player.PlayerEntity;

public class MkdirProgram extends Program {

	public MkdirProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return false;
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (args.length < 2)
			console.println("err: Usage: mkdir [filename]", user);
		else {
			try {
				fileSystem.mkdir(args[1]);
			} catch (FileSystemException e) {
				console.println(e.getMessage(), user);
			}
		}
	}

}
