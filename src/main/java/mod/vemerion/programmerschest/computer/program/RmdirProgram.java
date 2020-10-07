package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import net.minecraft.entity.player.PlayerEntity;

public class RmdirProgram extends Program {

	public RmdirProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return false;
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println("Usage: rmdir [folder name]. Removes folder.", user);
			return;
		}
		
		if (args.length < 2)
			console.println("err: Usage: rmdir [filename]", user);
		else {
			try {
				fileSystem.rmdir(args[1]);
			} catch (FileSystemException e) {
				console.println(e.getMessage(), user);
			}
		}
	}

}
