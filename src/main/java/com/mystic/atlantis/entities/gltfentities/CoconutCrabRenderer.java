package com.mystic.atlantis.entities.gltfentities;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mystic.atlantis.util.Reference;
import com.timlee9024.mcgltf.IGltfModelReceiver;
import com.timlee9024.mcgltf.MCglTF;
import com.timlee9024.mcgltf.RenderedGltfModel;
import com.timlee9024.mcgltf.RenderedGltfScene;
import com.timlee9024.mcgltf.animation.GltfAnimationCreator;
import com.timlee9024.mcgltf.animation.InterpolatedChannel;
import de.javagl.jgltf.model.AnimationModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class CoconutCrabRenderer extends EntityRenderer<CoconutCrabEntity> implements IGltfModelReceiver {

	protected RenderedGltfScene renderedScene;
	
	protected List<List<InterpolatedChannel>> animations;
	
	public CoconutCrabRenderer(EntityRendererProvider.Context p_174008_) {
		super(p_174008_);
	}

	@Override
	public ResourceLocation getModelLocation() {
		return new ResourceLocation(Reference.MODID, "models/entity/coconut_crab/coconut_crab.gltf");
	}

	@Override
	public void onReceiveSharedModel(RenderedGltfModel renderedModel) {
		renderedScene = renderedModel.renderedGltfScenes.get(0);
		List<AnimationModel> animationModels = renderedModel.gltfModel.getAnimationModels();
		animations = new ArrayList<>(animationModels.size());
		for(AnimationModel animationModel : animationModels) {
			animations.add(GltfAnimationCreator.createGltfAnimation(animationModel));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(CoconutCrabEntity p_114482_) {
		return null;
	}

	private void getAnimationIndex(CoconutCrabEntity coconutCrabEntity, float p_114487_, int index) {
		float time = (coconutCrabEntity.level.getGameTime() + p_114487_) / 20;
		animations.get(index).forEach((channel) -> {
		 	  float key = channel.getKeys()[channel.getKeys().length - 1];
			  channel.update(time % key);
		});
	}

	@Override
	public void render(CoconutCrabEntity coconutCrabEntity, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
		if (coconutCrabEntity.isFallFlying()) {
			getAnimationIndex(coconutCrabEntity, p_114487_, 4);
		} else if (!coconutCrabEntity.isAlive()) {
			getAnimationIndex(coconutCrabEntity, p_114487_, 3);
		} else if (coconutCrabEntity.isMovingSlowly()) {
			getAnimationIndex(coconutCrabEntity, p_114487_, 2);
		} else if (coconutCrabEntity.isAngry()) {
			getAnimationIndex(coconutCrabEntity, p_114487_, 5);
		} else if (coconutCrabEntity.hurtMarked) {
			getAnimationIndex(coconutCrabEntity, p_114487_, 0);
		} else if (!coconutCrabEntity.isMovingSlowly()) {
			getAnimationIndex(coconutCrabEntity, p_114487_, 1);
		}

		int currentVAO = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);
		int currentArrayBuffer = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
		int currentElementArrayBuffer = GL11.glGetInteger(GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING);
		
		boolean currentCullFace = GL11.glGetBoolean(GL11.GL_CULL_FACE);
		
		boolean currentDepthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
		boolean currentBlend = GL11.glGetBoolean(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		p_114488_.pushPose();
		p_114488_.mulPose(new Quaternion(0.0F, Mth.rotLerp(p_114487_, coconutCrabEntity.yBodyRotO, coconutCrabEntity.yBodyRot), 0.0F, true));
		RenderedGltfModel.CURRENT_POSE = p_114488_.last().pose();
		RenderedGltfModel.CURRENT_NORMAL = p_114488_.last().normal();
		p_114488_.popPose();
		
		GL30.glVertexAttribI2i(RenderedGltfModel.vaUV2, p_114490_ & '\uffff', p_114490_ >> 16 & '\uffff');
		
		if(MCglTF.getInstance().isShaderModActive()) {
			renderedScene.renderForShaderMod();
		}
		else {
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			int currentTexture2 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, MCglTF.getInstance().getLightTexture().getId());
			
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			int currentTexture1 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			int currentTexture0 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
			
			renderedScene.renderForVanilla();
			
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture2);
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture1);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture0);
		}
		
		GL30.glVertexAttribI2i(RenderedGltfModel.vaUV2, 0, 0);
		
		if(!currentDepthTest) GL11.glDisable(GL11.GL_DEPTH_TEST);
		if(!currentBlend) GL11.glDisable(GL11.GL_BLEND);
		
		if(currentCullFace) GL11.glEnable(GL11.GL_CULL_FACE);
		else GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL30.glBindVertexArray(currentVAO);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, currentArrayBuffer);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, currentElementArrayBuffer);
		RenderedGltfModel.nodeGlobalTransformLookup.clear();
		super.render(coconutCrabEntity, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
	}

}