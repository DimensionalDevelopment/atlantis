package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisItemModelProvider extends ItemModelProvider {
    public AtlantisItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, "atlantis", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_A);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_B);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_C);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_D);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_E);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_F);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_G);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_H);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_I);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_J);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_K);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_L);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_M);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_N);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_O);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_P);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_Q);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_R);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_S);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_T);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_U);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_V);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_W);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_X);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_Y);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_Z);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_0);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_1);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_2);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_3);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_4);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_5);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_6);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_7);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_8);
        item(ItemInit.LINGUISTIC_GLYPH_SCROLL_9);

        for(LinguisticGlyph glyph : LinguisticGlyph.values()) {
            for(DyeColor color : DyeColor.values()) {
                withParent(BlockInit.getLinguisticBlock(glyph, color), glyph);
            }

            withParent(BlockInit.getLinguisticBlock(glyph, null), glyph);
        }

        //        block(BlockInit.WRITING_BLOCK);
        block(BlockInit.ORICHALCUM_BLOCK);

        item(ItemInit.ORICHALCUM_IGNOT);
        item(ItemInit.ORICHALCUM_BLEND);

        item(ItemInit.ORICHALCUM_HELMET);
        item(ItemInit.ORICHALCUM_CHESTPLATE);
        item(ItemInit.ORICHALCUM_LEGGINGS);
        item(ItemInit.ORICHALCUM_BOOTS);
        itemTool(ItemInit.ORICHALCUM_AXE);
        itemTool(ItemInit.ORICHALCUM_PICKAXE);
        itemTool(ItemInit.ORICHALCUM_SHOVEL);
        itemTool(ItemInit.ORICHALCUM_SWORD);
        itemTool(ItemInit.ORICHALCUM_HOE);
    }

    private void itemTool(RegistryObject<Item> tool) {
        getBuilder(tool.getId().getPath())
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", items(tool.getId()));
    }

    private void withParent(RegistryObject<Block> block, LinguisticGlyph glyph) {
        withExistingParent(block.getId().getPath(), block(Atlantis.id("linguistic_" + glyph.name().toLowerCase())));
    }

    private void block(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(), block(block.getId()));
    }

    private void item(RegistryObject<Item> block) {
        try {
            getBuilder(block.getId().getPath())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", items(block.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private ResourceLocation block(ResourceLocation location) {
        return new ResourceLocation(location.getNamespace(), "block/" + location.getPath());
    }


    private ResourceLocation items(ResourceLocation location) {
        return new ResourceLocation(location.getNamespace(), "items/" + location.getPath());
    }
}
