package me.codecraft.items;


import me.codecraft.entity.SpiderWeb;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SpiderShoter extends Item {
    public SpiderShoter(Settings settings) {
        super((Properties) settings);
    }



    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack shooterStack = player.getItemInHand(interactionHand);

        ItemStack cobweb = player.getInventory().getItem(1);
        if (cobweb.is(Items.COBWEB)){
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
            if (level instanceof ServerLevel serverLevel) {
                //                int j = (int)(EnchantmentHelper.getFishingTimeReduction(serverLevel, shooterStack, player) * 20.0f);
//                int k = EnchantmentHelper.getFishingLuckBonus(serverLevel, shooterStack, player);
                Projectile.spawnProjectile(new SpiderWeb(player,level), serverLevel, shooterStack);
            }
            shooterStack.causeUseVibration(player, GameEvent.ITEM_INTERACT_START);
            shooterStack.hurtAndBreak(1,player,interactionHand);

            cobweb.consume(1,player);
        }


        return InteractionResult.SUCCESS;
    }

//    static enum SpiderShooterState {
//        FLYING,
//        HOOKED_IN
//    }

}
