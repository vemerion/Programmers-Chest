package mod.vemerion.programmerschest.computer.program;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.File;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class PutProgram extends ItemProgram {

	public PutProgram(String[] args) {
		super(args);
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println(
					"Usage: put [item name] [count]. Insert item into chest. Count is optional. Replace spaces with underscores. Can use regex.",
					user);
			return;
		}

		// Get count
		int count = getCount(console, user);
		if (count == -1)
			return;

		if (args.length < 2) {
			console.println("err: Usage: put [item name] [count].", user);
			return;
		}

		// Get Items
		List<ItemStack> putting = new ArrayList<>();
		IInventory inventory = user.inventory;
		String regex = args[1];
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (File.toFileName(inventory.getStackInSlot(i).getDisplayName().getString()).matches(regex)) {
				ItemStack match = inventory.decrStackSize(i, count);
				putting.add(match);
				count -= match.getCount();
				if (count == 0)
					break;
			}
		}
		
		for (ItemStack stack : putting) {
			try {
				fileSystem.put(stack);
			} catch (FileSystemException e) {
				console.println(e.getMessage(), user);
			}
		}
	}

}
