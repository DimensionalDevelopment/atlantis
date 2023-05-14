package com.mystic.atlantis.inventory;

import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.GlyphBlock;
import com.mystic.atlantis.init.MenuTypeInit;
import com.mystic.atlantis.items.LinguisticGlyphScrollItem;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class LinguisticMenu extends AbstractContainerMenu {
	private final ContainerLevelAccess access;
	private Runnable slotUpdateListener = () -> {};
	private final Slot blankSlot;
	private final Slot dyeSlot;
	private final Slot symbolSlot;
	private final Slot resultSlot;
	private long lastSoundTime;
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

	public LinguisticMenu(int id, Inventory inventory) {
		this(id, inventory, ContainerLevelAccess.NULL);
	}

	public LinguisticMenu(int id, Inventory inventory, ContainerLevelAccess accessLevel) {
		super(MenuTypeInit.LINGUISTIC.get(), id);
		this.access = accessLevel;
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
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 12 + k * 18, 88 + j * 18));
			}
		}

		for(int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 12 + j * 18, 146));
		}
	}

	@Override
	public boolean stillValid(Player usingPlayer) {
		return stillValid(this.access, usingPlayer, BlockInit.LINGUISTIC_BLOCK.get());
	}

	@Override
	public void slotsChanged(Container curContainer) {
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

	public void registerUpdateListener(Runnable updateListener) {
		this.slotUpdateListener = updateListener;
	}

	@Override
	public ItemStack quickMoveStack(Player usingPlayer, int index) {
		ItemStack emptyStack = ItemStack.EMPTY;
		Slot targetSlot = this.slots.get(index);

		if (targetSlot != null && targetSlot.hasItem()) {
			ItemStack targetStack = targetSlot.getItem();
			emptyStack = targetStack.copy();

			if (index == this.resultSlot.index) {
				if (!this.moveItemStackTo(targetStack, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				targetSlot.onQuickCraft(targetStack, emptyStack);
			} else if (index != this.dyeSlot.index && index != this.blankSlot.index && index != this.symbolSlot.index) {
				if (BlockInit.getLinguisticBlock(LinguisticGlyph.BLANK, null).map(Block::asItem).filter(a -> a == targetStack.getItem()).isPresent()) {
					if (!this.moveItemStackTo(targetStack, this.blankSlot.index, this.blankSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (targetStack.getItem() instanceof DyeItem) {
					if (!this.moveItemStackTo(targetStack, this.dyeSlot.index, this.dyeSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (targetStack.getItem() instanceof LinguisticGlyphScrollItem) {
					if (!this.moveItemStackTo(targetStack, this.symbolSlot.index, this.symbolSlot.index + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 4 && index < 31) {
					if (!this.moveItemStackTo(targetStack, 31, 40, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 31 && index < 40 && !this.moveItemStackTo(targetStack, 4, 31, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(targetStack, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (targetStack.isEmpty()) {
				targetSlot.set(ItemStack.EMPTY);
			} else {
				targetSlot.setChanged();
			}

			if (targetStack.getCount() == emptyStack.getCount()) {
				return ItemStack.EMPTY;
			}

			targetSlot.onTake(usingPlayer, targetStack);
		}

		return emptyStack;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.access.execute((arg2, arg3) -> this.clearContainer(player, this.inputContainer));
	}

	private void setupResultSlot() {
		DyeColor curDyeColor = this.dyeSlot.hasItem() ? ((DyeItem) this.dyeSlot.getItem().getItem()).getDyeColor() : null;
		LinguisticGlyph curSymbol = this.symbolSlot.hasItem() ? ((LinguisticGlyphScrollItem) this.symbolSlot.getItem().getItem()).getSymbol() : LinguisticGlyph.BLANK;
		ItemStack result = ItemStack.EMPTY;

		if(this.blankSlot.hasItem()) {
			result = new ItemStack(BlockInit.getLinguisticBlock(curSymbol, curDyeColor).get());
		}

		if (!ItemStack.matches(result, this.resultSlot.getItem())) {
			this.resultSlot.set(result);
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
