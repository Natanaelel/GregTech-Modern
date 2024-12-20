package com.gregtechceu.gtceu.api.misc;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class StackedInsertionInvWrapper implements IItemHandlerModifiable {

    private final IItemHandlerModifiable itemHandler;

    public StackedInsertionInvWrapper(IItemHandlerModifiable itemHandler){
        this.itemHandler = itemHandler;
    }

    @Override
    public void setStackInSlot(int i, @NotNull ItemStack itemStack) {
        itemHandler.setStackInSlot(i, itemStack);
    }

    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int ignoredSlot, @NotNull ItemStack itemStack, boolean simulate) {
        // insert stacked
        for(int slot = 0; slot < itemHandler.getSlots(); slot++){
            if(itemStack.isEmpty())
                break;
            if(itemHandler.isItemValid(slot, itemStack) && ItemHandlerHelper.canItemStacksStack(itemHandler.getStackInSlot(slot), itemStack)){
                itemStack = itemHandler.insertItem(slot, itemStack, simulate);
            }
        }
        // insert rest normally
        itemStack = itemHandler.insertItem(ignoredSlot, itemStack, simulate);
        return itemStack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return itemHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack itemStack) {
        return itemHandler.isItemValid(slot, itemStack);
    }
}
