package mod.vemerion.programmerschest.tileentity;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.Parser;
import mod.vemerion.programmerschest.computer.ServerConsole;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.TreeFileSystem;
import mod.vemerion.programmerschest.computer.program.Program;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ProgrammersChestTileEntity extends TileEntity implements INamedContainerProvider {
	
	private FileSystem fileSystem = new TreeFileSystem();
	private Console console = new ServerConsole();

	public ProgrammersChestTileEntity() {
		super(Main.PROGRAMMERS_CHEST_TILE_ENTITY_TYPE);
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
		return new ProgrammersChestContainer(id, playerInv, getPos());
	}

	@Override
	public ITextComponent getDisplayName() {
		return StringTextComponent.EMPTY;
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("fileSystem", fileSystem.save());
		return super.write(compound);
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		fileSystem.load(nbt.getCompound("fileSystem"));
		super.read(state, nbt);
	}
	
	public void runProgram(String argumentString, PlayerEntity user) {
		Parser parser = new Parser();
		Program program = parser.parse(argumentString);
		if (!program.isClientOnlyProgram())
			program.run(console, fileSystem, user);
	}

}
