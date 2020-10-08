package mod.vemerion.programmerschest.computer.filesystem;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class Folder implements File {
	private static final int MAX_NAME_LENGTH = 15;
	
	private List<Folder> children;
	private List<ItemStackFile> stacks;
	private String name;
	private Folder parent;
	
	public Folder(Folder parent) {
		this.parent = parent;
		children = new ArrayList<>();
		stacks = new ArrayList<>();
	}
	
	public Folder(Folder parent, String name) throws FileSystemException {
		this(parent);
		this.name = name;
		if (name.length() > MAX_NAME_LENGTH)
			throw new FileSystemException("err: Folder name can not be longer than " + MAX_NAME_LENGTH);
	}

	@Override
	public CompoundNBT save() {
		CompoundNBT compound = new CompoundNBT();
		compound.putString("name", getName());
		ListNBT childrenNBT = new ListNBT();
		for (File child : children) {
			childrenNBT.add(child.save());
		}
		compound.put("children", childrenNBT);
		ListNBT stacksNBT = new ListNBT();
		for (File child : stacks) {
			stacksNBT.add(child.save());
		}
		compound.put("stacks", stacksNBT);
		return compound;
	}

	@Override
	public void load(CompoundNBT compound) {
		name = compound.getString("name");
		ListNBT childrenNBT = compound.getList("children", 10);
		for (int i = 0; i < childrenNBT.size(); i++) {
			Folder file = new Folder(this);
			file.load(childrenNBT.getCompound(i));
			children.add(file);
		}
		ListNBT stacksNBT = compound.getList("stacks", 10);
		for (int i = 0; i < stacksNBT.size(); i++) {
			ItemStackFile file = new ItemStackFile(this);
			file.load(stacksNBT.getCompound(i));
			stacks.add(file);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public void mkdir(String folder) throws FileSystemException {
		if (folder.isEmpty())
			throw new FileSystemException("err: Folder name can not be empty");
		
		for (Folder f : children) {
			if (f.getName().equals(folder))
				throw new FileSystemException("err: Folder with that name already exists");
		}
		
		children.add(new Folder(this, folder));
	}

	public void rmdir(String folder) throws FileSystemException {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getName().equals(folder)) {
				if (children.get(i).isEmpty()) {
					children.remove(i);
					return;
				} else {
					throw new FileSystemException("err: Can not remove non-empty folder");
				}
			}
		}
		throw new FileSystemException("err: Folder does not exist");
	}

	@Override
	public boolean isEmpty() {
		return children.isEmpty() && stacks.isEmpty();
	}

	public List<String> ls() {
		List<String> fileNames = new ArrayList<>();
		for (File f : children)
			fileNames.add(f.getName());
		for (ItemStackFile f : stacks)
			fileNames.add(f.getCount() + ":" + f.getName());
		return fileNames;
	}

	@Override
	public Folder parent() {
		return parent;
	}

	public Folder child(String folder) throws FileSystemException {
		for (Folder f : children) {
			if (f.getName().equals(folder))
				return f;
		}
		throw new FileSystemException("err: Folder does not exist");
	}

	public String path() {
		return parent == null ? getName() : parent().path() + "/" + getName();
	}

	public ItemStack put(ItemStack stack) {
		for (File f : stacks) {
			stack = f.put(stack);
		}
		if (!stack.isEmpty()) {
			ItemStackFile file = new ItemStackFile(this);
			file.put(stack);
			stacks.add(file);
		}
		return stack;
	}

}
