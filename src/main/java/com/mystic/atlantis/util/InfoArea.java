package com.mystic.atlantis.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public abstract class InfoArea {
    protected final Rect2i area;

    protected InfoArea(Rect2i area) {
        this.area = area;
    }

    public abstract void draw(GuiGraphics transform);
}