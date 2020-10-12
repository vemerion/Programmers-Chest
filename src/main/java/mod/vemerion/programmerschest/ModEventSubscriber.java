package mod.vemerion.programmerschest;

import mod.vemerion.programmerschest.block.ProgrammersChestBlock;
import mod.vemerion.programmerschest.computer.BasicComputer;
import mod.vemerion.programmerschest.computer.Computer;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import mod.vemerion.programmerschest.network.Network;
import mod.vemerion.programmerschest.network.PrintlnMessage;
import mod.vemerion.programmerschest.network.ProgramMessage;
import mod.vemerion.programmerschest.network.SetPathMessage;
import mod.vemerion.programmerschest.tileentity.ProgrammersChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		event.getRegistry()
				.register(setup(
						new BlockItem(Main.PROGRAMMERS_CHEST_BLOCK, new Item.Properties().group(ItemGroup.SEARCH)),
						"programmers_chest_block_item"));
	}

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(
				setup(new ProgrammersChestBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2, 6)), "programmers_chest_block"));
	}

	@SubscribeEvent
	public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event) {
		TileEntityType<ProgrammersChestTileEntity> programmersChestTileEntityType = TileEntityType.Builder
				.<ProgrammersChestTileEntity>create(() -> new ProgrammersChestTileEntity(), Main.PROGRAMMERS_CHEST_BLOCK)
				.build(null);

		event.getRegistry().register(setup(programmersChestTileEntityType, "programmers_chest_tile_entity_type"));

	}

	@SubscribeEvent
	public static void onRegisterContainer(RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(
				setup(IForgeContainerType.create(ProgrammersChestContainer::new), "programmers_chest_container_type"));

	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(Computer.class, new IStorage<Computer>() {
			@Override
			public INBT writeNBT(Capability<Computer> capability, Computer instance, Direction side) {
				return instance.shutdown();
			}

			@Override
			public void readNBT(Capability<Computer> capability, Computer instance, Direction side, INBT nbt) {
				instance.startup((CompoundNBT) nbt);
			}
		}, BasicComputer::new);

		Network.INSTANCE.registerMessage(0, ProgramMessage.class, ProgramMessage::encode, ProgramMessage::decode,
				ProgramMessage::handle);
		Network.INSTANCE.registerMessage(1, PrintlnMessage.class, PrintlnMessage::encode, PrintlnMessage::decode,
				PrintlnMessage::handle);
		Network.INSTANCE.registerMessage(2, SetPathMessage.class, SetPathMessage::encode, SetPathMessage::decode,
				SetPathMessage::handle);
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Main.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
