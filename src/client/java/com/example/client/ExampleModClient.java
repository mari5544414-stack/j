package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

// Твои импорты кнопок (KeyMapping и т.д.) оставляем как были...

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Твоя регистрация кнопки открытия меню здесь...
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Твоя проверка нажатия кнопки здесь...
            // while (openMenuKey.consumeClick()) { client.setScreen(new ModScreen()); }
            
            if (client.player != null) {
                // Если включен Noclip ИЛИ Полет
                if (ModState.flightEnabled || ModState.noclipEnabled) {
                    client.player.getAbilities().mayfly = true;
                    // Автоматически заставляем лететь при noclip, чтобы не падать в бездну
                    if (ModState.noclipEnabled && !client.player.getAbilities().flying) {
                        client.player.getAbilities().flying = true;
                    }
                } else if (!client.player.isCreative() && !client.player.isSpectator()) {
                    // Выключаем только если игрок в выживании
                    client.player.getAbilities().mayfly = false;
                    client.player.getAbilities().flying = false;
                }
                
                // Это важно для синхронизации клиента с твоими изменениями
                client.player.onUpdateAbilities();
            }
        });
    }
}
