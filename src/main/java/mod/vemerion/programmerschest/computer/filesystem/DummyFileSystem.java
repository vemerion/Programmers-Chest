package mod.vemerion.programmerschest.computer.filesystem;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DummyFileSystem implements FileSystem {

	@Override
	public void put(ItemStack stack) throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}

	@Override
	public List<ItemStack> get(String regex, int count) throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}

	@Override
	public void mkdir(String name) throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}

	@Override
	public void rmdir(String name) throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}

	@Override
	public List<String> ls() throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}

	@Override
	public void cd(String folder) throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}

	@Override
	public CompoundNBT save() {
		return null;
	}

	@Override
	public void load(CompoundNBT compound) {

	}

	@Override
	public String pwd() throws FileSystemException {
		throw new FileSystemException("err: Trying to use DummyFileSystem");
	}
}
