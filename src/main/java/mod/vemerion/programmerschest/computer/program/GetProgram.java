package mod.vemerion.programmerschest.computer.program;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class GetProgram extends ItemProgram {

	public GetProgram(String[] args) {
		super(args);
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println(
					"Usage: get [item name] [count]. Extract item from chest. Count is optional. Replace spaces with underscores. Can use regex.",
					user);
			return;
		}

		// Get count
		int count = getCount(console, user);
		if (count == -1)
			return;

		if (args.length < 2) {
			console.println("err: Usage: get [item name] [count].", user);
			return;
		}

		// Get Items
		List<ItemStack> getting = new ArrayList<>();
		try {
			getting = fileSystem.get(args[1], count);
		} catch (FileSystemException e) {
			console.println(e.getMessage(), user);
		}

		for (ItemStack stack : getting) {
			user.addItemStackToInventory(stack);
		}

		// Re-insert into chest if player inventory is full
		for (ItemStack stack : getting) {
			if (!stack.isEmpty()) {
				try {
					fileSystem.put(stack);
				} catch (FileSystemException e) {
					console.println(e.getMessage(), user);
				}
			}
		}
	}

}
