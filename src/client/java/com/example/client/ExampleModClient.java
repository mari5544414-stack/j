package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

    private static final Identifier SPEED_BOOST_ID =
            Identifier.of("modid", "speed_boost");

    private static KeyBinding speedKey;

    @Override
    public void onInitializeClient() {
        speedKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.modid.speedboost",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.modid.keys"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (speedKey.wasPressed()) {
                if (client.player == null) continue;

                EntityAttributeInstance attribute =
                        client.player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);

                if (attribute == null) continue;

                if (attribute.getModifier(SPEED_BOOST_ID) == null) {
                    attribute.addTemporaryModifier(new EntityAttributeModifier(
                            SPEED_BOOST_ID,
                            100.0,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ));
                    client.player.sendMessage(Text.literal("§aСкорость +100"), true);
                } else {
                    attribute.removeModifier(SPEED_BOOST_ID);
                    client.player.sendMessage(Text.literal("§cСкорость сброшена"), true);
                }
            }
        });
    }
}
