package mod.vemerion.programmerschest.network;

import java.util.function.Supplier;

import mod.vemerion.programmerschest.computer.Console;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SetPathMessage {
	private String path;

	public SetPathMessage(String path) {
		this.path = path;
	}

	public static void encode(final SetPathMessage msg, final PacketBuffer buffer) {
		buffer.writeString(msg.path);

	}

	public static SetPathMessage decode(final PacketBuffer buffer) {
		return new SetPathMessage(buffer.readString());
	}

	public static void handle(final SetPathMessage msg, final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> SetPath.setPath(msg.path)));
	}

	private static class SetPath {
		private static DistExecutor.SafeRunnable setPath(String path) {
			return new DistExecutor.SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft mc = Minecraft.getInstance();
					if (mc != null && mc.player != null && mc.currentScreen != null
							&& mc.currentScreen instanceof Console)
						((Console) mc.currentScreen).setPath(path, mc.player);
				}
			};
		}
	}
}
