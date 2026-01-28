package me.codecraft.entity;

import me.codecraft.Spidy;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class MainEntity {

    public static final ResourceKey<EntityType<?>> SPIDER_WEB = RegistryKey("spider_web");

    public static final EntityType<SpiderWeb> SPIDER_WEB_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            SPIDER_WEB,
            EntityType.Builder.<SpiderWeb>of(SpiderWeb::new, MobCategory.MISC)
                    .noLootTable()
                    .noSave()
                    .noSummon()
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(5)
                    .build(SPIDER_WEB)
    );



    public static void init(){
        Spidy.LOGGER.info("Registered Entities");
    }

    private static ResourceKey<EntityType<?>> RegistryKey(String string) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Spidy.MOD_ID,string));
    }
}
