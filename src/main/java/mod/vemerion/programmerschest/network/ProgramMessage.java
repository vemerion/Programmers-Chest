package mod.vemerion.programmerschest.network;

import java.util.function.Supplier;

import mod.vemerion.programmerschest.Main;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class ProgramMessage {
	
	private String argumentString;
	private BlockPos pos;
	
	public ProgramMessage(String argumentString, BlockPos pos) {
		this.argumentString = argumentString;
		this.pos = pos;
	}

	public static void encode(final ProgramMessage msg, final PacketBuffer buffer) {
		buffer.writeString(msg.argumentString);
		buffer.writeBlockPos(msg.pos);
	}

	public static ProgramMessage decode(final PacketBuffer buffer) {
		return new ProgramMessage(buffer.readString(200), buffer.readBlockPos());
	}

	public static void handle(final ProgramMessage msg, final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> {
			World world = context.getSender().world;
			if (context.getSender() != null && world.isAreaLoaded(msg.pos, 1)) {
				world.getTileEntity(msg.pos).getCapability(Main.COMPUTER).ifPresent(c -> c.run(msg.argumentString, context.getSender()));
			}
		});  
	}
}
