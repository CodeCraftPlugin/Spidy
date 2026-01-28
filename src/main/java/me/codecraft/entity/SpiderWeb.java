package me.codecraft.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class SpiderWeb extends ThrowableProjectile {

    private static boolean hasJumped = false;
    private SpiderWebState spiderWebState = SpiderWebState.FLYING;
    private final RandomSource syncronizedRandom = RandomSource.create();
    private final InterpolationHandler interpolationHandler = new InterpolationHandler(this);

    public SpiderWeb(EntityType<? extends SpiderWeb> type, Level level) {
        super(type, level);
    }

    public SpiderWeb(Player player, Level level) {
        super(MainEntity.SPIDER_WEB_ENTITY, level);
        // Get look direction from player's rotation

        this.setOwner(player);
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        float xDir = -Mth.sin(yaw * ((float)Math.PI / 180)) * Mth.cos(pitch * ((float)Math.PI / 180));
        float yDir = -Mth.sin(pitch * ((float)Math.PI / 180));
        float zDir = Mth.cos(yaw * ((float)Math.PI / 180)) * Mth.cos(pitch * ((float)Math.PI / 180));

        Vec3 direction = new Vec3(xDir, yDir, zDir).normalize();
        double speed = 1.5; // Adjust for desired launch velocity
        this.setDeltaMovement(direction.scale(speed));

        // Set initial position slightly in front of player
        double px = player.getX() + direction.x * 0.5;
        double py = player.getEyeY() + direction.y * 0.5;
        double pz = player.getZ() + direction.z * 0.5;
        this.snapTo(px, py, pz, yaw, pitch);

        this.setYRot(yaw);
        this.setXRot(pitch);
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected boolean shouldBounceOnWorldBorder() {
        return true;
    }
    @Override
    public boolean shouldRenderAtSqrDistance(double d) {
        double e = 64.0;
        return d < 4096.0;
    }

    @Override
    public void tick() {
        this.syncronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level().getGameTime());
        this.getInterpolation().interpolate();
        Player player = (Player) this.getOwner();
        if (player == null) {
            this.discard();
            return;
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.applyEffectsFromBlocks();
        this.updateRotation();

        if (this.spiderWebState == SpiderWebState.HOOKED_IN_GROUND){
            System.out.println("Loaded");
            this.Pull1();
        }

        if (this.isInLiquid()){
            this.discard();
        }
        this.setDeltaMovement(this.getDeltaMovement().scale(0.92));
        this.reapplyPosition();
        super.tick();
    }


    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.spiderWebState = SpiderWebState.HOOKED_IN_GROUND; // Correct state for ground hit
        this.setDeltaMovement(Vec3.ZERO);
        if (!hasJumped){
            double jumpBoost = 0.3; // Adjust for desired jump height
            this.getPlayerOwner().push(0, jumpBoost, 0);
            hasJumped = true;
        }

        super.onHitBlock(blockHitResult);
    }

    private void Pull() {
        this.spiderWebState  =SpiderWebState.HOOKED_IN_GROUND;
        this.setDeltaMovement(Vec3.ZERO);
        
        Player player  = this.getPlayerOwner();
        Vec3 webPos = this.position();
        Vec3 playerPos = player.position();
        double distance = webPos.distanceTo(playerPos);
        // Stop pulling if player collides with a block or is close enough
        if (player.onGround() || player.horizontalCollision || distance < 1.0) {
            player.setInvulnerable(false);
            this.discard();
        } else {
            player.setInvulnerable(true);
            Vec3 direction = webPos.subtract(playerPos).normalize();
            double pullStrength = 0.5;
            player.setDeltaMovement(direction.scale(pullStrength));
        }


    }

    private void Pull1() {
        Player player = this.getPlayerOwner();
        if (player == null) {
            this.discard();
            return;
        }
        Vec3 webPos = this.position();
        Vec3 playerPos = player.position();
        double distance = webPos.distanceTo(playerPos);

        // Pull the player towards the web
        if (distance >= 1.0 && !player.horizontalCollision) {
            Vec3 direction = webPos.subtract(playerPos).normalize();
            double pullStrength = 0.5;
            player.push(direction.x * pullStrength, direction.y * pullStrength, direction.z * pullStrength);
        }

        // Discard if player is close enough or stuck
        if (distance < 1.0 || player.horizontalCollision) {
            this.discard();
        }
    }

    @Override
    public @Nullable InterpolationHandler getInterpolation() {
        return interpolationHandler;
    }

    public @Nullable Player getPlayerOwner() {
        Player player;
        Entity entity = this.getOwner();
        return entity instanceof Player ? (player = (Player)entity) : null;
    }

    private enum SpiderWebState{
            FLYING,
            HOOKED_IN_ENTITY,
            HOOKED_IN_GROUND,
    }


}
