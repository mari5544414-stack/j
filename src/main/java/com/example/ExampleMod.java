package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class ExampleModClient implements ClientModInitializer {

    // Уникальный ID для нашего модификатора, чтобы не добавить его дважды
    private static final UUID SPEED_BOOST_ID =
            UUID.fromString("2ad3f44e-4730-4f38-bf9f-ee111e9bfd4e");

    private static KeyBinding speedKey;

    @Override
    public void onInitializeClient() {
        speedKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.example-mod.speedboost",      // id клавиши (для lang-файла)
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,          // сама клавиша
                "category.example-mod.keys"        // категория в настройках управления
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (speedKey.wasPressed()) {
                if (client.player == null) continue;

                EntityAttributeInstance attribute =
                        client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

                if (attribute == null) continue;

                if (attribute.getModifier(SPEED_BOOST_ID) == null) {
                    // включаем ускорение
                    attribute.addTemporaryModifier(new EntityAttributeModifier(
                            SPEED_BOOST_ID,
                            "Speed boost",
                            100.0,
                            EntityAttributeModifier.Operation.ADDITION
                    ));
                    client.player.sendMessage(Text.literal("§aСкорость +100"), true);
                } else {
                    // выключаем
                    attribute.removeModifier(SPEED_BOOST_ID);
                    client.player.sendMessage(Text.literal("§cСкорость сброшена"), true);
                }
            }
        });
    }
}
