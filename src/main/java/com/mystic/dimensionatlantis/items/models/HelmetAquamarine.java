package com.mystic.dimensionatlantis.items.models;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class HelmetAquamarine extends ModelBiped {
	    public ModelRenderer helm_top;
	    public ModelRenderer fore_head_guard;
	    public ModelRenderer wing_top_right;
	    public ModelRenderer wing_top_left;
	    public ModelRenderer wing_bottom_right;
	    public ModelRenderer wing_bottom_left;
	    public ModelRenderer helm_left;
	    public ModelRenderer helm_right;
	    public ModelRenderer bar_1_top;
	    public ModelRenderer visor_bar;
	    public ModelRenderer visor_crown;
	    public ModelRenderer visor_right;
	    public ModelRenderer visor_left;
	    public ModelRenderer bar_2;
	    public ModelRenderer bar_3;
	    public ModelRenderer bar_4;
	    public ModelRenderer bar_5;
	    public ModelRenderer thick_end_bar;
	    public ModelRenderer bar_6_bottom;

	    public HelmetAquamarine() {
	        this.textureWidth = 64;
	        this.textureHeight = 64;
	        this.bar_4 = new ModelRenderer(this, 0, 0);
	        this.bar_4.setRotationPoint(0.0F, 0.6F, 0.6F);
	        this.bar_4.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
	        this.bar_3 = new ModelRenderer(this, 0, 0);
	        this.bar_3.setRotationPoint(0.0F, 0.6F, 0.6F);
	        this.bar_3.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
	        this.helm_top = new ModelRenderer(this, 0, 0);
	        this.helm_top.setRotationPoint(0.0F, -9.0F, 0.0F);
	        this.helm_top.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F);
	        this.wing_top_left = new ModelRenderer(this, 0, 0);
	        this.wing_top_left.setRotationPoint(-5.0F, -6.0F, 1.0F);
	        this.wing_top_left.addBox(-4.0F, 0.0F, 0.0F, 10, 1, 3, 0.0F);
	        this.setRotateAngle(wing_top_left, 1.7453292519943295F, 1.5707963267948966F, 0.0F);
	        this.wing_bottom_left = new ModelRenderer(this, 0, 0);
	        this.wing_bottom_left.setRotationPoint(-5.5F, 0.0F, 1.0F);
	        this.wing_bottom_left.addBox(-4.0F, 0.0F, 0.0F, 10, 1, 3, 0.0F);
	        this.setRotateAngle(wing_bottom_left, 1.3962634015954636F, 1.5707963267948966F, 0.0F);
	        this.thick_end_bar = new ModelRenderer(this, 0, 0);
	        this.thick_end_bar.setRotationPoint(0.0F, 1.4F, 1.4F);
	        this.thick_end_bar.addBox(-4.0F, 0.0F, 0.0F, 8, 2, 3, 0.0F);
	        this.fore_head_guard = new ModelRenderer(this, 0, 0);
	        this.fore_head_guard.setRotationPoint(0.0F, -6.0F, -5.0F);
	        this.fore_head_guard.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
	        this.setRotateAngle(fore_head_guard, 1.3962634015954636F, 0.0F, 0.0F);
	        this.wing_bottom_right = new ModelRenderer(this, 0, 0);
	        this.wing_bottom_right.setRotationPoint(4.5F, 0.0F, 0.0F);
	        this.wing_bottom_right.addBox(-5.0F, 0.0F, 0.0F, 10, 1, 3, 0.0F);
	        this.setRotateAngle(wing_bottom_right, 1.7453292519943295F, 1.5707963267948966F, 0.0F);
	        this.helm_left = new ModelRenderer(this, 0, 0);
	        this.helm_left.setRotationPoint(-4.5F, -3.5F, 0.0F);
	        this.helm_left.addBox(-4.0F, 0.0F, -4.0F, 9, 1, 8, 0.0F);
	        this.setRotateAngle(helm_left, 1.5707963267948966F, 1.5707963267948966F, 0.0F);
	        this.helm_right = new ModelRenderer(this, 0, 0);
	        this.helm_right.setRotationPoint(3.5F, -3.5F, 0.0F);
	        this.helm_right.addBox(-4.0F, 0.0F, -4.0F, 9, 1, 8, 0.0F);
	        this.setRotateAngle(helm_right, 1.5707963267948966F, 1.5707963267948966F, 0.0F);
	        this.bar_1_top = new ModelRenderer(this, 0, 0);
	        this.bar_1_top.setRotationPoint(0.0F, -8.0F, 4.0F);
	        this.bar_1_top.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
	        this.setRotateAngle(bar_1_top, -0.7853981633974483F, 0.0F, 0.0F);
	        this.visor_bar = new ModelRenderer(this, 0, 0);
	        this.visor_bar.setRotationPoint(0.0F, 0.5F, -2.7F);
	        this.visor_bar.addBox(-4.0F, 0.0F, -1.0F, 8, 1, 1, 0.0F);
	        this.setRotateAngle(visor_bar, 0.17453292519943295F, 0.0F, 0.0F);
	        this.visor_crown = new ModelRenderer(this, 0, 0);
	        this.visor_crown.setRotationPoint(-0.04F, -1.0F, 4.5F);
	        this.visor_crown.addBox(-3.0F, 0.0F, -3.0F, 6, 1, 6, 0.0F);
	        this.setRotateAngle(visor_crown, -0.45378560551852565F, 0.7504915783575618F, -0.33161255787892263F);
	        this.bar_2 = new ModelRenderer(this, 0, 0);
	        this.bar_2.setRotationPoint(0.0F, 0.6F, 0.6F);
	        this.bar_2.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
	        this.bar_6_bottom = new ModelRenderer(this, 0, 0);
	        this.bar_6_bottom.setRotationPoint(0.0F, 0.6F, 0.6F);
	        this.bar_6_bottom.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
	        this.wing_top_right = new ModelRenderer(this, 0, 0);
	        this.wing_top_right.setRotationPoint(4.0F, -6.0F, 0.0F);
	        this.wing_top_right.addBox(-5.0F, 0.0F, 0.0F, 10, 1, 3, 0.0F);
	        this.setRotateAngle(wing_top_right, 1.3962634015954636F, 1.5707963267948966F, 0.0F);
	        this.visor_right = new ModelRenderer(this, 0, 0);
	        this.visor_right.setRotationPoint(4.6F, 0.8F, -2.6F);
	        this.visor_right.addBox(-4.9F, 0.0F, -2.0F, 5, 1, 6, 0.0F);
	        this.setRotateAngle(visor_right, 0.0F, -0.20943951023931953F, 0.7853981633974483F);
	        this.visor_left = new ModelRenderer(this, 0, 0);
	        this.visor_left.setRotationPoint(-1.3F, -2.5F, -3.6F);
	        this.visor_left.addBox(-5.0F, 0.0F, -2.0F, 5, 1, 6, 0.0F);
	        this.setRotateAngle(visor_left, 0.0F, 0.20943951023931953F, -0.7853981633974483F);
	        this.bar_5 = new ModelRenderer(this, 0, 0);
	        this.bar_5.setRotationPoint(0.0F, 0.6F, 0.6F);
	        this.bar_5.addBox(-4.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
	        this.bar_3.addChild(this.bar_4);
	        this.bar_2.addChild(this.bar_3);
	        this.bar_5.addChild(this.thick_end_bar);
	        this.fore_head_guard.addChild(this.visor_bar);
	        this.visor_bar.addChild(this.visor_crown);
	        this.bar_1_top.addChild(this.bar_2);
	        this.bar_5.addChild(this.bar_6_bottom);
	        this.visor_bar.addChild(this.visor_right);
	        this.visor_bar.addChild(this.visor_left);
	        this.bar_4.addChild(this.bar_5);
	        this.bipedHead.addChild(helm_top);
		    this.bipedHead.addChild(fore_head_guard);
		    this.bipedHead.addChild(wing_top_right);
		    this.bipedHead.addChild(wing_top_left);
		    this.bipedHead.addChild(wing_bottom_right);
		    this.bipedHead.addChild(wing_bottom_left);
		    this.bipedHead.addChild(helm_left);
		    this.bipedHead.addChild(helm_right);
		    this.bipedHead.addChild(bar_1_top);
		    this.bipedHead.addChild(visor_bar);
		    this.bipedHead.addChild(visor_crown);
		    this.bipedHead.addChild(visor_right);
		    this.bipedHead.addChild(visor_left);
		    this.bipedHead.addChild(thick_end_bar);
		    this.bipedHead.addChild(bar_6_bottom);
	    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
    	super.render(entity, f, f1, f2, f3, f4, f5);
     }

    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
