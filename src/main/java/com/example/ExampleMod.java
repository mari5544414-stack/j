package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "modid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		System.out.println("--- MOD WORK ---");

		// Регистрируем проверку нажатия клавиши каждый игровой тик на клиенте
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.getWindow() != null) {
				// Проверяем, нажат ли Правый Shift через LWJGL напрямую
				if (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
					// Если никакое меню еще не открыто, открываем наше GUI
					if (client.currentScreen == null) {
						client.setScreen(new SpeedGuiScreen());
					}
				}
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
