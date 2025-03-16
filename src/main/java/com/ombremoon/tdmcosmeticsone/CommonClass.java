package com.ombremoon.tdmcosmeticsone;

import com.ombremoon.tdmcosmeticsone.common.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLLoader;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        ItemInit.register(modEventBus);
        EntityInit.register(modEventBus);
        MobEffectInit.register(modEventBus);
        MenuTypeInit.register(modEventBus);
        LootModifierInit.register(modEventBus);
    }

    public static boolean isDevEnv() {
        return !FMLLoader.isProduction();
    }

    public static ResourceLocation customLocation(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }
}
