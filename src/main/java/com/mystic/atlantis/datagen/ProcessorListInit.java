package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.plants.NymphSaplingBlock;
import com.mystic.atlantis.init.FireMelonHead;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

public class ProcessorListInit {
    public static ResourceKey<StructureProcessorList> ATLANTEAN_VILLAGE_FARM = key("atlantean_village/farm");
    public static ResourceKey<StructureProcessorList> ATLANTEAN_VILLAGE_ROADS = key("atlantean_village/roads");

    public ProcessorListInit(BootstrapContext<StructureProcessorList> context) {
        context.register(ATLANTEAN_VILLAGE_FARM, new StructureProcessorList(
                List.of(new RuleProcessor(List.of(
                        new ProcessorRule(
                                AlwaysTrueTest.INSTANCE,
                                new RandomBlockMatchTest(Blocks.SEA_PICKLE, 0.2f),
                                Blocks.KELP.defaultBlockState().setValue(KelpBlock.AGE, KelpBlock.MAX_AGE)
                        ),
                        new ProcessorRule(
                                AlwaysTrueTest.INSTANCE,
                                new RandomBlockMatchTest(Blocks.SEA_PICKLE, 0.25f),
                                Blocks.SEAGRASS.defaultBlockState()
                        ),
                        new ProcessorRule(
                                AlwaysTrueTest.INSTANCE,
                                new RandomBlockMatchTest(Blocks.SEA_PICKLE, 0.2f),
                                BlockInit.NYMPH_SAPLING.get().defaultBlockState().setValue(NymphSaplingBlock.STAGE, 1)
                        ),
                        new ProcessorRule(
                                AlwaysTrueTest.INSTANCE,
                                new RandomBlockMatchTest(Blocks.SEA_PICKLE, 0.2f),
                                BlockInit.FIRE_MELON_TOP.get().defaultBlockState().setValue(FireMelonHead.AGE, FireMelonHead.MAX_AGE)
                        )
                )))
        ));

        context.register(ATLANTEAN_VILLAGE_ROADS, new StructureProcessorList(
                List.of(new RuleProcessor(List.of(
                        new ProcessorRule(
                                AlwaysTrueTest.INSTANCE,
                                new RandomBlockMatchTest(BlockInit.NYMPH_PLANKS.block().get(), 0.25f),
                                Blocks.SAND.defaultBlockState()
                        )
                )))
        ));
    }

    public static ResourceKey<StructureProcessorList> key(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, Atlantis.id(name));
    }
}
