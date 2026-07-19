package com.example.client;

import net.minecraft.client.Minecraft;
// Возвращаем твой правильный импорт вместо GuiGraphics:
import net.minecraft.client.gui.GuiGraphicsExtractor;
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

        // Поле для ввода ника (для отслеживания)
        this.playerTargetBox = new EditBox(this.font, this.width / 2 - 100, 115, 140, 20, Component.literal("Ник игрока"));
        this.playerTargetBox.setMaxLength(16);
        this.addRenderableWidget(this.playerTargetBox);

        // Кнопка для поиска координат
        this.addRenderableWidget(Button.builder(
            Component.literal("Найти"),
            button -> {
                searchPlayer(this.playerTargetBox.getValue());
            }
        ).bounds(this.width / 2 + 45, 115, 55, 20).build());
    }

    private void searchPlayer(String name) {
        if (this.minecraft == null || this.minecraft.level == null || name.isEmpty()) return;

        // Поиск среди игроков в зоне прорисовки
        for (Player player : this.minecraft.level.players()) {
            if (player.getName().getString().equalsIgnoreCase(name)) {
                int x = (int) player.getX();
                int y = (int) player.getY();
                int z = (int) player.getZ();
                this.searchResult = Component.literal("§aКоординаты " + name + ": X: " + x + " | Y: " + y + " | Z: " + z);
                return;
            }
        }
        this.searchResult = Component.literal("§cИгрок не найден (Слишком далеко)");
    }

    // ИСПРАВЛЕНИЕ: Используем правильный метод из твоего окружения
    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        // Отрисовываем базовые элементы экрана (кнопки, фон и т.д.)
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        
        // Отрисовываем текст с результатами поиска по центру под панелью
        int textWidth = this.font.width(this.searchResult);
        graphics.text(this.font, this.searchResult, this.width / 2 - textWidth / 2, 145, 0xFFFFFF);
    }
}
