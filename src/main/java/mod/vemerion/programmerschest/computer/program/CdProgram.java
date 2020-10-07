package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import net.minecraft.entity.player.PlayerEntity;

public class CdProgram extends Program {

	public CdProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return false;
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println("Usage: cd [folder]. Change current folder. Use '..' to go up one folder.", user);
		}
		
		try {
			fileSystem.cd(args.length < 2 ? "" : args[1]);
			console.setPath(fileSystem.pwd(), user);
		} catch (FileSystemException e) {
			console.println(e.getMessage(), user);
		}
	}

}
