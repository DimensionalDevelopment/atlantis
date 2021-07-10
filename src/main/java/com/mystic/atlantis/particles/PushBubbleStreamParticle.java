package com.mystic.atlantis.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class PushBubbleStreamParticle extends SpriteBillboardParticle {
   /**
    * The angle, in radians, of the horizontal acceleration of the particle.
    */
   private float accelerationAngle;

   private Direction direction;

   PushBubbleStreamParticle(ClientWorld clientWorld, double x, double y, double z, Direction direction) {
      super(clientWorld, x, y, z);
      this.direction = direction;
      this.maxAge = (int)(Math.random() * 60.0D) + 30;
      this.collidesWithWorld = false;

      Vec3f vec = direction.getUnitVector();
      vec.scale(0.5f);

      this.setVelocity(vec.getX(), vec.getY(), vec.getZ());

      this.setBoundingBoxSpacing(0.02F, 0.02F);
      this.scale *= this.random.nextFloat() * 0.6F + 0.2F;
      this.gravityStrength = 0.002F;
   }

   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         float f = 0.6F;

         switch (direction.getAxis()) {
            case Y -> {
               this.velocityX += f * MathHelper.cos(this.accelerationAngle);
               this.velocityZ += f * MathHelper.sin(this.accelerationAngle);
               this.velocityX *= 0.7F;
               this.velocityZ *= 0.7F;
            }
            case X -> {
               this.velocityY += f * MathHelper.cos(this.accelerationAngle);
               this.velocityZ += f * MathHelper.sin(this.accelerationAngle);
               this.velocityY *= 0.7F;
               this.velocityZ *= 0.7F;
            }
            case Z -> {
               this.velocityX += f * MathHelper.cos(this.accelerationAngle);
               this.velocityY += f * MathHelper.sin(this.accelerationAngle);
               velocityX *= 0.7F;
               velocityY *= 0.7F;
            }

         }
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         if (!this.world.getFluidState(new BlockPos(this.x, this.y, this.z)).isIn(FluidTags.WATER) || this.onGround) {
            this.markDead();
         }

         this.accelerationAngle = (float)((double)this.accelerationAngle + ((direction.getAxis().equals(Direction.AxisDirection.NEGATIVE) ? 0.08D : -0.08D)));
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
         PushBubbleStreamParticle pushBubbleStreamParticle = new PushBubbleStreamParticle(clientWorld, d, e, f, Direction.byId((int) g));
         pushBubbleStreamParticle.setSprite(this.spriteProvider);
         return pushBubbleStreamParticle;
      }
   }
}
