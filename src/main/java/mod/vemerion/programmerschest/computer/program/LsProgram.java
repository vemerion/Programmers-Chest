package mod.vemerion.programmerschest.computer.program;

import java.util.List;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import net.minecraft.entity.player.PlayerEntity;

public class LsProgram extends Program {

	public LsProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return false;
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println("Lists the content of the current folder.", user);
			return;
		}
		
		try {
			List<String> files = fileSystem.ls();
			StringBuilder sb = new StringBuilder();
			for (String s : files) {
				sb.append(s + "    ");
			}
			console.println(sb.toString(), user);
		} catch (FileSystemException e) {
			console.println(e.getMessage(), user);
		}
	}

}
