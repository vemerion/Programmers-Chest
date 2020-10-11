package mod.vemerion.programmerschest.computer.filesystem;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface File {
	public CompoundNBT save();

	public void load(CompoundNBT compound);
	
	public String getName();
	
	public boolean isEmpty();
	
	public Folder parent();
	
	public String path();
	
	public ItemStack put(ItemStack stack);
	
	public List<ItemStack> get(String regex, int count);
	
	public static String toFileName(String name) {
		return name.replace(" ", "_");
	}
}
