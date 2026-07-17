package com.example;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;

public class SpeedGuiScreen extends Screen {

    public SpeedGuiScreen() {
        super(Text.literal("Настройка скорости"));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 120;
        int buttonHeight = 20;
        int x = this.width / 2 - buttonWidth / 2;
        int yStart = this.height / 2 - 40;

        // Кнопка: Обычная скорость
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Обычная (x1)"), button -> setPlayerSpeed(0.1f))
                .dimensions(x, yStart, buttonWidth, buttonHeight).build());

        // Кнопка: Скорость x2
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Быстрая (x2)"), button -> setPlayerSpeed(0.2f))
                .dimensions(x, yStart + 25, buttonWidth, buttonHeight).build());

        // Кнопка: Скорость x5
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Супер (x5)"), button -> setPlayerSpeed(0.5f))
                .dimensions(x, yStart + 50, buttonWidth, buttonHeight).build());

        // Кнопка: Закрыть
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Закрыть"), button -> this.close())
                .dimensions(x, yStart + 85, buttonWidth, buttonHeight).build());
    }

    private void setPlayerSpeed(float speed) {
        if (this.client != null && this.client.player != null) {
            // Используем корректный реестр атрибутов
            var attribute = this.client.player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.setBaseValue(speed);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 60, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPauseGame() {
        return false;
    }
}
