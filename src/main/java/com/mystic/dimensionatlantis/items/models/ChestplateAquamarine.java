package com.mystic.dimensionatlantis.items.models;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ChestplateAquamarine extends ModelBiped {
    public ModelRenderer shape41;
    public ModelRenderer shape41_1;
    public ModelRenderer shape41_2;
    public ModelRenderer shape41_3;
    public ModelRenderer shoulder;
    public ModelRenderer shoulder_1;
    public ModelRenderer shape41_4;
    public ModelRenderer shape41_5;
    public ModelRenderer shape53;
    public ModelRenderer shape52;
    public ModelRenderer shape56;
    public ModelRenderer shape56_1;
    public ModelRenderer shape56_2;
    public ModelRenderer shape53_1;
    public ModelRenderer shape52_1;
    public ModelRenderer shape56_3;
    public ModelRenderer shape56_4;
    public ModelRenderer shape56_5;

    public ChestplateAquamarine() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shoulder = new ModelRenderer(this, 0, 0);
        this.shoulder.setRotationPoint(5.2F, -1.3F, 0.0F);
        this.shoulder.addBox(-0.3F, -1.2999999999999998F, -2.0F, 3, 3, 4, 0.0F);
        this.shape53 = new ModelRenderer(this, 0, 0);
        this.shape53.setRotationPoint(2.8F, 1.0F, 0.0F);
        this.shape53.addBox(-3.0F, 0.0F, -3.0F, 4, 1, 6, 0.0F);
        this.shape41 = new ModelRenderer(this, 0, 0);
        this.shape41.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.shape41.addBox(-5.0F, -3.0F, -4.0F, 10, 4, 8, 0.0F);
        this.shape56 = new ModelRenderer(this, 0, 0);
        this.shape56.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.shape56.addBox(0.0F, -3.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape56, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape41_5 = new ModelRenderer(this, 0, 0);
        this.shape41_5.setRotationPoint(0.0F, 3.0F, 2.2F);
        this.shape41_5.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 1, 0.0F);
        this.shape56_5 = new ModelRenderer(this, 0, 0);
        this.shape56_5.setRotationPoint(3.0F, -2.2F, 0.0F);
        this.shape56_5.addBox(-0.4F, 0.0F, -3.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(shape56_5, 0.0F, 0.0F, 1.1344640137963142F);
        this.shape53_1 = new ModelRenderer(this, 0, 0);
        this.shape53_1.setRotationPoint(2.8F, 1.0F, 0.0F);
        this.shape53_1.addBox(-3.0F, 0.0F, -3.0F, 4, 1, 6, 0.0F);
        this.shape56_2 = new ModelRenderer(this, 0, 0);
        this.shape56_2.setRotationPoint(3.0F, -2.2F, 0.0F);
        this.shape56_2.addBox(-0.4F, 0.0F, -3.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(shape56_2, 0.0F, 0.0F, 1.1344640137963142F);
        this.shoulder_1 = new ModelRenderer(this, 0, 0);
        this.shoulder_1.setRotationPoint(-5.0F, -1.4F, 0.0F);
        this.shoulder_1.addBox(-0.4F, -1.2F, -2.0F, 3, 3, 4, 0.0F);
        this.setRotateAngle(shoulder_1, 0.0F, 3.141592653589793F, 0.0F);
        this.shape52 = new ModelRenderer(this, 0, 0);
        this.shape52.setRotationPoint(0.2F, -1.4F, 0.0F);
        this.shape52.addBox(0.0F, 1.0F, -2.0F, 3, 2, 4, 0.0F);
        this.shape41_1 = new ModelRenderer(this, 0, 0);
        this.shape41_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.shape41_1.addBox(-3.95F, 0.0F, 2.0F, 8, 4, 6, 0.0F);
        this.setRotateAngle(shape41_1, 0.0F, -0.009599310885968812F, 0.0F);
        this.shape56_3 = new ModelRenderer(this, 0, 0);
        this.shape56_3.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.shape56_3.addBox(0.0F, -3.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape56_3, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape56_4 = new ModelRenderer(this, 0, 0);
        this.shape56_4.setRotationPoint(-0.6F, 0.9F, 2.5F);
        this.shape56_4.addBox(-0.2F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape56_4, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape41_3 = new ModelRenderer(this, 0, 0);
        this.shape41_3.setRotationPoint(-4.6F, 0.0F, -4.0F);
        this.shape41_3.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.shape41_4 = new ModelRenderer(this, 0, 0);
        this.shape41_4.setRotationPoint(0.0F, 4.0F, 2.0F);
        this.shape41_4.addBox(-3.95F, 0.0F, 1.0F, 8, 4, 4, 0.0F);
        this.shape41_2 = new ModelRenderer(this, 0, 0);
        this.shape41_2.setRotationPoint(-0.3F, 0.0F, -4.0F);
        this.shape41_2.addBox(1.05F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
        this.shape56_1 = new ModelRenderer(this, 0, 0);
        this.shape56_1.setRotationPoint(-0.6F, 0.9F, 2.5F);
        this.shape56_1.addBox(-0.2F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape56_1, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape52_1 = new ModelRenderer(this, 0, 0);
        this.shape52_1.setRotationPoint(0.2F, -1.4F, 0.0F);
        this.shape52_1.addBox(0.0F, 0.8F, -2.0F, 3, 2, 4, 0.0F);
        this.shape41.addChild(this.shoulder);
        this.shoulder.addChild(this.shape53);
        this.shoulder.addChild(this.shape56);
        this.shape41_1.addChild(this.shape41_5);
        this.shoulder_1.addChild(this.shape56_5);
        this.shoulder_1.addChild(this.shape53_1);
        this.shoulder.addChild(this.shape56_2);
        this.shape41.addChild(this.shoulder_1);
        this.shoulder.addChild(this.shape52);
        this.shape41.addChild(this.shape41_1);
        this.shoulder_1.addChild(this.shape56_3);
        this.shoulder_1.addChild(this.shape56_4);
        this.shape41.addChild(this.shape41_3);
        this.shape41_1.addChild(this.shape41_4);
        this.shape41.addChild(this.shape41_2);
        this.shoulder.addChild(this.shape56_1);
        this.shoulder_1.addChild(this.shape52_1);
        this.bipedBody.addChild(shape41);
        this.bipedBody.addChild(shape41_1);
        this.bipedBody.addChild(shape41_2);
        this.bipedBody.addChild(shape41_3);
        this.bipedBody.addChild(shoulder);
        this.bipedBody.addChild(shoulder_1);
        this.bipedBody.addChild(shape41_4);
        this.bipedBody.addChild(shape41_5);
        this.bipedBody.addChild(shape53);
        this.bipedBody.addChild(shape52);
        this.bipedBody.addChild(shape56);
        this.bipedBody.addChild(shape56_1);
        this.bipedBody.addChild(shape56_2);
        this.bipedBody.addChild(shape53_1);
        this.bipedBody.addChild(shape52_1);
        this.bipedBody.addChild(shape56_3);
        this.bipedBody.addChild(shape56_4);
        this.bipedBody.addChild(shape56_5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
