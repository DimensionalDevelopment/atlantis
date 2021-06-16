package com.mystic.atlantis;

import java.awt.Color;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;

public class ModTrackedDataHandlers {
	public static final TrackedDataHandler<Color> COLOR = new TrackedDataHandler<Color>() {
		public void write(PacketByteBuf packetByteBuf, Color color) {
			packetByteBuf.writeVarInt(color.getRGB());
		}

		public Color read(PacketByteBuf packetByteBuf) {
			return new Color(packetByteBuf.readVarInt());
		}

		public Color copy(Color color) {
			return color;
		}
	};

	static {
		TrackedDataHandlerRegistry.register(COLOR);
	}
}
