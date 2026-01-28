package me.codecraft.client.render.spider_web;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.codecraft.Spidy;
import me.codecraft.entity.SpiderWeb;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.core.Direction.Axis.X;


@Environment(EnvType.CLIENT)
public class SpiderWebRender extends EntityRenderer<SpiderWeb,SpiderWebRenderState> {

    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(Spidy.MOD_ID,"textures/entity/spider_web/spider_web.png");
    private static final RenderType RENDER_TYPE = RenderTypes.entityCutout(TEXTURE_LOCATION);


    public static Identifier getTextureLocation() {
        return TEXTURE_LOCATION;
    }

    public SpiderWebRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(SpiderWebRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(cameraRenderState.orientation);
        submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, vertexConsumer) -> {
            SpiderWebRender.vertex(vertexConsumer, pose, entityRenderState.lightCoords, 0.0f, 0, 0, 1);
            SpiderWebRender.vertex(vertexConsumer, pose, entityRenderState.lightCoords, 1.0f, 0, 1, 1);
            SpiderWebRender.vertex(vertexConsumer, pose, entityRenderState.lightCoords, 1.0f, 1, 1, 0);
            SpiderWebRender.vertex(vertexConsumer, pose, entityRenderState.lightCoords, 0.0f, 1, 0, 0);
        });
        poseStack.popPose();

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            double rx = entityRenderState.x;
            double ry = entityRenderState.y;
            double rz = entityRenderState.z;

            Vec3 test = new Vec3(rx,ry,rz);


            entityRenderState.lineOriginOffset = new Vec3(x, y, z).subtract(test);
        }
        int segments = 16;
        float lineWidth = Minecraft.getInstance().getWindow().getAppropriateLineWidth();
        float f = (float)entityRenderState.lineOriginOffset.x;
        float g = (float)entityRenderState.lineOriginOffset.y;
        float h = (float)entityRenderState.lineOriginOffset.z;

        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.lines(), (pose, vertexConsumer) -> {
            float prevX = 0, prevY = 0, prevZ = 0;
            for (int i = 1; i <= segments; i++) {
                float t = (float)i / segments;
                float x = Mth.lerp(t, 0, f);
                float y = Mth.lerp(t, 0, g) + (t * t + t) * 0.5f * 0.25f; // quadratic sag, tweak as needed
                float z = Mth.lerp(t, 0, h);

                vertexConsumer.addVertex(pose, prevX, prevY, prevZ)
                        .setColor(0xFF000000)
                        .setNormal(pose, 0, 1, 0)
                        .setLineWidth(lineWidth);
                vertexConsumer.addVertex(pose, x, y, z)
                        .setColor(0xFF000000)
                        .setNormal(pose, 0, 1, 0)
                        .setLineWidth(lineWidth);

                prevX = x;
                prevY = y;
                prevZ = z;
            }
        });
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    public SpiderWebRenderState createRenderState() {
        return new SpiderWebRenderState();
    }

    private static float fraction(int i, int j) {
        return (float)i / (float)j;
    }


    private static void stringVertex(float f, float g, float h, VertexConsumer vertexConsumer, PoseStack.Pose pose, float i, float j, float k) {
        float l = f * i;
        float m = g * (i * i + i) * 0.5f + 0.25f;
        float n = h * i;
        float o = f * j - l;
        float p = g * (j * j + j) * 0.5f + 0.25f - m;
        float q = h * j - n;
        float r = Mth.sqrt(o * o + p * p + q * q);
        vertexConsumer.addVertex(pose, l, m, n).setColor(-16777216).setNormal(pose, o /= r, p /= r, q /= r).setLineWidth(k);
    }

    private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, int i, float f, int j, int k, int l) {
        vertexConsumer.addVertex(pose, f - 0.5f, (float)j - 0.5f, 0.0f).setColor(-1).setUv(k, l).setOverlay(OverlayTexture.NO_OVERLAY).setLight(i).setNormal(pose, 0.0f, 1.0f, 0.0f);
    }
}
