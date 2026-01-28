package me.codecraft.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRenderMixin {

//    @Shadow
//    @Final
//    private ItemModels models;
//
//    @Shadow
//    public abstract ItemModelUtils getModels();
//
//
//    @ModifyVariable(
//            method = "renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILint;[ILjava/util/List;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V",
//            at = @At(value = "HEAD"),
//            argsOnly = true
//    )
//    public static ItemDisplayContext test(ItemDisplayContext value, PoseStack poseStack, MultiBufferSource multiBufferSource, int i , int j , int [] is, RenderType renderType, ItemStackRenderState.FoilType foilType){
//
//        if (value == ItemDisplayContext.GUI) {
//            getModels().
//            // Render 2D GUI texture
//        } else {
//            // Render 3D in-hand/world texture
//        }
//        return value;
//    }

}
