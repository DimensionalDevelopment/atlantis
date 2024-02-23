package com.mystic.atlantis.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class PushBubbleStreamParticle extends TextureSheetParticle {
   /**
    * The angle, in radians, of the horizontal acceleration of the particle.
    */
   private float accelerationAngle;

   private Direction direction;

   PushBubbleStreamParticle(ClientLevel clientWorld, double x, double y, double z, Direction direction) {
      super(clientWorld, x, y, z);
      this.direction = direction;
      this.lifetime = (int)(Math.random() * 60.0D) + 30;
      this.hasPhysics = false;

      Vector3f vec = direction.step();
      vec.mul(0.5f);

      this.setParticleSpeed(vec.x(), vec.y(), vec.z());

      this.setSize(0.02F, 0.02F);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
      this.gravity = 0.002F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         float f = 0.6F;

         switch (direction.getAxis()) {
            case Y -> {
               this.xd += f * Mth.cos(this.accelerationAngle);
               this.zd += f * Mth.sin(this.accelerationAngle);
               this.xd *= 0.7F;
               this.zd *= 0.7F;
            }
            case X -> {
               this.yd += f * Mth.cos(this.accelerationAngle);
               this.zd += f * Mth.sin(this.accelerationAngle);
               this.yd *= 0.7F;
               this.zd *= 0.7F;
            }
            case Z -> {
               this.xd += f * Mth.cos(this.accelerationAngle);
               this.yd += f * Mth.sin(this.accelerationAngle);
               xd *= 0.7F;
               yd *= 0.7F;
            }

         }
         this.move(this.xd, this.yd, this.zd);
         if (!this.level.getFluidState(new BlockPos((int) this.x, (int) this.y, (int) this.z)).is(FluidTags.WATER) || this.onGround) {
            this.remove();
         }

         this.accelerationAngle = (float)((double)this.accelerationAngle + ((direction.getAxis().equals(Direction.AxisDirection.NEGATIVE) ? 0.08D : -0.08D)));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteProvider;

      public Factory(SpriteSet spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
         PushBubbleStreamParticle pushBubbleStreamParticle = new PushBubbleStreamParticle(clientWorld, d, e, f, Direction.from3DDataValue((int) g));
         pushBubbleStreamParticle.pickSprite(this.spriteProvider);
         return pushBubbleStreamParticle;
      }
   }
}
