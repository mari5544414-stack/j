package com.example.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModScreen extends Screen {

    public ModScreen() {
        super(Component.literal("Mod Menu"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 30;

        this.addRenderableWidget(Button.builder(
                Component.literal(noclipLabel()),
                button -> {
                    ModState.noclipEnabled = !ModState.noclipEnabled;
                    button.setMessage(Component.literal(noclipLabel()));
                })
                .bounds(centerX - 100, startY, 200, 20)
                .build());

        this.addRenderableWidget(Button.builder(
                Component.literal(flightLabel()),
                button -> {
                    ModState.flightEnabled = !ModState.flightEnabled;
                    button.setMessage(Component.literal(flightLabel()));
                    if (!ModState.flightEnabled && this.minecraft.player != null) {
                        this.minecraft.player.getAbilities().flying = false;
                        this.minecraft.player.getAbilities().mayfly = false;
                        this.minecraft.player.onUpdateAbilities();
                    }
                })
                .bounds(centerX - 100, startY + 25, 200, 20)
                .build());

        this.addRenderableWidget(Button.builder(
                Component.literal("Закрыть"),
                button -> this.onClose())
                .bounds(centerX - 100, startY + 55, 200, 20)
                .build());
    }

    private String noclipLabel() {
        return "Noclip: " + (ModState.noclipEnabled ? "ВКЛ" : "ВЫКЛ");
    }

    private String flightLabel() {
        return "Полёт: " + (ModState.flightEnabled ? "ВКЛ" : "ВЫКЛ");
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.text(this.font, "Mod Menu", this.width / 2 - 30, this.height / 2 - 60, 0xFFFFFFFF, true);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
