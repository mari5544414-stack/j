package com.example.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ModScreen extends Screen {
    private EditBox playerTargetBox;
    private Component searchResult = Component.literal("");

    public ModScreen() {
        super(Component.literal("Mod Menu"));
    }

    @Override
    protected void init() {
        super.init();
        
        // Кнопка Бессмертия
        this.addRenderableWidget(Button.builder(
            Component.literal("Бессмертие: " + (ModState.godModeEnabled ? "ВКЛ" : "ВЫКЛ")),
            button -> {
                ModState.godModeEnabled = !ModState.godModeEnabled;
                button.setMessage(Component.literal("Бессмертие: " + (ModState.godModeEnabled ? "ВКЛ" : "ВЫКЛ")));
            }
        ).bounds(this.width / 2 - 100, 20, 200, 20).build());

        // Кнопка Полета
        this.addRenderableWidget(Button.builder(
            Component.literal("Полет: " + (ModState.flightEnabled ? "ВКЛ" : "ВЫКЛ")),
            button -> {
                ModState.flightEnabled = !ModState.flightEnabled;
                button.setMessage(Component.literal("Полет: " + (ModState.flightEnabled ? "ВКЛ" : "ВЫКЛ")));
            }
        ).bounds(this.width / 2 - 100, 50, 200, 20).build());

        // Кнопка Noclip
        this.addRenderableWidget(Button.builder(
            Component.literal("Noclip: " + (ModState.noclipEnabled ? "ВКЛ" : "ВЫКЛ")),
            button -> {
                ModState.noclipEnabled = !ModState.noclipEnabled;
                button.setMessage(Component.literal("Noclip: " + (ModState.noclipEnabled ? "ВКЛ" : "ВЫКЛ")));
            }
        ).bounds(this.width / 2 - 100, 80, 200, 20).build());

        // НОВОЕ: Поле для ввода ника
        this.playerTargetBox = new EditBox(this.font, this.width / 2 - 100, 115, 140, 20, Component.literal("Ник игрока"));
        this.playerTargetBox.setMaxLength(16);
        this.addRenderableWidget(this.playerTargetBox);

        // НОВОЕ: Кнопка для поиска координат
        this.addRenderableWidget(Button.builder(
            Component.literal("Найти"),
            button -> {
                searchPlayer(this.playerTargetBox.getValue());
            }
        ).bounds(this.width / 2 + 45, 115, 55, 20).build());
    }

    private void searchPlayer(String name) {
        if (this.minecraft == null || this.minecraft.level == null || name.isEmpty()) return;

        // Перебираем всех игроков, о которых знает клиент (в зоне прорисовки)
        for (Player player : this.minecraft.level.players()) {
            if (player.getName().getString().equalsIgnoreCase(name)) {
                int x = (int) player.getX();
                int y = (int) player.getY();
                int z = (int) player.getZ();
                this.searchResult = Component.literal("§aКоординаты " + name + ": X: " + x + " | Y: " + y + " | Z: " + z);
                return;
            }
        }
        this.searchResult = Component.literal("§cИгрок не найден (Слишком далеко от вас)");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick); // Затенение фона
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        // Отрисовка текста с результатами поиска
        guiGraphics.drawCenteredString(this.font, this.searchResult, this.width / 2, 145, 0xFFFFFF);
    }
}
