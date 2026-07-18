package com.example.client.mixin;

import com.example.client.ModState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class NoclipMixin {

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void modid$onMove(MoverType type, Vec3 movement, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        if (ModState.noclipEnabled && self == Minecraft.getInstance().player) {
            self.setPos(self.getX() + movement.x, self.getY() + movement.y, self.getZ() + movement.z);
            ci.cancel();
        }
    }
}
