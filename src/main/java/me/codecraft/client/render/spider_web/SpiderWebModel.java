package me.codecraft.client.render.spider_web;

import me.codecraft.Spidy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.CustomModelData;


@Environment(EnvType.CLIENT)
public class SpiderWebModel extends EntityModel<SpiderWebRenderState> {


    public static final ModelLayerLocation SPIDER_WEB =  new ModelLayerLocation(Identifier.fromNamespaceAndPath(Spidy.MOD_ID, "stick_torch"), "main");
    private final ModelPart spider_web;
    protected SpiderWebModel(ModelPart modelPart) {
        super(modelPart);
        this.spider_web = root.getChild("spider_web");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition  = meshDefinition.getRoot();
        PartDefinition partMain = partDefinition.addOrReplaceChild("spider_web", CubeListBuilder.create().texOffs(0,0).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F,new CubeDeformation(0.0F)),PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition,16,16);
    }

}
