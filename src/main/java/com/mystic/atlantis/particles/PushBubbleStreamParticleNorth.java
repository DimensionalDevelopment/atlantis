package com.mystic.atlantis.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class PushBubbleStreamParticleNorth extends TextureSheetParticle {

   private final Direction direction;

   PushBubbleStreamParticleNorth(ClientLevel clientWorld, double x, double y, double z, Direction direction) {
      super(clientWorld, x, y, z);
      this.direction = direction;
      this.lifetime = (int)(40.0 / (Math.random() * 0.8 + 0.2));
      this.hasPhysics = false;

      Vector3f vec = direction.step();

      // Set initial directional velocity based on the direction
      this.xd = vec.x() * 0.2 + (Math.random() * 0.02 - 0.01);  // Small randomization for natural look
      this.yd = vec.y() * 0.2 + (Math.random() * 0.02 - 0.01);
      this.zd = vec.z() * 0.2 + (Math.random() * 0.02 - 0.01);

      this.setSize(0.02F, 0.02F);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
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
         // Adjust velocity slightly each tick to continue movement in push direction
         Vector3f vec = direction.step();
         float speedFactor = 0.02F;  // Consistent speed factor
         this.xd = vec.x() * speedFactor;
         this.yd = vec.y() * speedFactor;
         this.zd = vec.z() * speedFactor;

         // Move in the current direction
         this.move(this.xd, this.yd, this.zd);

         // Check if particle is still in water, if not remove
         if (!this.level.getFluidState(new BlockPos((int) this.x, (int) this.y, (int) this.z)).is(FluidTags.WATER)) {
            this.remove();
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteProvider;

      public Factory(SpriteSet spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
         PushBubbleStreamParticleNorth pushBubbleStreamParticle = new PushBubbleStreamParticleNorth(clientWorld, d, e, f, Direction.NORTH);
         pushBubbleStreamParticle.pickSprite(this.spriteProvider);
         return pushBubbleStreamParticle;
      }
   }
}
