package com.github.klyser8.earthbounds.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Biome.class)
public interface BiomeAccessor {

    @Invoker("getCategory")
    Biome.Category invokeGetCategory();

}
