package com.mystic.atlantis.inventory;

import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.GlyphBlock;
import com.mystic.atlantis.items.item.LinguisticGlyphScrollItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

public class LinguisticMenu extends AbstractContainerMenu {
	private static final int INV_SLOT_START = 4;
	private static final int INV_SLOT_END = 31;
	private static final int USE_ROW_SLOT_START = 31;
	private static final int USE_ROW_SLOT_END = 40;
	private final ContainerLevelAccess access;
	Runnable slotUpdateListener = () -> {};
	final Slot blankSlot;
	final Slot dyeSlot;
	private final Slot symbolSlot;
	private final Slot resultSlot;
	long lastSoundTime;
	private final Container inputContainer = new SimpleContainer(3) {
		@Override
		public void setChanged() {
			super.setChanged();
			LinguisticMenu.this.slotsChanged(this);
			LinguisticMenu.this.slotUpdateListener.run();
		}
	};
	private final Container outputContainer = new SimpleContainer(1) {
		@Override
		public void setChanged() {
			super.setChanged();
			LinguisticMenu.this.slotUpdateListener.run();
		}
	};

	public LinguisticMenu(int i, Inventory arg) {
		this(i, arg, ContainerLevelAccess.NULL);
	}

	public LinguisticMenu(int i, Inventory arg, ContainerLevelAccess arg2) {
		super(MenuTypeInit.LINGUISTIC.get(), i);
		this.access = arg2;
		this.blankSlot = this.addSlot(new Slot(this.inputContainer, 0, 28, 28) {
			@Override
			public boolean mayPlace(ItemStack arg) {
				return arg.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof GlyphBlock;
			}
		});
		this.dyeSlot = this.addSlot(new Slot(this.inputContainer, 1, 48,28) {
			@Override
			public boolean mayPlace(ItemStack arg) {
				return arg.getItem() instanceof DyeItem;
			}
		});
		this.symbolSlot = this.addSlot(new Slot(this.inputContainer, 2, 38, 48) {
			@Override
			public boolean mayPlace(ItemStack arg) {
				return arg.getItem() instanceof LinguisticGlyphScrollItem;
			}
		});
		this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 122, 38) {
			@Override
			public boolean mayPlace(ItemStack arg) {
				return false;
			}

			@Override
			public void onTake(Player arg, ItemStack arg2) {
				LinguisticMenu.this.blankSlot.remove(1);
				LinguisticMenu.this.dyeSlot.remove(1);
				LinguisticMenu.this.symbolSlot.remove(1);

//				if (!LinguisticMenu.this.blankSlot.hasItem() || !LinguisticMenu.this.dyeSlot.hasItem()) {
//					LinguisticMenu.this.selectedBannerPatternIndex.set(0);
//				}

				access.execute((argx, arg2xxx) -> {
					long l = argx.getGameTime();
					if (LinguisticMenu.this.lastSoundTime != l) {
						argx.playSound(null, arg2xxx, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
						LinguisticMenu.this.lastSoundTime = l;
					}

				});
				super.onTake(arg, arg2);
			}
		});

		for(int j = 0; j < 3; ++j) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(arg, k + j * 9 + 9, 12 + k * 18, 88 + j * 18));
			}
		}

		for(int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(arg, j, 12 + j * 18, 146));
		}
	}

	@Override
	public boolean stillValid(Player arg) {
		return stillValid(this.access, arg, BlockInit.LINGUISTIC_BLOCK.get());
	}

	@Override
	public void slotsChanged(Container arg) {
		ItemStack blankStack = this.blankSlot.getItem();
		ItemStack dyetack = this.dyeSlot.getItem();
		ItemStack systemStack = this.symbolSlot.getItem();
		ItemStack resultStack = this.resultSlot.getItem();
		if (!resultStack.isEmpty() && (blankStack.isEmpty() || dyetack.isEmpty()) && systemStack.isEmpty()) {
			this.resultSlot.set(ItemStack.EMPTY);
		}

		this.setupResultSlot();
		this.broadcastChanges();
	}

	public void registerUpdateListener(Runnable runnable) {
		this.slotUpdateListener = runnable;
	}

	@Override
	public ItemStack quickMoveStack(Player arg, int i) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(i);
		if (slot != null && slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();
			if (i == this.resultSlot.index) {
				if (!this.moveItemStackTo(itemStack2, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemStack2, itemStack);
			} else if (i != this.dyeSlot.index && i != this.blankSlot.index && i != this.symbolSlot.index) {
				if (BlockInit.getLinguisticBlock(LinguisticGlyph.BLANK, null).map(Block::asItem).filter(a -> a == itemStack2.getItem()).isPresent()) {
					if (!this.moveItemStackTo(itemStack2, this.blankSlot.index, this.blankSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (itemStack2.getItem() instanceof DyeItem) {
					if (!this.moveItemStackTo(itemStack2, this.dyeSlot.index, this.dyeSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (itemStack2.getItem() instanceof LinguisticGlyphScrollItem) {
					if (!this.moveItemStackTo(itemStack2, this.symbolSlot.index, this.symbolSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (i >= 4 && i < 31) {
					if (!this.moveItemStackTo(itemStack2, 31, 40, false)) {
						return ItemStack.EMPTY;
					}
				} else if (i >= 31 && i < 40 && !this.moveItemStackTo(itemStack2, 4, 31, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemStack2, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(arg, itemStack2);
		}

		return itemStack;
	}

	@Override
	public void removed(Player arg) {
		super.removed(arg);
		this.access.execute((arg2, arg3) -> this.clearContainer(arg, this.inputContainer));
	}

	private void setupResultSlot() {
		DyeColor dye = this.dyeSlot.hasItem() ? ((DyeItem) this.dyeSlot.getItem().getItem()).getDyeColor() : null;
		LinguisticGlyph symbol = this.symbolSlot.hasItem() ? ((LinguisticGlyphScrollItem) this.symbolSlot.getItem().getItem()).getSymbol() : LinguisticGlyph.BLANK;
		ItemStack result = ItemStack.EMPTY;

		if(this.blankSlot.hasItem()) {
			result = new ItemStack(BlockInit.getLinguisticBlock(symbol, dye).get());
		}

//		if (this.selectedBannerPatternIndex.get() > 0) {
//			ItemStack itemStack = this.blankSlot.getItem();
//			ItemStack itemStack2 = this.dyeSlot.getItem();
//			ItemStack itemStack3 = ItemStack.EMPTY;
//			if (!itemStack.isEmpty() && !itemStack2.isEmpty()) {
//				itemStack3 = itemStack.copy();
//				itemStack3.setCount(1);
//				BannerPattern bannerPattern = BannerPattern.values()[this.selectedBannerPatternIndex.get()];
//				DyeColor dyeColor = ((DyeItem)itemStack2.getItem()).getDyeColor();
//				CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack3);
//				ListTag listTag;
//				if (compoundTag != null && compoundTag.contains("Patterns", 9)) {
//					listTag = compoundTag.getList("Patterns", 10);
//				} else {
//					listTag = new ListTag();
//					if (compoundTag == null) {
//						compoundTag = new CompoundTag();
//					}
//
//					compoundTag.put("Patterns", listTag);
//				}
//
//				CompoundTag compoundTag2 = new CompoundTag();
//				compoundTag2.putString("Pattern", bannerPattern.getHashname());
//				compoundTag2.putInt("Color", dyeColor.getId());
//				listTag.add(compoundTag2);
//				BlockItem.setBlockEntityData(itemStack3, BlockEntityType.BANNER, compoundTag);
//			}

			if (!ItemStack.matches(result, this.resultSlot.getItem())) {
				this.resultSlot.set(result);
//			}
		}

	}

	public Slot getBlankSlot() {
		return this.blankSlot;
	}

	public Slot getDyeSlot() {
		return this.dyeSlot;
	}

	public Slot getSymbolSlot() {
		return this.symbolSlot;
	}

	public Slot getResultSlot() {
		return this.resultSlot;
	}
}
