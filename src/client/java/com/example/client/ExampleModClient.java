package com.example.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

    private static final Identifier SPEED_BOOST_ID =
            Identifier.fromNamespaceAndPath("modid", "speed_boost");

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("modid", "speedboost_category")
    );

    private static KeyMapping speedKey;

    @Override
    public void onInitializeClient() {
        speedKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.modid.speedboost",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (speedKey.consumeClick()) {
                if (client.player == null) continue;

                AttributeInstance attribute = client.player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute == null) continue;

                if (attribute.getModifier(SPEED_BOOST_ID) == null) {
                    attribute.addTransientModifier(new AttributeModifier(
                            SPEED_BOOST_ID,
                            100.0,
                            AttributeModifier.Operation.ADD_VALUE
                    ));
                    client.player.sendSystemMessage(Component.literal("§aСкорость +100"));
                } else {
                    attribute.removeModifier(SPEED_BOOST_ID);
                    client.player.sendSystemMessage(Component.literal("§cСкорость сброшена"));
                }
            }
        });
    }
}
