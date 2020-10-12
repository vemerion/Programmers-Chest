package mod.vemerion.programmerschest.computer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public interface Computer {
	
	public boolean login(PlayerEntity player);

	public void logout(PlayerEntity player);
	
	public void run(String argString, PlayerEntity user);
	
	public boolean isOccupied();
	
	public CompoundNBT shutdown();
	
	public void startup(CompoundNBT compound);
	
	public void destroy(World world, Vector3d position);
}
