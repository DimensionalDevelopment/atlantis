package com.mystic.atlantis.datagen;

import com.mystic.atlantis.TagsInit;
import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.items.armor.AtlantisArmorSet;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.mystic.atlantis.init.BlockInit.*;

public class AtlantisItemTagsProvider extends ItemTagsProvider {

    public AtlantisItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, BlockTagsProvider blocksGetter, String modid) {
        super(output, lookup, blocksGetter.contentsGetter(), modid, null);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        var sink = tag(TagsInit.Item.CAN_ITEM_SINK);

        tag(ItemTags.FENCES).add(
                ANCIENT_BIRCH.fence().get().asItem(), ANCIENT_ACACIA.fence().get().asItem(), ANCIENT_JUNGLE.fence().get().asItem(),
                ANCIENT_OAK.fence().get().asItem(), ANCIENT_DARK_OAK.fence().get().asItem(), ANCIENT_SPRUCE.fence().get().asItem(),
                ANCIENT_MANGROVE.fence().get().asItem(), ANCIENT_BAMBOO.fence().get().asItem(), ANCIENT_CHERRY.fence().get().asItem(),
                ANCIENT_CRIMSON.fence().get().asItem(), ANCIENT_WARPED.fence().get().asItem(), NYMPH_PLANKS.fence().get().asItem(),
                PALM_PLANKS.fence().get().asItem()
        );

        tag(ItemTags.BUTTONS).add(
                ANCIENT_BIRCH.button().get().asItem(), ANCIENT_ACACIA.button().get().asItem(), ANCIENT_JUNGLE.button().get().asItem(),
                ANCIENT_OAK.button().get().asItem(), ANCIENT_DARK_OAK.button().get().asItem(), ANCIENT_SPRUCE.button().get().asItem(),
                ANCIENT_MANGROVE.button().get().asItem(), ANCIENT_BAMBOO.button().get().asItem(), ANCIENT_CHERRY.button().get().asItem(),
                ANCIENT_CRIMSON.button().get().asItem(), ANCIENT_WARPED.button().get().asItem(), NYMPH_PLANKS.button().get().asItem(),
                PALM_PLANKS.button().get().asItem()
        );

        tag(ItemTags.FENCE_GATES).add(
                ANCIENT_BIRCH.fenceGate().get().asItem(), ANCIENT_ACACIA.fenceGate().get().asItem(), ANCIENT_JUNGLE.fenceGate().get().asItem(),
                ANCIENT_OAK.fenceGate().get().asItem(), ANCIENT_DARK_OAK.fenceGate().get().asItem(), ANCIENT_SPRUCE.fenceGate().get().asItem(),
                ANCIENT_MANGROVE.fenceGate().get().asItem(), ANCIENT_BAMBOO.fenceGate().get().asItem(), ANCIENT_CHERRY.fenceGate().get().asItem(),
                ANCIENT_CRIMSON.fenceGate().get().asItem(), ANCIENT_WARPED.fenceGate().get().asItem(), NYMPH_PLANKS.fenceGate().get().asItem(),
                PALM_PLANKS.fenceGate().get().asItem()
        );

        tag(ItemTags.DOORS).add(
                ANCIENT_BIRCH.door().get().asItem(), ANCIENT_ACACIA.door().get().asItem(), ANCIENT_JUNGLE.door().get().asItem(),
                ANCIENT_OAK.door().get().asItem(), ANCIENT_DARK_OAK.door().get().asItem(), ANCIENT_SPRUCE.door().get().asItem(),
                ANCIENT_MANGROVE.door().get().asItem(), ANCIENT_BAMBOO.door().get().asItem(), ANCIENT_CHERRY.door().get().asItem(),
                ANCIENT_CRIMSON.door().get().asItem(), ANCIENT_WARPED.door().get().asItem(), NYMPH_PLANKS.door().get().asItem(),
                PALM_PLANKS.door().get().asItem()
        );

        tag(ItemTags.PLANKS).add(
                ANCIENT_BIRCH.block().get().asItem(), ANCIENT_ACACIA.block().get().asItem(), ANCIENT_JUNGLE.block().get().asItem(),
                ANCIENT_OAK.block().get().asItem(), ANCIENT_DARK_OAK.block().get().asItem(), ANCIENT_SPRUCE.block().get().asItem(),
                ANCIENT_MANGROVE.block().get().asItem(), ANCIENT_BAMBOO.block().get().asItem(), ANCIENT_CHERRY.block().get().asItem(),
                ANCIENT_CRIMSON.block().get().asItem(), ANCIENT_WARPED.block().get().asItem(), NYMPH_PLANKS.block().get().asItem(),
                PALM_PLANKS.block().get().asItem()
        );

        tag(ItemTags.SIGNS).add(ItemInit.NYMPH_SIGN.get(), ItemInit.PALM_SIGN.get());

        tag(ItemTags.TRAPDOORS).add(
                ANCIENT_BIRCH.trapDoor().get().asItem(), ANCIENT_ACACIA.trapDoor().get().asItem(), ANCIENT_JUNGLE.trapDoor().get().asItem(),
                ANCIENT_OAK.trapDoor().get().asItem(), ANCIENT_DARK_OAK.trapDoor().get().asItem(), ANCIENT_SPRUCE.trapDoor().get().asItem(),
                ANCIENT_MANGROVE.trapDoor().get().asItem(), ANCIENT_BAMBOO.trapDoor().get().asItem(), ANCIENT_CHERRY.trapDoor().get().asItem(),
                ANCIENT_CRIMSON.trapDoor().get().asItem(), ANCIENT_WARPED.trapDoor().get().asItem(), NYMPH_PLANKS.trapDoor().get().asItem(),
                PALM_PLANKS.trapDoor().get().asItem()
        );

        for (BlockType blockType : SEA_GLASS_LIST.values()) tag(ItemTags.WALLS).add(blockType.wall().get().asItem());
        for (BlockType blockType : SEA_GLASS_PATTERNS.values()) tag(ItemTags.WALLS).add(blockType.wall().get().asItem());

        tag(ItemTags.BOATS).add(ItemInit.NYMPH_BOAT.get(), ItemInit.PALM_BOAT.get(), ItemInit.SUBMARINE.get());

        tag(ItemTags.SHOVELS).add(ItemInit.ORICHALCUM_SHOVEL.get(), ItemInit.ORICHALCUM_SHOVEL.get());
        tag(ItemTags.SWORDS).add(ItemInit.ORICHALCUM_SWORD.get(), ItemInit.ORICHALCUM_SWORD.get());
        tag(ItemTags.HOES).add(ItemInit.AQUAMARINE_HOE.get(), ItemInit.ORICHALCUM_HOE.get());
        tag(ItemTags.PICKAXES).add(ItemInit.AQUAMARINE_PICKAXE.get(), ItemInit.ORICHALCUM_PICKAXE.get());
        tag(ItemTags.AXES).add(ItemInit.AQUAMARINE_AXE.get(), ItemInit.ORICHALCUM_AXE.get());

        tag(ItemTags.WOODEN_FENCES).add(
                ANCIENT_BIRCH.fence().get().asItem(), ANCIENT_ACACIA.fence().get().asItem(), ANCIENT_JUNGLE.fence().get().asItem(),
                ANCIENT_OAK.fence().get().asItem(), ANCIENT_DARK_OAK.fence().get().asItem(), ANCIENT_SPRUCE.fence().get().asItem(),
                ANCIENT_MANGROVE.fence().get().asItem(), ANCIENT_BAMBOO.fence().get().asItem(), ANCIENT_CHERRY.fence().get().asItem(),
                ANCIENT_CRIMSON.fence().get().asItem(), ANCIENT_WARPED.fence().get().asItem(), NYMPH_PLANKS.fence().get().asItem(),
                PALM_PLANKS.fence().get().asItem()
        );

        tag(Tags.Items.INGOTS).add(ItemInit.ANCIENT_CUPRUM_INGOT.get(), ItemInit.ORICHALCUM_INGOT.get());
        tag(Tags.Items.GEMS).add(ItemInit.AQUAMARINE_GEM.get());

        tag(ItemTags.WOODEN_DOORS).add(
                ANCIENT_BIRCH.door().get().asItem(), ANCIENT_ACACIA.door().get().asItem(), ANCIENT_JUNGLE.door().get().asItem(),
                ANCIENT_OAK.door().get().asItem(), ANCIENT_DARK_OAK.door().get().asItem(), ANCIENT_SPRUCE.door().get().asItem(),
                ANCIENT_MANGROVE.door().get().asItem(), ANCIENT_BAMBOO.door().get().asItem(), ANCIENT_CHERRY.door().get().asItem(),
                ANCIENT_CRIMSON.door().get().asItem(), ANCIENT_WARPED.door().get().asItem(), NYMPH_PLANKS.door().get().asItem(),
                PALM_PLANKS.door().get().asItem()
        );
        tag(ItemTags.WOODEN_SLABS).add(
                ANCIENT_BIRCH.slab().get().asItem(), ANCIENT_ACACIA.slab().get().asItem(), ANCIENT_JUNGLE.slab().get().asItem(),
                ANCIENT_OAK.slab().get().asItem(), ANCIENT_DARK_OAK.slab().get().asItem(), ANCIENT_SPRUCE.slab().get().asItem(),
                ANCIENT_MANGROVE.slab().get().asItem(), ANCIENT_BAMBOO.slab().get().asItem(), ANCIENT_CHERRY.slab().get().asItem(),
                ANCIENT_CRIMSON.slab().get().asItem(), ANCIENT_WARPED.slab().get().asItem(), NYMPH_PLANKS.slab().get().asItem(),
                PALM_PLANKS.slab().get().asItem()
        );
        tag(ItemTags.WOODEN_STAIRS).add(
                ANCIENT_BIRCH.stairs().get().asItem(), ANCIENT_ACACIA.stairs().get().asItem(), ANCIENT_JUNGLE.stairs().get().asItem(),
                ANCIENT_OAK.stairs().get().asItem(), ANCIENT_DARK_OAK.stairs().get().asItem(), ANCIENT_SPRUCE.stairs().get().asItem(),
                ANCIENT_MANGROVE.stairs().get().asItem(), ANCIENT_BAMBOO.stairs().get().asItem(), ANCIENT_CHERRY.stairs().get().asItem(),
                ANCIENT_CRIMSON.stairs().get().asItem(), ANCIENT_WARPED.stairs().get().asItem(), NYMPH_PLANKS.stairs().get().asItem(),
                PALM_PLANKS.stairs().get().asItem()
        );
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(
                ANCIENT_BIRCH.pressurePlate().get().asItem(), ANCIENT_ACACIA.pressurePlate().get().asItem(), ANCIENT_JUNGLE.pressurePlate().get().asItem(),
                ANCIENT_OAK.pressurePlate().get().asItem(), ANCIENT_DARK_OAK.pressurePlate().get().asItem(), ANCIENT_SPRUCE.pressurePlate().get().asItem(),
                ANCIENT_MANGROVE.pressurePlate().get().asItem(), ANCIENT_BAMBOO.pressurePlate().get().asItem(), ANCIENT_CHERRY.pressurePlate().get().asItem(),
                ANCIENT_CRIMSON.pressurePlate().get().asItem(), ANCIENT_WARPED.pressurePlate().get().asItem(), NYMPH_PLANKS.pressurePlate().get().asItem(),
                PALM_PLANKS.pressurePlate().get().asItem()
        );

        tag(ItemTags.WOODEN_BUTTONS).add(
                ANCIENT_CRIMSON.button().get().asItem(), ANCIENT_CRIMSON.pressurePlate().get().asItem(),
                ANCIENT_WARPED.button().get().asItem(), ANCIENT_WARPED.pressurePlate().get().asItem()
        );

        tag(ItemTags.NON_FLAMMABLE_WOOD).add(
                BlockInit.ANCIENT_CRIMSON.button().get().asItem(), BlockInit.ANCIENT_CRIMSON.pressurePlate().get().asItem(),
                BlockInit.ANCIENT_CRIMSON.block().get().asItem(), BlockInit.ANCIENT_CRIMSON.slab().get().asItem(),
                BlockInit.ANCIENT_CRIMSON.fence().get().asItem(), BlockInit.ANCIENT_CRIMSON.fenceGate().get().asItem(),
                BlockInit.ANCIENT_CRIMSON.stairs().get().asItem(), BlockInit.ANCIENT_CRIMSON.door().get().asItem(),
                BlockInit.ANCIENT_CRIMSON.trapDoor().get().asItem(),
                BlockInit.ANCIENT_WARPED.button().get().asItem(), BlockInit.ANCIENT_WARPED.pressurePlate().get().asItem(),
                BlockInit.ANCIENT_WARPED.block().get().asItem(), BlockInit.ANCIENT_WARPED.slab().get().asItem(),
                BlockInit.ANCIENT_WARPED.fence().get().asItem(), BlockInit.ANCIENT_WARPED.fenceGate().get().asItem(),
                BlockInit.ANCIENT_WARPED.stairs().get().asItem(), BlockInit.ANCIENT_WARPED.door().get().asItem(),
                BlockInit.ANCIENT_WARPED.trapDoor().get().asItem()
        );

        tag(ItemTags.LEAVES).add(BlockInit.NYMPH_LEAVES.get().asItem(), BlockInit.PALM_LEAVES.get().asItem());
        tag(ItemTags.LOGS_THAT_BURN).add(BlockInit.NYMPH_LOG.get().asItem(), BlockInit.STRIPPED_NYMPH_LOG.get().asItem(),
                BlockInit.PALM_LOG.get().asItem(), BlockInit.STRIPPED_PALM_LOG.get().asItem());
        tag(ItemTags.LOGS).add(BlockInit.NYMPH_LOG.get().asItem(), BlockInit.STRIPPED_NYMPH_LOG.get().asItem(),
                BlockInit.PALM_LOG.get().asItem(), BlockInit.STRIPPED_PALM_LOG.get().asItem());

        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ItemInit.PANBEE.get(), ItemInit.COLUMN_CAVITATION.get());

        TagsInit.Item.getItemsThatCanSink().stream()
                .map(ItemLike::asItem).map(Item::builtInRegistryHolder).map(Holder.Reference::key)
                .forEach(sink::add);

        var trimmables = tag(ItemTags.TRIMMABLE_ARMOR);
        addArmorSet(trimmables, ItemInit.AQUAMARINE);
        addArmorSet(trimmables, ItemInit.BROWN_WROUGHT);
        addArmorSet(trimmables, ItemInit.ORICHALCUM);
    }

    private void addArmorSet(IntrinsicTagAppender<Item> tag, AtlantisArmorSet set) {
        tag.add(set.helmet().get(), set.chestplate().get(), set.leggings().get(), set.boots().get());
    }
}
