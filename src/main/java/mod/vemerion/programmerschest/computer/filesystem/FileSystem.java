package mod.vemerion.programmerschest.computer.filesystem;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface FileSystem {
	void put(ItemStack stack) throws FileSystemException;

	List<ItemStack> get(String regex, int count) throws FileSystemException;

	void mkdir(String name) throws FileSystemException;

	void rmdir(String name) throws FileSystemException;

	List<String> ls() throws FileSystemException;

	void cd(String folder) throws FileSystemException;
	
	String pwd() throws FileSystemException;

	CompoundNBT save();

	void load(CompoundNBT compound);
}
