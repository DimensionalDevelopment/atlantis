package com.mystic.atlantis.datagen;

//public class AtlantisBlockModelProvider extends BlockModelProvider {
//
//    public AtlantisBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
//        super(generator, "atlantis", existingFileHelper);
//    }
//
//    @Override
//    protected void registerModels() {
//        this.cubeAll(BlockInit.ORICHALCUM_BLOCK);
//        this.cubeBottomTop("writing_block", Atlantis.id("block/writing_table_side"), Atlantis.id("block/atlantean_planks"), Atlantis.id("block/writing_table_top"));
//    }
//
//    private void cubeAll(RegistryObject<Block> block) {
//        this.cubeAll(block.getId().getPath(), blockTexture(block.getId()));
//    }
//
//    private ResourceLocation blockTexture(ResourceLocation loc) {
//        return new ResourceLocation(loc.getNamespace(), "block/" + loc.getPath());
//    }
//}
