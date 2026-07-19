package com.example.client.mixin;

import com.example.client.ModState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class NoclipMixin {
    
    @Inject(method = "move", at = @At("HEAD"))
    private void onMove(MoverType type, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        
        // Проверяем, что это именно мы (наш игрок)
        if (entity instanceof Player && entity == Minecraft.getInstance().player) {
            if (ModState.noclipEnabled) {
                entity.noPhysics = true; // Выключает коллизию блоков (проход сквозь стены)
            } else {
                entity.noPhysics = false; // Включает обратно, если выключено
            }
        }
    }
}
