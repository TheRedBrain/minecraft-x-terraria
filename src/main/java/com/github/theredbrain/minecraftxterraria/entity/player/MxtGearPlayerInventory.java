package com.github.theredbrain.minecraftxterraria.entity.player;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.function.Predicate;

public class MxtGearPlayerInventory implements Inventory, Nameable {
//    public final DefaultedList<ItemStack> main = DefaultedList.ofSize(50, ItemStack.EMPTY);
//
//    public DefaultedList<ItemStack> coins = DefaultedList.ofSize(4, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> ammunition = DefaultedList.ofSize(4, ItemStack.EMPTY);
//
//    public DefaultedList<ItemStack> trash = DefaultedList.ofSize(1, ItemStack.EMPTY);
//
//    public DefaultedList<ItemStack> void_vault = DefaultedList.ofSize(40, ItemStack.EMPTY);

    public DefaultedList<ItemStack> equipment_dye = DefaultedList.ofSize(5, ItemStack.EMPTY);
    public DefaultedList<ItemStack> equipment = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public DefaultedList<ItemStack> armor_dye = DefaultedList.ofSize(3, ItemStack.EMPTY);
    public DefaultedList<ItemStack> accessories_dye = DefaultedList.ofSize(7, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> armor_dye_2 = DefaultedList.ofSize(3, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> accessories_dye_2 = DefaultedList.ofSize(7, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> armor_dye_3 = DefaultedList.ofSize(3, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> accessories_dye_3 = DefaultedList.ofSize(7, ItemStack.EMPTY);

    public DefaultedList<ItemStack> vanity_armor = DefaultedList.ofSize(3, ItemStack.EMPTY);
    public DefaultedList<ItemStack> vanity_accessories = DefaultedList.ofSize(7, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> vanity_armor_2 = DefaultedList.ofSize(3, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> vanity_accessories_2 = DefaultedList.ofSize(7, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> vanity_armor_3 = DefaultedList.ofSize(3, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> vanity_accessories_3 = DefaultedList.ofSize(7, ItemStack.EMPTY);

    public DefaultedList<ItemStack> armor = DefaultedList.ofSize(3, ItemStack.EMPTY);
    public DefaultedList<ItemStack> accessories = DefaultedList.ofSize(7, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> armor_2 = DefaultedList.ofSize(3, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> accessories_2 = DefaultedList.ofSize(7, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> armor_3 = DefaultedList.ofSize(3, ItemStack.EMPTY);
//    public DefaultedList<ItemStack> accessories_3 = DefaultedList.ofSize(7, ItemStack.EMPTY);

//    private final List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(
//            this.main, this.coins, this.ammunition, this.trash, this.void_vault,
//            this.armor, this.vanity_armor, this.armor_dye,
//            this.armor_2, this.vanity_armor_2, this.armor_dye_2,
//            this.armor_3, this.vanity_armor_3, this.armor_dye_3,
//            this.equipment, this.equipment_dye,
//            this.accessories, this.vanity_accessories, this.accessories_dye,
//            this.accessories_2, this.vanity_accessories_2, this.accessories_dye_2,
//            this.accessories_3, this.vanity_accessories_3, this.accessories_dye_3);

    private final List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(
            this.armor, this.vanity_armor, this.armor_dye,
            this.equipment, this.equipment_dye,
            this.accessories, this.vanity_accessories, this.accessories_dye);

    public int selectedSlot;
    public final PlayerEntity player;
    private int changeCount;
    //    private int activeLoadout;
//    public int activeInventoryTab;
    private int xGearInventoryOffset;
    private int yGearInventoryOffset;
    private int xEquipmentInventoryOffset;
    private int yEquipmentInventoryOffset;

    public MxtGearPlayerInventory(PlayerEntity player) {
        this.player = player;
        // TODO config
        this.xGearInventoryOffset = 240;
        this.yGearInventoryOffset = 30;
        this.xEquipmentInventoryOffset = this.xGearInventoryOffset + 72;
        this.yEquipmentInventoryOffset = 30;
    }

//    public ItemStack getMainHandStack() {
//        if (PlayerInventory.isValidHotbarIndex(this.selectedSlot)) {
//            return this.main.get(this.selectedSlot);
//        }
//        return ItemStack.EMPTY;
//    }
//
//    public static int getHotbarSize() {
//        return 10;
//    }

    private boolean canStackAddMore(ItemStack existingStack, ItemStack stack) {
        return !existingStack.isEmpty() && ItemStack.canCombine(existingStack, stack) && existingStack.isStackable() && existingStack.getCount() < existingStack.getMaxCount() && existingStack.getCount() < this.getMaxCountPerStack();
    }

//    public int getEmptySlot(ItemStack stack) {
//        boolean bl = false;
//        ItemStack itemStack;
//        if (stack.isIn(Tags.COIN_SLOT_ITEMS)) {
//            for (int i = 0; i < this.coins.size(); ++i) {
//                if (!this.coins.get(i).isEmpty()) continue;
//                return 50 + i;
//            }
//        }
//        if (stack.isIn(Tags.AMMUNITION_SLOT_ITEMS)) {
//            for (int i = 0; i < this.ammunition.size(); ++i) {
//                if (!this.ammunition.get(i).isEmpty()) continue;
//                return 54 + i;
//            }
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            if ((itemStack = this.main.get(i)).getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (!itemStack.isEmpty()) continue;
//            return i;
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                if (!this.void_vault.get(i).isEmpty()) continue;
//                return 99 + i;
//            }
//        }
//        return -1;
//    }
//
//    public void addPickBlock(ItemStack stack) {
//        int i = this.getSlotWithStack(stack);
//        if (PlayerInventory.isValidHotbarIndex(i)) {
//            this.selectedSlot = i;
//            return;
//        }
//        if (i == -1) {
//            int j;
//            this.selectedSlot = this.getSwappableHotbarSlot();
//            if (!this.main.get(this.selectedSlot).isEmpty() && (j = this.getEmptySlot(stack)) != -1) {
//                this.main.set(j, this.main.get(this.selectedSlot));
//            }
//            this.main.set(this.selectedSlot, stack);
//        } else {
//            this.swapSlotWithHotbar(i);
//        }
//    }
//
//    // TODO
//    public void swapSlotWithHotbar(int slot) {
//        this.selectedSlot = this.getSwappableHotbarSlot();
//        ItemStack itemStack = this.main.get(this.selectedSlot);
//        this.main.set(this.selectedSlot, this.main.get(slot));
//        this.main.set(slot, itemStack);
//    }
//
//    public static boolean isValidHotbarIndex(int slot) {
//        return slot >= 0 && slot < 10;
//    }
//
//    public int getSlotWithStack(ItemStack stack) {
//        boolean bl = false;
//        ItemStack itemStack;
//        if (stack.isIn(Tags.COIN_SLOT_ITEMS)) {
//            for (int i = 0; i < this.coins.size(); ++i) {
//                if (this.coins.get(i).isEmpty() || !ItemStack.canCombine(stack, this.coins.get(i))) continue;
//                return 50 + i;
//            }
//        }
//        if (stack.isIn(Tags.AMMUNITION_SLOT_ITEMS)) {
//            for (int i = 0; i < this.ammunition.size(); ++i) {
//                if (this.ammunition.get(i).isEmpty() || !ItemStack.canCombine(stack, this.ammunition.get(i))) continue;
//                return 54 + i;
//            }
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            if ((itemStack = this.main.get(i)).getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack)) continue;
//            return i;
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                if (this.void_vault.get(i).isEmpty() || !ItemStack.canCombine(stack, this.void_vault.get(i))) continue;
//                return 99 + i;
//            }
//        }
//        return -1;
//    }
//
//    public int indexOf(ItemStack stack) {
//        boolean bl = false;
//        if (stack.isIn(Tags.COIN_SLOT_ITEMS)) {
//            for (int i = 0; i < this.coins.size(); ++i) {
//                ItemStack itemStack = this.coins.get(i);
//                if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName())
//                    continue;
//                return 50 + i;
//            }
//        }
//        if (stack.isIn(Tags.AMMUNITION_SLOT_ITEMS)) {
//            for (int i = 0; i < this.ammunition.size(); ++i) {
//                ItemStack itemStack = this.ammunition.get(i);
//                if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName())
//                    continue;
//                return 54 + i;
//            }
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            ItemStack itemStack = this.main.get(i);
//            if (itemStack.getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName()) continue;
//            return i;
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                ItemStack itemStack = this.void_vault.get(i);
//                if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName()) continue;
//                return 99 + i;
//            }
//        }
//        return -1;
//    }
//
//    public int getSwappableHotbarSlot() {
//        int j;
//        int i;
//        for (i = 0; i < 10; ++i) {
//            j = (this.selectedSlot + i) % 10;
//            if (!this.main.get(j).isEmpty()) continue;
//            return j;
//        }
//        for (i = 0; i < 10; ++i) {
//            j = (this.selectedSlot + i) % 10;
//            if (this.main.get(j).hasEnchantments()) continue;
//            return j;
//        }
//        return this.selectedSlot;
//    }
//
//    public void scrollInHotbar(double scrollAmount) {
//        int i = (int)Math.signum(scrollAmount);
//        this.selectedSlot -= i;
//        while (this.selectedSlot < 0) {
//            this.selectedSlot += 10;
//        }
//        while (this.selectedSlot >= 10) {
//            this.selectedSlot -= 10;
//        }
//    }

    public int remove(Predicate<ItemStack> shouldRemove, int maxCount, Inventory craftingInventory) {
        int i = 0;
        boolean bl = maxCount == 0;
        i += Inventories.remove(this, shouldRemove, maxCount - i, bl);
        i += Inventories.remove(craftingInventory, shouldRemove, maxCount - i, bl);
        ItemStack itemStack = this.player.currentScreenHandler.getCursorStack();
        i += Inventories.remove(itemStack, shouldRemove, maxCount - i, bl);
        if (itemStack.isEmpty()) {
            this.player.currentScreenHandler.setCursorStack(ItemStack.EMPTY);
        }
        return i;
    }
//
//    private int addStack(ItemStack stack) {
//        int i = this.getOccupiedSlotWithRoomForStack(stack);
//        if (i == -1) {
//            i = this.getEmptySlot(stack);
//        }
//        if (i == -1) {
//            return stack.getCount();
//        }
//        return this.addStack(i, stack);
//    }
//
//    private int addStack(int slot, ItemStack stack) {
//        int j;
//        Item item = stack.getItem();
//        int i = stack.getCount();
//        ItemStack itemStack = this.getStack(slot);
//        if (itemStack.isEmpty()) {
//            itemStack = new ItemStack(item, 0);
//            if (stack.hasNbt()) {
//                itemStack.setNbt(stack.getNbt().copy());
//            }
//            this.setStack(slot, itemStack);
//        }
//        if ((j = i) > itemStack.getMaxCount() - itemStack.getCount()) {
//            j = itemStack.getMaxCount() - itemStack.getCount();
//        }
//        if (j > this.getMaxCountPerStack() - itemStack.getCount()) {
//            j = this.getMaxCountPerStack() - itemStack.getCount();
//        }
//        if (j == 0) {
//            return i;
//        }
//        itemStack.increment(j);
//        itemStack.setBobbingAnimationTime(5);
//        return i -= j;
//    }
//
//    public int getOccupiedSlotWithRoomForStack(ItemStack stack) {
//        boolean bl = false;
//        ItemStack itemStack;
//        if (this.canStackAddMore(this.getStack(this.selectedSlot), stack)) {
//            return this.selectedSlot;
//        }
//        if (stack.isIn(Tags.COIN_SLOT_ITEMS)) {
//            for (int i = 0; i < this.coins.size(); ++i) {
//                if (!this.canStackAddMore(this.coins.get(i), stack)) continue;
//                return 50 + i;
//            }
//        }
//        if (stack.isIn(Tags.AMMUNITION_SLOT_ITEMS)) {
//            for (int i = 0; i < this.ammunition.size(); ++i) {
//                if (!this.canStackAddMore(this.ammunition.get(i), stack)) continue;
//                return 54 + i;
//            }
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            if ((itemStack = this.main.get(i)).getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (!this.canStackAddMore(itemStack, stack)) continue;
//            return i;
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                if (!this.canStackAddMore(this.void_vault.get(i), stack)) continue;
//                return 99 + i;
//            }
//        }
//        return -1;
//    }

    public void updateItems() {
//        List<DefaultedList<ItemStack>> tickingInventories = ImmutableList.of();
//        if (this.activeLoadout == 0) {
//            tickingInventories = ImmutableList.of(
//                    this.main, this.coins, this.ammunition, this.void_vault, this.equipment, this.armor, this.accessories);
//        } else if (this.activeLoadout == 1) {
//            tickingInventories = ImmutableList.of(
//                    this.main, this.coins, this.ammunition, this.void_vault, this.equipment, this.armor_2, this.accessories_2);
//        } else if (this.activeLoadout == 2) {
//            tickingInventories = ImmutableList.of(
//                    this.main, this.coins, this.ammunition, this.void_vault, this.equipment, this.armor_3, this.accessories_3);
//        }

        List<DefaultedList<ItemStack>> tickingInventories = ImmutableList.of(this.equipment, this.armor, this.accessories);
        for (DefaultedList<ItemStack> defaultedList : tickingInventories) {
            for (int i = 0; i < defaultedList.size(); ++i) {
                if (defaultedList.get(i).isEmpty()) continue;
                defaultedList.get(i).inventoryTick(this.player.world, this.player, i, this.selectedSlot == i);
            }
        }
    }

//    public boolean insertStack(ItemStack stack) {
//        return this.insertStack(-1, stack);
//    }
//
//    public boolean insertStack(int slot, ItemStack stack) {
//        if (stack.isEmpty()) {
//            return false;
//        }
//        try {
//            if (!stack.isDamaged()) {
//                int i;
//                do {
//                    i = stack.getCount();
//                    if (slot == -1) {
//                        stack.setCount(this.addStack(stack));
//                        continue;
//                    }
//                    stack.setCount(this.addStack(slot, stack));
//                } while (!stack.isEmpty() && stack.getCount() < i);
////                if (stack.getCount() == i && this.player.getAbilities().creativeMode) {
////                    stack.setCount(0);
////                    return true;
////                }
//                return stack.getCount() < i;
//            }
//            if (slot == -1) {
//                slot = this.getEmptySlot(stack);
//            }
//            if (slot >= 0) {
//                this.main.set(slot, stack.copy());
//                this.main.get(slot).setBobbingAnimationTime(5);
//                stack.setCount(0);
//                return true;
//            }
////            if (this.player.getAbilities().creativeMode) {
////                stack.setCount(0);
////                return true;
////            }
//            return false;
//        }
//        catch (Throwable throwable) {
//            CrashReport crashReport = CrashReport.create(throwable, "Adding item to inventory");
//            CrashReportSection crashReportSection = crashReport.addElement("Item being added");
//            crashReportSection.add("Item ID", Item.getRawId(stack.getItem()));
//            crashReportSection.add("Item data", stack.getDamage());
//            crashReportSection.add("Item name", () -> stack.getName().getString());
//            throw new CrashException(crashReport);
//        }
//    }
//
//    public void offerOrDrop(ItemStack stack) {
//        this.offer(stack, true);
//    }
//
//    public void offer(ItemStack stack, boolean notifiesClient) {
//        while (!stack.isEmpty()) {
//            int i = this.getOccupiedSlotWithRoomForStack(stack);
//            if (i == -1) {
//                i = this.getEmptySlot(stack);
//            }
//            if (i == -1) {
//                this.player.dropItem(stack, false);
//                break;
//            }
//            int j = stack.getMaxCount() - this.getStack(i).getCount();
//            if (!this.insertStack(i, stack.split(j)) || !notifiesClient || !(this.player instanceof ServerPlayerEntity)) continue;
//            ((ServerPlayerEntity)this.player).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, i, this.getStack(i)));
//        }
//    }

    public NbtList writeNbt(NbtList nbtList) {
        NbtCompound nbtCompound;
        int i;
        int j;
//        for (i = 0; i < this.main.size(); ++i) {
//            if (this.main.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)i);
//            this.main.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
//        j = i;
//        MinecraftXTerraria.LOGGER.info("j: " + j);
//        for (i = 0; i < this.coins.size(); ++i) {
//            if (this.coins.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)(i + j));
//            this.coins.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
//        j = j + i;
//        MinecraftXTerraria.LOGGER.info("j: " + j);
//        for (i = 0; i < this.ammunition.size(); ++i) {
//            if (this.ammunition.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)(i + j));
//            this.ammunition.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
//        j = j + i;
//        MinecraftXTerraria.LOGGER.info("j: " + j);
//        for (i = 0; i < this.trash.size(); ++i) {
//            if (this.trash.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)(i + j));
//            this.trash.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
//        j = j + i;
//        for (i = 0; i < this.main.size(); ++i) {
//            if (this.main.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)i);
//            this.main.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
//        j = i;
//        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.equipment_dye.size(); ++i) {
            if (this.equipment_dye.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i));
            this.equipment_dye.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.equipment.size(); ++i) {
            if (this.equipment.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.equipment.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = j + i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.armor_dye.size(); ++i) {
            if (this.armor_dye.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.armor_dye.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = j + i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.accessories_dye.size(); ++i) {
            if (this.accessories_dye.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.accessories_dye.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = j + i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.vanity_armor.size(); ++i) {
            if (this.vanity_armor.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.vanity_armor.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = j + i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.vanity_accessories.size(); ++i) {
            if (this.vanity_accessories.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.vanity_accessories.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = j + i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.armor.size(); ++i) {
            if (this.armor.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.armor.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        j = j + i;
        MinecraftXTerraria.LOGGER.info("j: " + j);
        for (i = 0; i < this.accessories.size(); ++i) {
            if (this.accessories.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + j));
            this.accessories.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
//        j = j + i;
//        MinecraftXTerraria.LOGGER.info("j: " + j);
//        for (i = 0; i < this.void_vault.size(); ++i) {
//            if (this.void_vault.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)(i + j));
//            this.void_vault.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
        return nbtList;
    }

    public void readNbt(NbtList nbtList) { // TODO nbt
//        this.main.clear();
//        this.coins.clear();
//        this.ammunition.clear();
//        this.trash.clear();
//        this.void_vault.clear();
        this.armor.clear();
        this.vanity_armor.clear();
        this.armor_dye.clear();
        this.equipment.clear();
        this.equipment_dye.clear();
        this.accessories.clear();
        this.vanity_accessories.clear();
        this.accessories_dye.clear();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 0xFF;
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            if (itemStack.isEmpty()) continue;
//            if (j >= 0 && j < this.main.size()) {
//                this.main.set(j, itemStack);
//                continue;
//            }
//            if (j >= 50 && j < this.coins.size() + 50) {
//                this.coins.set(j - 50, itemStack);
//                continue;
//            }
//            if (j >= 54 && j < this.ammunition.size() + 54) {
//                this.ammunition.set(j - 54, itemStack);
//                continue;
//            }
//            if (j >= 58 && j < this.trash.size() + 58) {
//                this.trash.set(j - 58, itemStack);
//                continue;
//            }
            if (j >= 0 && j < this.equipment_dye.size()) {
                this.equipment_dye.set(j, itemStack);
                continue;
            }
            if (j >= 5 && j < this.equipment.size() + 5) {
                this.equipment.set(j - 5, itemStack);
                continue;
            }
            if (j >= 10 && j < this.armor_dye.size() + 10) {
                this.armor_dye.set(j - 10, itemStack);
                continue;
            }
            if (j >= 13 && j < this.accessories_dye.size() + 13) {
                this.accessories_dye.set(j - 13, itemStack);
                continue;
            }
            if (j >= 20 && j < this.vanity_armor.size() + 20) {
                this.vanity_armor.set(j - 20, itemStack);
                continue;
            }
            if (j >= 23 && j < this.vanity_accessories.size() + 23) {
                this.vanity_accessories.set(j - 23, itemStack);
                continue;
            }
            if (j >= 30 && j < this.armor.size() + 30) {
                this.armor.set(j - 30, itemStack);
                continue;
            }
            if (j >= 33 && j < this.accessories.size() + 33) {
                this.accessories.set(j - 33, itemStack);
//                continue;
            }
//            if (j >= 99 && j < this.void_vault.size() + 99) {
//                this.void_vault.set(j - 99, itemStack);
//            }
        }
    }

//    public void readNbt(NbtList nbtList) { // TODO nbt
//        this.main.clear();
//        this.coins.clear();
//        this.ammunition.clear();
//        this.trash.clear();
//        this.void_vault.clear();
//        this.armor.clear();
//        this.vanity_armor.clear();
//        this.armor_dye.clear();
//        this.armor_2.clear();
//        this.vanity_armor_2.clear();
//        this.armor_dye_2.clear();
//        this.armor_3.clear();
//        this.vanity_armor_3.clear();
//        this.armor_dye_3.clear();
//        this.equipment.clear();
//        this.equipment_dye.clear();
//        this.accessories.clear();
//        this.vanity_accessories.clear();
//        this.accessories_dye.clear();
//        this.accessories_2.clear();
//        this.vanity_accessories_2.clear();
//        this.accessories_dye_2.clear();
//        this.accessories_3.clear();
//        this.vanity_accessories_3.clear();
//        this.accessories_dye_3.clear();
//        for (int i = 0; i < nbtList.size(); ++i) {
//            NbtCompound nbtCompound = nbtList.getCompound(i);
//            int j = nbtCompound.getByte("Slot") & 0xFF;
//            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
//            if (itemStack.isEmpty()) continue;
//            if (j >= 0 && j < this.main.size()) {
//                this.main.set(j, itemStack);
//                continue;
//            }
//            if (j >= 50 && j < this.coins.size() + 50) {
//                this.coins.set(j - 50, itemStack);
//                continue;
//            }
//            if (j >= 54 && j < this.ammunition.size() + 54) {
//                this.ammunition.set(j - 54, itemStack);
//                continue;
//            }
//            if (j >= 58 && j < this.trash.size() + 58) {
//                this.trash.set(j - 58, itemStack);
//                continue;
//            }
//            if (j >= 59 && j < this.void_vault.size() + 59) {
//                this.void_vault.set(j - 59, itemStack);
//                continue;
//            }
//            if (j >= 99 && j < this.equipment_dye.size() + 99) {
//                this.equipment_dye.set(j - 99, itemStack);
//                continue;
//            }
//            if (j >= 104 && j < this.equipment.size() + 104) {
//                this.equipment.set(j - 104, itemStack);
//                continue;
//            }
//            if (j >= 109 && j < this.armor_dye.size() + 109) {
//                this.armor_dye.set(j - 109, itemStack);
//                continue;
//            }
//            if (j >= 112 && j < this.accessories_dye.size() + 112) {
//                this.accessories_dye.set(j - 112, itemStack);
//                continue;
//            }
//            if (j >= 119 && j < this.armor_dye_2.size() + 119) {
//                this.armor_dye_2.set(j - 119, itemStack);
//                continue;
//            }
//            if (j >= 122 && j < this.accessories_dye_2.size() + 122) {
//                this.accessories_dye_2.set(j - 122, itemStack);
//                continue;
//            }
//            if (j >= 129 && j < this.armor_dye_3.size() + 129) {
//                this.armor_dye_3.set(j - 129, itemStack);
//                continue;
//            }
//            if (j >= 132 && j < this.accessories_dye_3.size() + 132) {
//                this.accessories_dye_3.set(j - 132, itemStack);
//                continue;
//            }
//            if (j >= 139 && j < this.vanity_armor.size() + 139) {
//                this.vanity_armor.set(j - 139, itemStack);
//                continue;
//            }
//            if (j >= 142 && j < this.vanity_accessories.size() + 142) {
//                this.vanity_accessories.set(j - 142, itemStack);
//                continue;
//            }
//            if (j >= 149 && j < this.vanity_armor_2.size() + 149) {
//                this.vanity_armor_2.set(j - 149, itemStack);
//                continue;
//            }
//            if (j >= 152 && j < this.vanity_accessories_2.size() + 152) {
//                this.vanity_accessories_2.set(j - 152, itemStack);
//                continue;
//            }
//            if (j >= 159 && j < this.vanity_armor_3.size() + 159) {
//                this.vanity_armor_3.set(j - 159, itemStack);
//                continue;
//            }
//            if (j >= 162 && j < this.vanity_accessories_3.size() + 162) {
//                this.vanity_accessories_3.set(j - 162, itemStack);
//                continue;
//            }
//            if (j >= 169 && j < this.armor.size() + 169) {
//                this.armor.set(j - 169, itemStack);
//                continue;
//            }
//            if (j >= 172 && j < this.accessories.size() + 172) {
//                this.accessories.set(j - 172, itemStack);
//                continue;
//            }
//            if (j >= 179 && j < this.armor_2.size() + 179) {
//                this.armor_2.set(j - 179, itemStack);
//                continue;
//            }
//            if (j >= 182 && j < this.accessories_2.size() + 182) {
//                this.accessories_2.set(j - 182, itemStack);
//                continue;
//            }
//            if (j >= 189 && j < this.armor_3.size() + 189) {
//                this.armor_3.set(j - 189, itemStack);
//                continue;
//            }
//            if (j >= 194 && j < this.accessories_3.size() + 194) {
//                this.accessories_3.set(j - 194, itemStack);
//            }
//        }
//    }

    @Override
    public int size() {
        int size = 0;
        for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            size += defaultedList.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            for (int i = 0; i < defaultedList.size(); ++i) {
                if (defaultedList.get(i).isEmpty()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        DefaultedList<ItemStack> list = null;
        for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            if (slot < defaultedList.size()) {
                list = defaultedList;
                break;
            }
            slot -= defaultedList.size();
        }
        return list == null ? ItemStack.EMPTY : (ItemStack)list.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        DefaultedList<ItemStack> list = null;
        for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            if (slot < defaultedList.size()) {
                list = defaultedList;
                break;
            }
            slot -= defaultedList.size();
        }
        if (list != null && !((ItemStack)list.get(slot)).isEmpty()) {
            return Inventories.splitStack(list, slot, amount);
        }
        return ItemStack.EMPTY;
    }

    public void removeOne(ItemStack stack) {
        block0: for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            for (int i = 0; i < defaultedList.size(); ++i) {
                if (defaultedList.get(i) != stack) continue;
                defaultedList.set(i, ItemStack.EMPTY);
                continue block0;
            }
        }
    }

    @Override
    public ItemStack removeStack(int slot) {
        DefaultedList<ItemStack> defaultedList = null;
        for (DefaultedList<ItemStack> defaultedList2 : this.combinedInventory) {
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
            slot -= defaultedList2.size();
        }
        if (defaultedList != null && !((ItemStack)defaultedList.get(slot)).isEmpty()) {
            ItemStack itemStack = defaultedList.get(slot);
            defaultedList.set(slot, ItemStack.EMPTY);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = null;
        for (DefaultedList<ItemStack> defaultedList2 : this.combinedInventory) {
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
            slot -= defaultedList2.size();
        }
        if (defaultedList != null) {
            defaultedList.set(slot, stack);
        }
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    public void dropAll() {
        for (List list : this.combinedInventory) {
            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemStack = (ItemStack)list.get(i);
                if (itemStack.isEmpty()) continue;
                this.player.dropItem(itemStack, true, false);
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void markDirty() {
        ++this.changeCount;
    }

    public int getChangeCount() {
        return this.changeCount;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.player.isRemoved()) {
            return false;
        }
        return !(player.squaredDistanceTo(this.player) > 64.0);
    }

    public boolean contains(ItemStack stack) {
        for (List<ItemStack> list : this.combinedInventory) {
            for (ItemStack itemStack : list) {
                if (itemStack.isEmpty() || !ItemStack.canCombine(itemStack, stack)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean contains(TagKey<Item> tag) {
        for (List<ItemStack> list : this.combinedInventory) {
            for (ItemStack itemStack : list) {
                if (itemStack.isEmpty() || !itemStack.isIn(tag)) continue;
                return true;
            }
        }
        return false;
    }

    public void clone(MxtPlayerInventory other) {
        for (int i = 0; i < this.size(); ++i) {
            this.setStack(i, other.getStack(i));
        }
        this.selectedSlot = other.selectedSlot;
    }

    // TODO maybe also check for gear slots
    @Override
    public boolean isValid(int slot, ItemStack stack) {
//        if (slot >= 50 && slot <=53) {
//            return stack.isIn(Tags.COIN_SLOT_ITEMS);
//        }
//        if (slot >= 54 && slot <=57) {
//            return stack.isIn(Tags.AMMUNITION_SLOT_ITEMS);
//        }
        return true;
    }

//    @Override
//    public void provideRecipeInputs(RecipeMatcher finder) {
//        boolean bl = false;
//        for (ItemStack itemStack : this.main) {
//            if (itemStack.getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            finder.addUnenchantedInput(itemStack);
//        }
//        if (bl) {
//            for (ItemStack itemStack : this.void_vault) {
//                finder.addUnenchantedInput(itemStack);
//            }
//        }
//        for (ItemStack itemStack : this.coins) {
//            finder.addUnenchantedInput(itemStack);
//        }
//        for (ItemStack itemStack : this.ammunition) {
//            finder.addUnenchantedInput(itemStack);
//        }
//    }

    @Override
    public void clear() {
        for (List list : this.combinedInventory) {
            list.clear();
        }
    }

//    public void populateRecipeFinder(RecipeMatcher finder) {
//        for (ItemStack itemStack : this.main) {
//            finder.addUnenchantedInput(itemStack);
//        }
//    }

    // TODO ?
//    public ItemStack dropSelectedItem(boolean entireStack) {
//        ItemStack itemStack = this.getMainHandStack();
//        if (itemStack.isEmpty()) {
//            return ItemStack.EMPTY;
//        }
//        return this.removeStack(this.selectedSlot, entireStack ? itemStack.getCount() : 1);
//    }

    //    public int getCurrentLoadout() {
//        MinecraftXTerraria.LOGGER.info("getCurrentLoadout: " + this.currentLoadout);
//        return currentLoadout;
//    }
//
//    public void setCurrentLoadout(int currentLoadout) {
//        this.currentLoadout = currentLoadout;
//        MinecraftXTerraria.LOGGER.info("setCurrentLoadout to: " + this.currentLoadout);
//    }
//
    public ItemStack mxtGetArmorStack(int slot/*, int loadout*/) {
//        switch (loadout) {
//            case 0:
        return this.armor.get(slot);
//            case 1:
//                return this.armor_2.get(slot);
//            case 2:
//                return this.armor_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
    }

    public ItemStack mxtGetVanityArmorStack(int slot/*, int loadout*/) {
//        switch (loadout) {
//            case 0:
        return this.vanity_armor.get(slot);
//            case 1:
//                return this.vanity_armor_2.get(slot);
//            case 2:
//                return this.vanity_armor_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
    }

    public ItemStack mxtGetArmorDyeStack(int slot/*, int loadout*/) {
//        switch (loadout) {
//            case 0:
        return this.armor_dye.get(slot);
//            case 1:
//                return this.armor_dye_2.get(slot);
//            case 2:
//                return this.armor_dye_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
    }

    public ItemStack mxtGetAccessoryStack(int slot/*, int loadout*/) {
//        switch (loadout) {
//            case 0:
        return this.accessories.get(slot);
//            case 1:
//                return this.accessories_2.get(slot);
//            case 2:
//                return this.accessories_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
    }

    public ItemStack mxtGetVanityAccessoryStack(int slot/*, int loadout*/) {
//        switch (loadout) {
//            case 0:
        return this.vanity_accessories.get(slot);
//            case 1:
//                return this.vanity_accessories_2.get(slot);
//            case 2:
//                return this.vanity_accessories_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
    }

    public ItemStack mxtGetAccessoryDyeStack(int slot/*, int loadout*/) {
//        switch (loadout) {
//            case 0:
        return this.accessories_dye.get(slot);
//            case 1:
//                return this.accessories_dye_2.get(slot);
//            case 2:
//                return this.accessories_dye_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
    }

    public ItemStack mxtGetEquipmentStack(int slot) {
        return this.equipment.get(slot);
    }

    public ItemStack mxtGetEquipmentDyeStack(int slot) {
        return this.equipment_dye.get(slot);
    }

    @Override
    public Text getName() {
        return Text.translatable("container.inventory");
    }

//    public int getActiveLoadout() {
//        return activeLoadout;
//    }
//
//    public void setActiveLoadout(int activeLoadout) {
//        this.activeLoadout = activeLoadout;
//    }
//
//    /**
//     *
//     * @return 0 -> loadouts, 1 -> equipment, 2 -> housing
//     */
//    public int getActiveInventoryTab() {
//        return activeInventoryTab;
//    }
//
//    /**
//     *
//     * @param activeInventoryTab:  0 -> loadouts, 1 -> equipment, 2 -> housing
//     */
//    public void setActiveInventoryTab(int activeInventoryTab) {
//        if (activeInventoryTab < 0 || activeInventoryTab > 2) {
//            activeInventoryTab = 0;
//        }
//        this.activeInventoryTab = activeInventoryTab;
//    }

    public int getXGearInventoryOffset() {
        return this.xGearInventoryOffset;
    }

    public int getYGearInventoryOffset() {
        return this.yGearInventoryOffset;
    }

    public int getXEquipmentInventoryOffset() {
        return this.xEquipmentInventoryOffset;
    }

    public int getYEquipmentInventoryOffset() {
        return this.yEquipmentInventoryOffset;
    }
}
