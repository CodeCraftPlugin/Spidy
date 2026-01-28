package me.codecraft.client;

import me.codecraft.Spidy;
import me.codecraft.client.render.spider_web.SpiderWebModel;
import me.codecraft.client.render.spider_web.SpiderWebRender;
import me.codecraft.entity.MainEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class SpidyClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(SpiderWebModel.SPIDER_WEB, SpiderWebModel::getTexturedModelData);
        EntityRenderers.register(MainEntity.SPIDER_WEB_ENTITY, SpiderWebRender::new);


        Spidy.LOGGER.info("Client Init");
    }
}
