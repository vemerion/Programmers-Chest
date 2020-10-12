package mod.vemerion.programmerschest.computer.filesystem;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class TreeFileSystem implements FileSystem {

	private Folder root;
	private Folder currentFolder;

	public TreeFileSystem() {
		try {
			root = new Folder(null, "home");
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		currentFolder = root;
	}

	@Override
	public void put(ItemStack stack) {
		currentFolder.put(stack);
	}

	@Override
	public List<ItemStack> get(String regex, int count) {
		return currentFolder.get(regex, count);
	}

	@Override
	public void mkdir(String name) throws FileSystemException {
		currentFolder.mkdir(name);
	}

	@Override
	public void rmdir(String name) throws FileSystemException {
		currentFolder.rmdir(name);
	}

	@Override
	public List<String> ls() {
		return currentFolder.ls();
	}

	@Override
	public void cd(String folder) throws FileSystemException {
		if (folder.isEmpty()) {
			currentFolder = root;
		} else if (folder.equals("..")) {
			if (currentFolder != root)
				currentFolder = currentFolder.parent();
		} else {
			currentFolder = currentFolder.child(folder);
		}
	}

	@Override
	public CompoundNBT save() {
		return root.save();
	}

	@Override
	public void load(CompoundNBT compound) {
		root.load(compound);
	}

	@Override
	public String pwd() throws FileSystemException {
		return currentFolder.path();
	}
}
