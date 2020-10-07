package mod.vemerion.programmerschest.computer.filesystem;

import net.minecraft.nbt.CompoundNBT;

public interface File {
	public CompoundNBT save();

	public void load(CompoundNBT compound);
	
	public String name();
	
	public boolean isEmpty();
	
	public Folder parent();
}
