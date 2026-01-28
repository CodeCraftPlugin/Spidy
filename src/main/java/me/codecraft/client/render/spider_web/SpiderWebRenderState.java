package me.codecraft.client.render.spider_web;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SpiderWebRenderState extends EntityRenderState {


    public Vec3 lineOriginOffset = Vec3.ZERO;
    @Nullable
    public BlockState blockState;

    public SpiderWebRenderState() {
    }
}
