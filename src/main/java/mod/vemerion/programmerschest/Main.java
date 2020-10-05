package mod.vemerion.programmerschest;

import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import mod.vemerion.programmerschest.tileentity.ProgrammersChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "programmers-chest";
	
	@ObjectHolder(Main.MODID + ":programmers_chest_tile_entity_type")
	public static final TileEntityType<? extends ProgrammersChestTileEntity> PROGRAMMERS_CHEST_TILE_ENTITY_TYPE = null;

	@ObjectHolder(Main.MODID + ":programmers_chest_container_type")
	public static final ContainerType<ProgrammersChestContainer> PROGRAMMERS_CHEST_CONTAINER_TYPE = null;

	@ObjectHolder(Main.MODID + ":programmers_chest_block")
	public static final Block PROGRAMMERS_CHEST_BLOCK = null;
}
