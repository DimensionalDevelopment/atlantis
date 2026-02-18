package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.init.ToolInit;

import com.mystic.atlantis.items.HammerItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrichalcumHammer extends HammerItem {
    public OrichalcumHammer() {
        super(ToolInit.ORICHALCUM,5, 5, 5, new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("orichalcum_hammer.description"));
        list.add(Component.translatable("orichalcum_hammer.tooltip"));
    }
}
