package com.mystic.atlantis.blocks.power.atlanteanstone;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.mystic.atlantis.init.AtlantisEntityInit;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class SodiumPrimedBombBlock extends Entity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(SodiumPrimedBombBlock.class, EntityDataSerializers.INT);
    @Nullable
    private LivingEntity owner;

    public SodiumPrimedBombBlock(EntityType<? extends SodiumPrimedBombBlock> type, Level level) {
        super(type, level);
    }

    public SodiumPrimedBombBlock(Level level, double targetX, double targetY, double targetZ, @Nullable LivingEntity owner) {
        this(AtlantisEntityInit.BOMB.get(), level);
        this.setPos(targetX, targetY, targetZ);
        double randOffset = level.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin(randOffset) * 0.02, 0.2f, -Math.cos(randOffset) * 0.02);
        this.setFuse(80);
        this.xo = targetX;
        this.yo = targetY;
        this.zo = targetZ;
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    @NotNull
    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }
        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);
        if (fuse <= 0) {
            this.discard();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    protected void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625), this.getZ(), 4.0f, this.isUnderWater(), Explosion.BlockInteraction.BREAK);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putShort("Fuse", (short) this.getFuse());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.setFuse(nbt.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    @Override
    protected float getEyeHeight(@NotNull Pose curPose, @NotNull EntityDimensions dimensions) {
        return 0.15f;
    }

    public void setFuse(int fuse) {
        this.entityData.set(DATA_FUSE_ID, fuse);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    @NotNull
    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
