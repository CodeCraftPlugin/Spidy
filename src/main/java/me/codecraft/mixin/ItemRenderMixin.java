package me.codecraft.mixin;


import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
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
