package com.github.theredbrain.minecraftxterraria.mixin.entity.player;
//
//import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerInventoryMixin;
//import com.github.theredbrain.minecraftxterraria.registry.ItemsRegistry;
//import com.google.common.collect.ImmutableList;
//import net.minecraft.entity.damage.DamageSource;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.Inventories;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.nbt.NbtList;
//import net.minecraft.recipe.RecipeInputProvider;
//import net.minecraft.recipe.RecipeMatcher;
//import net.minecraft.util.collection.DefaultedList;
//import org.spongepowered.asm.mixin.*;
//
//import java.util.List;
//
//@Mixin(PlayerInventory.class)
//public class PlayerInventoryMixin implements RecipeInputProvider, DuckPlayerInventoryMixin {
//
//    @Shadow
//    @Final
//    @Mutable
//    public static int MAIN_SIZE = 50;
//
//    @Shadow
//    @Final
//    @Mutable
//    private static int HOTBAR_SIZE = 10;
//
//
//    @Shadow
//    @Final
//    @Mutable
//    public static int[] ARMOR_SLOTS = new int[]{0, 1, 2};
//
//    @Shadow
//    @Final
//    @Mutable
//    public static int[] HELMET_SLOTS = new int[]{2};
//
//    @Shadow
//    @Final
//    @Mutable
//    public DefaultedList<ItemStack> main = initialize50SlotInventory();
//
//    @Shadow
//    @Final
//    @Mutable
//    public DefaultedList<ItemStack> armor = initialize3SlotInventory();
//
//    @Shadow
//    public int selectedSlot;
//
//    @Shadow @Final public PlayerEntity player;
//
//    @Shadow
//    private boolean canStackAddMore(ItemStack existingStack, ItemStack stack) {
//        throw new AssertionError();
//    }
//
//    public DefaultedList<ItemStack> vanity_armor = initialize3SlotInventory();
//    public DefaultedList<ItemStack> armor_dye = initialize3SlotInventory();
//    public DefaultedList<ItemStack> armor_2 = initialize3SlotInventory();
//    public DefaultedList<ItemStack> vanity_armor_2 = initialize3SlotInventory();
//    public DefaultedList<ItemStack> armor_dye_2 = initialize3SlotInventory();
//    public DefaultedList<ItemStack> armor_3 = initialize3SlotInventory();
//    public DefaultedList<ItemStack> vanity_armor_3 = initialize3SlotInventory();
//    public DefaultedList<ItemStack> armor_dye_3 = initialize3SlotInventory();
//
//    public DefaultedList<ItemStack> coins = initialize4SlotInventory();
//    public DefaultedList<ItemStack> ammunition = initialize4SlotInventory();
//
//    public DefaultedList<ItemStack> trash = initialize1SlotInventory();
//
//    public DefaultedList<ItemStack> equipment = initialize5SlotInventory();
//    public DefaultedList<ItemStack> equipment_dye = initialize5SlotInventory();
//
//    public DefaultedList<ItemStack> accessories = initialize7SlotInventory();
//    public DefaultedList<ItemStack> vanity_accessories = initialize7SlotInventory();
//    public DefaultedList<ItemStack> accessories_dye = initialize7SlotInventory();
//    public DefaultedList<ItemStack> accessories_2 = initialize7SlotInventory();
//    public DefaultedList<ItemStack> vanity_accessories_2 = initialize7SlotInventory();
//    public DefaultedList<ItemStack> accessories_dye_2 = initialize7SlotInventory();
//    public DefaultedList<ItemStack> accessories_3 = initialize7SlotInventory();
//    public DefaultedList<ItemStack> vanity_accessories_3 = initialize7SlotInventory();
//    public DefaultedList<ItemStack> accessories_dye_3 = initialize7SlotInventory();
//
//    public DefaultedList<ItemStack> void_vault = initialize40SlotInventory();
//
//    private static DefaultedList<ItemStack> initialize1SlotInventory() {
//        return DefaultedList.ofSize(1, ItemStack.EMPTY);
//    }
//
//    private static DefaultedList<ItemStack> initialize3SlotInventory() {
//        return DefaultedList.ofSize(3, ItemStack.EMPTY);
//    }
//
//    private static DefaultedList<ItemStack> initialize4SlotInventory() {
//        return DefaultedList.ofSize(4, ItemStack.EMPTY);
//    }
//
//    private static DefaultedList<ItemStack> initialize5SlotInventory() {
//        return DefaultedList.ofSize(5, ItemStack.EMPTY);
//    }
//
//    private static DefaultedList<ItemStack> initialize7SlotInventory() {
//        return DefaultedList.ofSize(7, ItemStack.EMPTY);
//    }
//
//    private static DefaultedList<ItemStack> initialize40SlotInventory() {
//        return DefaultedList.ofSize(40, ItemStack.EMPTY);
//    }
//
//    private static DefaultedList<ItemStack> initialize50SlotInventory() {
//        return DefaultedList.ofSize(50, ItemStack.EMPTY);
//    }
//
//    private List<DefaultedList<ItemStack>> getListOfCombinedInventories() {
//        return ImmutableList.of(
//                this.main, this.coins, this.ammunition, this.trash, this.void_vault,
//                this.armor, this.vanity_armor, this.armor_dye,
//                this.armor_2, this.vanity_armor_2, this.armor_dye_2,
//                this.armor_3, this.vanity_armor_3, this.armor_dye_3,
//                this.equipment, this.equipment_dye,
//                this.accessories, this.vanity_accessories, this.accessories_dye,
//                this.accessories_2, this.vanity_accessories_2, this.accessories_dye_2,
//                this.accessories_3, this.vanity_accessories_3, this.accessories_dye_3);
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason hotbar is bigger
//     */
//    @Overwrite
//    public static int getHotbarSize() {
//        return 10;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason also check open void vault
//     */
//    @Overwrite
//    public int getEmptySlot() { // TODO coin and ammunition slot can only hold some items
//        boolean bl = false;
//        ItemStack itemStack;
//        for (int i = 0; i < this.coins.size(); ++i) {
//            if (!this.coins.get(i).isEmpty()) continue;
//            return i; // TODO correct slot ids
//        }
//        for (int i = 0; i < this.ammunition.size(); ++i) {
//            if (!this.ammunition.get(i).isEmpty()) continue;
//            return i; // TODO correct slot ids
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            if ((itemStack = this.main.get(i)).getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (!itemStack.isEmpty()) continue;
//            return i; // TODO correct slot ids
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                if (!this.void_vault.get(i).isEmpty()) continue;
//                return this.main.size() + 1 + i; // TODO correct slot ids
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason hotbar is bigger
//     */
//    @Overwrite
//    public static boolean isValidHotbarIndex(int slot) {
//        return slot >= 0 && slot < 10;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason also check open void vault
//     */
//    @Overwrite
//    public int getSlotWithStack(ItemStack stack) { // TODO coin and ammunition slot can only hold some items
//        boolean bl = false;
//        ItemStack itemStack;
//        for (int i = 0; i < this.coins.size(); ++i) {
//            if (this.coins.get(i).isEmpty() || !ItemStack.canCombine(stack, this.coins.get(i))) continue;
//            return i; // TODO correct slot ids
//        }
//        for (int i = 0; i < this.ammunition.size(); ++i) {
//            if (this.ammunition.get(i).isEmpty() || !ItemStack.canCombine(stack, this.ammunition.get(i))) continue;
//            return i; // TODO correct slot ids
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            if ((itemStack = this.main.get(i)).getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack)) continue;
//            return i; // TODO correct slot ids
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                if (this.void_vault.get(i).isEmpty() || !ItemStack.canCombine(stack, this.void_vault.get(i))) continue;
//                return this.main.size() + 1 + i; // TODO correct slot ids
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public int indexOf(ItemStack stack) { // TODO coin and ammunition slot can only hold some items and correct slot ids
//        boolean bl = false;
//        if (bl) {
//            for (int i = 0; i < this.coins.size(); ++i) {
//                ItemStack itemStack = this.coins.get(i);
//                if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName()) continue;
//                return i; // TODO correct slot ids
//            }
//        }
//        if (bl) {
//            for (int i = 0; i < this.ammunition.size(); ++i) {
//                ItemStack itemStack = this.ammunition.get(i);
//                if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName()) continue;
//                return i; // TODO correct slot ids
//            }
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            ItemStack itemStack = this.main.get(i);
//            if (itemStack.getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName()) continue;
//            return i; // TODO correct slot ids
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                ItemStack itemStack = this.void_vault.get(i);
//                if (itemStack.isEmpty() || !ItemStack.canCombine(stack, itemStack) || itemStack.isDamaged() || itemStack.hasEnchantments() || itemStack.hasCustomName()) continue;
//                return i; // TODO correct slot ids
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason hotbar is bigger
//     */
//    @Overwrite
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
//    /**
//     * @author TheRedBrain
//     * @reason hotbar is bigger
//     */
//    @Overwrite
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
//
//    /**
//     * @author TheRedBrain
//     * @reason also check open void vault
//     */
//    @Overwrite
//    public int getOccupiedSlotWithRoomForStack(ItemStack stack) {
//        boolean bl = false;
//        ItemStack itemStack;
//        if (this.canStackAddMore(this.getStack(this.selectedSlot), stack)) {
//            return this.selectedSlot;
//        }
//        for (int i = 0; i < this.coins.size(); ++i) {
//            if (!this.canStackAddMore(this.coins.get(i), stack)) continue;
//            return i; // TODO correct slot ids
//        }
//        for (int i = 0; i < this.ammunition.size(); ++i) {
//            if (!this.canStackAddMore(this.ammunition.get(i), stack)) continue;
//            return i; // TODO correct slot ids
//        }
//        for (int i = 0; i < this.main.size(); ++i) {
//            if ((itemStack = this.main.get(i)).getItem() == ItemsRegistry.OPEN_VOID_BAG) {
//                bl = true;
//            }
//            if (!this.canStackAddMore(itemStack, stack)) continue;
//            return i; // TODO correct slot ids
//        }
//        if (bl) {
//            for (int i = 0; i < this.void_vault.size(); ++i) {
//                if (!this.canStackAddMore(this.void_vault.get(i), stack)) continue;
//                return this.main.size() + 1 + i; // TODO correct slot ids
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public void updateItems() {
//        for (DefaultedList<ItemStack> defaultedList : this.getListOfCombinedInventories()) {
//            for (int i = 0; i < defaultedList.size(); ++i) {
//                if (defaultedList.get(i).isEmpty()) continue;
//                defaultedList.get(i).inventoryTick(this.player.world, this.player, i, this.selectedSlot == i);
//            }
//        }
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public ItemStack removeStack(int slot, int amount) {
//        DefaultedList<ItemStack> list = null;
//        for (DefaultedList<ItemStack> defaultedList : this.getListOfCombinedInventories()) {
//            if (slot < defaultedList.size()) {
//                list = defaultedList;
//                break;
//            }
//            slot -= defaultedList.size();
//        }
//        if (list != null && !((ItemStack)list.get(slot)).isEmpty()) {
//            return Inventories.splitStack(list, slot, amount);
//        }
//        return ItemStack.EMPTY;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public void removeOne(ItemStack stack) {
//        block0: for (DefaultedList<ItemStack> defaultedList : this.getListOfCombinedInventories()) {
//            for (int i = 0; i < defaultedList.size(); ++i) {
//                if (defaultedList.get(i) != stack) continue;
//                defaultedList.set(i, ItemStack.EMPTY);
//                continue block0;
//            }
//        }
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public ItemStack removeStack(int slot) {
//        DefaultedList<ItemStack> defaultedList = null;
//        for (DefaultedList<ItemStack> defaultedList2 : this.getListOfCombinedInventories()) {
//            if (slot < defaultedList2.size()) {
//                defaultedList = defaultedList2;
//                break;
//            }
//            slot -= defaultedList2.size();
//        }
//        if (defaultedList != null && !((ItemStack)defaultedList.get(slot)).isEmpty()) {
//            ItemStack itemStack = defaultedList.get(slot);
//            defaultedList.set(slot, ItemStack.EMPTY);
//            return itemStack;
//        }
//        return ItemStack.EMPTY;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public void setStack(int slot, ItemStack stack) {
//        DefaultedList<ItemStack> defaultedList = null;
//        for (DefaultedList<ItemStack> defaultedList2 : this.getListOfCombinedInventories()) {
//            if (slot < defaultedList2.size()) {
//                defaultedList = defaultedList2;
//                break;
//            }
//            slot -= defaultedList2.size();
//        }
//        if (defaultedList != null) {
//            defaultedList.set(slot, stack);
//        }
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public NbtList writeNbt(NbtList nbtList) { // TODO nbt
//        NbtCompound nbtCompound;
//        int i;
//        for (i = 0; i < this.main.size(); ++i) {
//            if (this.main.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)i);
//            this.main.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
//        for (i = 0; i < this.armor.size(); ++i) {
//            if (this.armor.get(i).isEmpty()) continue;
//            nbtCompound = new NbtCompound();
//            nbtCompound.putByte("Slot", (byte)(i + 100));
//            this.armor.get(i).writeNbt(nbtCompound);
//            nbtList.add(nbtCompound);
//        }
////        for (i = 0; i < this.offHand.size(); ++i) {
////            if (this.offHand.get(i).isEmpty()) continue;
////            nbtCompound = new NbtCompound();
////            nbtCompound.putByte("Slot", (byte)(i + 150));
////            this.offHand.get(i).writeNbt(nbtCompound);
////            nbtList.add(nbtCompound);
////        }
//        return nbtList;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public void readNbt(NbtList nbtList) { // TODO nbt
//        this.main.clear();
//        this.armor.clear();
////        this.offHand.clear();
//        for (int i = 0; i < nbtList.size(); ++i) {
//            NbtCompound nbtCompound = nbtList.getCompound(i);
//            int j = nbtCompound.getByte("Slot") & 0xFF;
//            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
//            if (itemStack.isEmpty()) continue;
//            if (j >= 0 && j < this.main.size()) {
//                this.main.set(j, itemStack);
//                continue;
//            }
//            if (j >= 100 && j < this.armor.size() + 100) {
//                this.armor.set(j - 100, itemStack);
//                continue;
//            }
////            if (j < 150 || j >= this.offHand.size() + 150) continue;
////            this.offHand.set(j - 150, itemStack);
//        }
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public int size() {
//        int size = 0;
//        for (DefaultedList<ItemStack> defaultedList : this.getListOfCombinedInventories()) {
//            size += defaultedList.size();
//        }
//        return size;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public boolean isEmpty() {
//        for (DefaultedList<ItemStack> defaultedList : this.getListOfCombinedInventories()) {
//            for (int i = 0; i < defaultedList.size(); ++i) {
//                if (defaultedList.get(i).isEmpty()) continue;
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason inventory is completely overhauled
//     */
//    @Overwrite
//    public ItemStack getStack(int slot) {
//        DefaultedList<ItemStack> list = null;
//        for (DefaultedList<ItemStack> defaultedList : this.getListOfCombinedInventories()) {
//            if (slot < defaultedList.size()) {
//                list = defaultedList;
//                break;
//            }
//            slot -= defaultedList.size();
//        }
//        return list == null ? ItemStack.EMPTY : (ItemStack)list.get(slot);
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason custom implementation is used and this one causes crashes
//     */
//    @Overwrite
//    public ItemStack getArmorStack(int slot) {
//        return ItemStack.EMPTY;
//    }
//
//
//    /**
//     * @author TheRedBrain
//     * @reason armour no longer takes damage
//     */
//    @Overwrite
//    public void damageArmor(DamageSource damageSource, float amount, int[] slots) {
//
//    }
//
//    /**
//     * @author TheRedBrain
//     * @reason also check open void vault
//     */
//    @Overwrite
//    public void populateRecipeFinder(RecipeMatcher finder) {
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
//    }
//
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
//
//    public ItemStack mxtGetArmorStack(int slot, int loadout) {
//        switch (loadout) {
//            case 0:
//                return this.armor.get(slot);
//            case 1:
//                return this.armor_2.get(slot);
//            case 2:
//                return this.armor_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
//    }
//
//    public ItemStack mxtGetVanityArmorStack(int slot, int loadout) {
//        switch (loadout) {
//            case 0:
//                return this.vanity_armor.get(slot);
//            case 1:
//                return this.vanity_armor_2.get(slot);
//            case 2:
//                return this.vanity_armor_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
//    }
//
//    public ItemStack mxtGetArmorDyeStack(int slot, int loadout) {
//        switch (loadout) {
//            case 0:
//                return this.armor_dye.get(slot);
//            case 1:
//                return this.armor_dye_2.get(slot);
//            case 2:
//                return this.armor_dye_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
//    }
//
//    public ItemStack mxtGetAccessoryStack(int slot, int loadout) {
//        switch (loadout) {
//            case 0:
//                return this.accessories.get(slot);
//            case 1:
//                return this.accessories_2.get(slot);
//            case 2:
//                return this.accessories_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
//    }
//
//    public ItemStack mxtGetVanityAccessoryStack(int slot, int loadout) {
//        switch (loadout) {
//            case 0:
//                return this.vanity_accessories.get(slot);
//            case 1:
//                return this.vanity_accessories_2.get(slot);
//            case 2:
//                return this.vanity_accessories_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
//    }
//
//    public ItemStack mxtGetAccessoryDyeStack(int slot, int loadout) {
//        switch (loadout) {
//            case 0:
//                return this.accessories_dye.get(slot);
//            case 1:
//                return this.accessories_dye_2.get(slot);
//            case 2:
//                return this.accessories_dye_3.get(slot);
//            default:
//                return ItemStack.EMPTY;
//        }
//    }
//
//    public ItemStack mxtGetEquipmentStack(int slot) {
//        return this.equipment.get(slot);
//    }
//
//    public ItemStack mxtGetEquipmentDyeStack(int slot) {
//        return this.equipment_dye.get(slot);
//    }
//}
