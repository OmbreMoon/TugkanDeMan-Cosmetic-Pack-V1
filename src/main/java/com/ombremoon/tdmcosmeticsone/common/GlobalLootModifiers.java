package com.ombremoon.tdmcosmeticsone.common;

import com.ombremoon.tdmcosmeticsone.Constants;
import com.ombremoon.tdmcosmeticsone.common.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class GlobalLootModifiers extends GlobalLootModifierProvider {
    private static final String CHEST = "chests";
    private static final String VILLAGE = "village";
    public GlobalLootModifiers(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        for (ResourceLocation resourceLocation : BuiltInLootTables.all()) {
            if (resourceLocation.getPath().startsWith(CHEST)) {
                String name = resourceLocation.getPath().substring(CHEST.length() + 1);
                if (name.startsWith(VILLAGE)) {
                    name = name.substring(VILLAGE.length() + 1);
                }
                addToStructureLootTable("small_hat_from_" + name, resourceLocation, ItemInit.SHORT_ENTREPRENEUR_HAT.get(), 0.1F);
                addToStructureLootTable("medium_hat_from_" + name, resourceLocation, ItemInit.MEDIUM_ENTREPRENEUR_HAT.get(), 0.05F);
                addToStructureLootTable("tall_hat_from_" + name, resourceLocation, ItemInit.TALL_ENTREPRENEUR_HAT.get(), 0.025F);
                addToStructureLootTable("suitcase_from_" + name, resourceLocation, ItemInit.SUITCASE.get(), 0.2F);
                addToStructureLootTable("money_bag_from_" + name, resourceLocation, ItemInit.MONEY_BAG.get(), 0.1F);
                addToStructureLootTable("dollar_balloon_from_" + name, resourceLocation, ItemInit.DOLLAR_BALLOON.get(), 0.05F);
                addToStructureLootTable("hacker_visor_from_" + name, resourceLocation, ItemInit.HACKER_VISOR.get(), 0.1F);
                addToStructureLootTable("hacking_gauntlet_from_" + name, resourceLocation, ItemInit.HACKING_GAUNTLET.get(), 0.05F);
                addToStructureLootTable("disruption_backpack_from_" + name, resourceLocation, ItemInit.DISRUPTION_BACKPACK.get(), 0.15F);
                addToStructureLootTable("military_coat_from_" + name, resourceLocation, ItemInit.MILITARY_COAT.get(), 0.15F);
                addToStructureLootTable("military_cap_from_" + name, resourceLocation, ItemInit.MILITARY_CAP.get(), 0.15F);
                addToStructureLootTable("military_cannon_from_" + name, resourceLocation, ItemInit.MILITARY_CANNON.get(), 0.1F);
                addToStructureLootTable("ghostvision_goggles_from_" + name, resourceLocation, ItemInit.GHOSTVISION_GOGGLES.get(), 0.2F);
                addToStructureLootTable("vacuum_backpack_from_" + name, resourceLocation, ItemInit.VACUUM_BACKPACK.get(), 0.15F);
                addToStructureLootTable("ectocleaner_from_" + name, resourceLocation, ItemInit.ECTOCLEANER.get(), 0.15F);
            }
        }
    }

    protected void addToStructureLootTable(String modifierName, ResourceLocation resourceLocation, Item item, float probabilityChance) {
        add(modifierName, new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(resourceLocation).build(),
                LootItemRandomChanceCondition.randomChance(probabilityChance).build()
        }, item));
    }
}
