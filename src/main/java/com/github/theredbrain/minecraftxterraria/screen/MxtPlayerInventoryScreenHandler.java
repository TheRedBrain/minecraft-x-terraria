package com.github.theredbrain.minecraftxterraria.screen;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.MxtPlayerInventory;
import com.github.theredbrain.minecraftxterraria.registry.Tags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;

public class MxtPlayerInventoryScreenHandler extends ScreenHandler {
    //    public static final int field_30802 = 0;
//    public static final int field_30803 = 0;
//    public static final int field_30804 = 1;
//    public static final int field_30805 = 5;
//    public static final int field_30806 = 5;
//    public static final int field_30807 = 9;
//    public static final int field_30808 = 9;
//    public static final int field_30809 = 36;
//    public static final int field_30810 = 36;
//    public static final int field_30811 = 45;
//    public static final int field_30812 = 45;
    public static final Identifier BLOCK_ATLAS_TEXTURE = new Identifier("textures/atlas/blocks.png");
    public static final Identifier EMPTY_ARMOR_SLOT_HEAD_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "item/empty_armor_slot_head");
    public static final Identifier EMPTY_ARMOR_SLOT_TORSO_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "item/empty_armor_slot_torso");
    public static final Identifier EMPTY_ARMOR_SLOT_LEGS_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "item/empty_armor_slot_legs");
    public static final Identifier EMPTY_DYE_SLOT_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "item/empty_dye_slot");

    public static final TagKey[] EQUIPMENT_ITEMS = new TagKey[]{Tags.PET_SLOT_ITEMS, Tags.LIGHT_PET_SLOT_ITEMS, Tags.MINECART_SLOT_ITEMS, Tags.MOUNT_SLOT_ITEMS, Tags.HOOK_SLOT_ITEMS};
    public static final TagKey[] VANITY_ARMOR_ITEMS = new TagKey[]{Tags.VANITY_HELMET_SLOT_ITEMS, Tags.VANITY_TORSO_SLOT_ITEMS, Tags.VANITY_LEGS_SLOT_ITEMS};
    public static final TagKey[] ARMOR_ITEMS = new TagKey[]{Tags.HELMET_SLOT_ITEMS, Tags.TORSO_SLOT_ITEMS, Tags.LEGS_SLOT_ITEMS};

    static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES = new Identifier[]{EMPTY_ARMOR_SLOT_HEAD_TEXTURE, EMPTY_ARMOR_SLOT_TORSO_TEXTURE, EMPTY_ARMOR_SLOT_LEGS_TEXTURE};
    //    static final Identifier[] EMPTY_EQUIPMENT_SLOT_TEXTURES = new Identifier[]{EMPTY_BOOTS_SLOT_TEXTURE, EMPTY_LEGGINGS_SLOT_TEXTURE, EMPTY_CHESTPLATE_SLOT_TEXTURE, EMPTY_HELMET_SLOT_TEXTURE};
    protected static final MxtEquipmentSlot[] EQUIPMENT_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.PET, MxtEquipmentSlot.LIGHT_PET, MxtEquipmentSlot.MINECART, MxtEquipmentSlot.MOUNT, MxtEquipmentSlot.HOOK};
    protected static final MxtEquipmentSlot[] EQUIPMENT_DYE_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.PET_DYE, MxtEquipmentSlot.LIGHT_PET_DYE, MxtEquipmentSlot.MINECART_DYE, MxtEquipmentSlot.MOUNT_DYE, MxtEquipmentSlot.HOOK_DYE};
    protected static final MxtEquipmentSlot[] ARMOR_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.HEAD, MxtEquipmentSlot.TORSO, MxtEquipmentSlot.LEGS};
    protected static final MxtEquipmentSlot[] VANITY_ARMOR_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.HEAD_VANITY, MxtEquipmentSlot.TORSO_VANITY, MxtEquipmentSlot.LEGS_VANITY};
    protected static final MxtEquipmentSlot[] ARMOR_DYE_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.HEAD_DYE, MxtEquipmentSlot.TORSO_DYE, MxtEquipmentSlot.LEGS_DYE};
    protected static final MxtEquipmentSlot[] ACCESSORY_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.ACCESSORY_1, MxtEquipmentSlot.ACCESSORY_2, MxtEquipmentSlot.ACCESSORY_3, MxtEquipmentSlot.ACCESSORY_4, MxtEquipmentSlot.ACCESSORY_5, MxtEquipmentSlot.ACCESSORY_6, MxtEquipmentSlot.ACCESSORY_7};
    protected static final MxtEquipmentSlot[] VANITY_ACCESSORY_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.ACCESSORY_1_VANITY, MxtEquipmentSlot.ACCESSORY_2_VANITY, MxtEquipmentSlot.ACCESSORY_3_VANITY, MxtEquipmentSlot.ACCESSORY_4_VANITY, MxtEquipmentSlot.ACCESSORY_5_VANITY, MxtEquipmentSlot.ACCESSORY_6_VANITY, MxtEquipmentSlot.ACCESSORY_7_VANITY};
    protected static final MxtEquipmentSlot[] ACCESSORY_DYE_SLOT_ORDER = new MxtEquipmentSlot[]{MxtEquipmentSlot.ACCESSORY_1_DYE, MxtEquipmentSlot.ACCESSORY_2_DYE, MxtEquipmentSlot.ACCESSORY_3_DYE, MxtEquipmentSlot.ACCESSORY_4_DYE, MxtEquipmentSlot.ACCESSORY_5_DYE, MxtEquipmentSlot.ACCESSORY_6_DYE, MxtEquipmentSlot.ACCESSORY_7_DYE};
    //    private final PlayerInventory craftingInput;
//    private final CraftingResultInventory craftingResult = new CraftingResultInventory();
    public final boolean onServer;
    protected final PlayerEntity owner;
    protected final MxtPlayerInventory mxtPlayerInventory;
//    protected final mxtPlayerInventory mxtPlayerInventory;
    protected int xGearInventoryOffset;
    protected int yGearInventoryOffset;
    protected int xEquipmentInventoryOffset;
    protected int yEquipmentInventoryOffset;

    public MxtPlayerInventoryScreenHandler(MxtPlayerInventory mxtPlayerInventory, boolean onServer, final PlayerEntity owner) {
        super(null, 0);
        int i;
        int j;
        this.xGearInventoryOffset = ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory().getXGearInventoryOffset();
        this.yGearInventoryOffset = ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory().getYGearInventoryOffset();
        this.xEquipmentInventoryOffset = ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory().getXEquipmentInventoryOffset();
        this.yEquipmentInventoryOffset = ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory().getYEquipmentInventoryOffset();
        this.onServer = onServer;
        this.owner = owner;
        this.mxtPlayerInventory = mxtPlayerInventory;
//        this.mxtPlayerInventory = mxtPlayerInventory;
        // hotbar
        for (i = 0; i < 10; ++i) {
            this.addSlot(new Slot(mxtPlayerInventory, i, 8 + i * 18, 18));
        }
        // main
        for (i = 0; i < 4; ++i) {
            for (j = 0; j < 10; ++j) {
                this.addSlot(new Slot(mxtPlayerInventory, j + (i + 1) * 10, 8 + j * 18, 40 + i * 18));
            }
        }
        // coins
        for (i = 0; i < 4; ++i) {
            this.addSlot(new Slot(mxtPlayerInventory, 50 + i, 192, 40 + i * 18) {

                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isIn(Tags.COIN_SLOT_ITEMS);
                }
            });
        }
        // ammunition
        for (i = 0; i < 4; ++i) {
            this.addSlot(new Slot(mxtPlayerInventory, 54 + i, 214, 40 + i * 18) {

                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isIn(Tags.AMMUNITION_SLOT_ITEMS);
                }
            });
        }
        // trash
        this.addSlot(new Slot(mxtPlayerInventory, 58, 170, 116));

        // equipment dye slots
        for (i = 0; i < 5; ++i) {
            final MxtEquipmentSlot equipmentSlot = EQUIPMENT_DYE_SLOT_ORDER[i];
            this.addSlot(new Slot(mxtPlayerInventory, 99 + i, xEquipmentInventoryOffset + 8, yEquipmentInventoryOffset + 8 + i * 18) {

                @Override
                public boolean isEnabled() {
                    return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 1;
                }

                @Override
                public void setStack(ItemStack stack) {
                    ItemStack itemStack = this.getStack();
                    super.setStack(stack);
                    ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                }

                @Override
                public int getMaxItemCount() {
                    return 1;
                }

//                @Override
//                public boolean canTakeItems(PlayerEntity playerEntity) {
//                    return isEnabled();
//                }

                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isIn(Tags.DYE_SLOT_ITEMS);
                }

                @Override
                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_DYE_SLOT_TEXTURE);
                }
            });
        }

        // equipment slots
        for (i = 0; i < 5; ++i) {
            final MxtEquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[i];
            final TagKey validItems = EQUIPMENT_ITEMS[i];
            this.addSlot(new Slot(mxtPlayerInventory, 104 + i, xEquipmentInventoryOffset + 28, yEquipmentInventoryOffset + 8 + i * 18) {

                @Override
                public boolean isEnabled() {
                    return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 1;
                }

                @Override
                public void setStack(ItemStack stack) {
                    ItemStack itemStack = this.getStack();
                    super.setStack(stack);
                    ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                }

                @Override
                public int getMaxItemCount() {
                    return 1;
                }

                @Override
                public boolean canInsert(ItemStack stack) {
                    return stack.isIn(validItems);
                }

//                @Override
//                public boolean canTakeItems(PlayerEntity playerEntity) {
//                return isEnabled();
//                }

//            @Override // TODO
//            public Pair<Identifier, Identifier> getBackgroundSprite() {
//                return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
//            }
            });
        }

        // loadouts
        for (j = 0; j < 3; ++j) {

            int loadout = j;
            // armor dye slots
            for (i = 0; i < 3; ++i) {
                final MxtEquipmentSlot equipmentSlot = ARMOR_DYE_SLOT_ORDER[i];
                this.addSlot(new Slot(mxtPlayerInventory, 109 + (loadout * 10) + i, xGearInventoryOffset + 8, yGearInventoryOffset + 8 + i * 18) {

                    @Override
                    public boolean isEnabled() {
                        return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 0 && ((DuckPlayerEntityMixin) owner).getActiveLoadout() == loadout;
                    }

                    @Override
                    public void setStack(ItemStack stack) {
                        ItemStack itemStack = this.getStack();
                        super.setStack(stack);
                        ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.isIn(Tags.DYE_SLOT_ITEMS);
                    }

//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }

                    @Override
                    public Pair<Identifier, Identifier> getBackgroundSprite() {
                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_DYE_SLOT_TEXTURE);
                    }
                });
            }

            // vanity armor slots
            for (i = 0; i < 3; ++i) {
                final MxtEquipmentSlot equipmentSlot = VANITY_ARMOR_SLOT_ORDER[i];
                final TagKey validItems = VANITY_ARMOR_ITEMS[i];
                this.addSlot(new Slot(mxtPlayerInventory, 139 + (loadout * 10) + i, xGearInventoryOffset + 28, yGearInventoryOffset + 8 + i * 18) {

                    @Override
                    public boolean isEnabled() {
                        return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 0 && ((DuckPlayerEntityMixin) owner).getActiveLoadout() == loadout;
                    }

                    @Override
                    public void setStack(ItemStack stack) {
                        ItemStack itemStack = this.getStack();
                        super.setStack(stack);
                        ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.isIn(validItems);
                    }

//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }

                    @Override
                    public Pair<Identifier, Identifier> getBackgroundSprite() {
                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
                    }
                });
            }

            // armor slots
            for (i = 0; i < 3; ++i) {
                final MxtEquipmentSlot equipmentSlot = ARMOR_SLOT_ORDER[i];
                final TagKey validItems = ARMOR_ITEMS[i];
                this.addSlot(new Slot(mxtPlayerInventory, 169 + (loadout * 10) + i, xGearInventoryOffset + 48, yGearInventoryOffset + 8 + i * 18) {

                    @Override
                    public boolean isEnabled() {
                        return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 0 && ((DuckPlayerEntityMixin) owner).getActiveLoadout() == loadout;
                    }

                    @Override
                    public void setStack(ItemStack stack) {
                        ItemStack itemStack = this.getStack();
                        super.setStack(stack);
                        ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.isIn(validItems);
                    }

//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }

                    @Override
                    public Pair<Identifier, Identifier> getBackgroundSprite() {
                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
                    }
                });
            }

            // accessory dye slots
            for (i = 0; i < 7; ++i) {
                final MxtEquipmentSlot equipmentSlot = ACCESSORY_DYE_SLOT_ORDER[i];
                this.addSlot(new Slot(mxtPlayerInventory, 112 + (loadout * 10) + i, xGearInventoryOffset + 8, yGearInventoryOffset + 66 + i * 18) {

                    @Override
                    public boolean isEnabled() {
                        return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 0 && ((DuckPlayerEntityMixin) owner).getActiveLoadout() == loadout;
                    }

                    @Override
                    public void setStack(ItemStack stack) {
                        ItemStack itemStack = this.getStack();
                        super.setStack(stack);
                        ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return insertionIntoSlotIsBlocked(this) && stack.isIn(Tags.DYE_SLOT_ITEMS);
                    }

//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }

                    @Override
                    public Pair<Identifier, Identifier> getBackgroundSprite() {
                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_DYE_SLOT_TEXTURE);
                    }
                });
            }

            // vanity accessory slots
            for (i = 0; i < 7; ++i) {
                final MxtEquipmentSlot equipmentSlot = VANITY_ACCESSORY_SLOT_ORDER[i];
                this.addSlot(new Slot(mxtPlayerInventory, 142 + (loadout * 10) + i, xGearInventoryOffset + 28, yGearInventoryOffset + 66 + i * 18) {

                    @Override
                    public boolean isEnabled() {
                        return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 0 && ((DuckPlayerEntityMixin) owner).getActiveLoadout() == loadout;
                    }

                    @Override
                    public void setStack(ItemStack stack) {
                        ItemStack itemStack = this.getStack();
                        super.setStack(stack);
                        ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return insertionIntoSlotIsBlocked(this) && stack.isIn(Tags.VANITY_ACCESSORY_SLOT_ITEMS);
                    }

//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }

//            @Override // TODO
//            public Pair<Identifier, Identifier> getBackgroundSprite() {
//                return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
//            }
                });
            }

            // accessory slots
            for (i = 0; i < 7; ++i) {
                final MxtEquipmentSlot equipmentSlot = ACCESSORY_SLOT_ORDER[i];
                this.addSlot(new Slot(mxtPlayerInventory, 172 + (loadout * 10) + i, xGearInventoryOffset + 48, yGearInventoryOffset + 66 + i * 18) {

                    @Override
                    public boolean isEnabled() {
                        return ((DuckPlayerEntityMixin) owner).getActiveInventoryTab() == 0 && ((DuckPlayerEntityMixin) owner).getActiveLoadout() == loadout;
                    }

                    @Override
                    public void setStack(ItemStack stack) {
                        ItemStack itemStack = this.getStack();
                        super.setStack(stack);
                        ((DuckPlayerEntityMixin)owner).mxtOnEquipStack(equipmentSlot, itemStack, stack);
                    }

                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return insertionIntoSlotIsBlocked(this) && stack.isIn(Tags.ACCESSORY_SLOT_ITEMS);
                    }

//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return insertionIntoSlotIsBlocked(this);
//                    }

//            @Override // TODO
//            public Pair<Identifier, Identifier> getBackgroundSprite() {
//                return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
//            }
                });
            }
        }
    }

    private boolean insertionIntoSlotIsBlocked(Slot slot) {
        return !((slot.inventory == this.mxtPlayerInventory && (slot.getIndex() == 117 || slot.getIndex() == 127 || slot.getIndex() == 137 || slot.getIndex() == 147 || slot.getIndex() == 157 || slot.getIndex() == 167 || slot.getIndex() == 177 || slot.getIndex() == 187 || slot.getIndex() == 197)
                    && !(((owner.world.getDifficulty() == Difficulty.NORMAL || owner.world.getDifficulty() == Difficulty.HARD) && (((DuckPlayerEntityMixin) owner).mxtGetDemonHeartConsumed())) || owner.world.getDifficulty() == Difficulty.HARD))
                || (slot.inventory == this.mxtPlayerInventory && (slot.getIndex() == 118 || slot.getIndex() == 128 || slot.getIndex() == 138 || slot.getIndex() == 148 || slot.getIndex() == 158 || slot.getIndex() == 168 || slot.getIndex() == 178 || slot.getIndex() == 188 || slot.getIndex() == 198)
                    && !((((DuckPlayerEntityMixin) owner).mxtGetDemonHeartConsumed()) && owner.world.getDifficulty() == Difficulty.HARD))
        );
    }

    public static boolean isInHotbar(int slot) {
        return slot >= 0 && slot < 10;
    }

//    @Override
//    public void populateRecipeFinder(RecipeMatcher finder) {
//        ((RecipeInputProvider) this.mxtPlayerInventory).provideRecipeInputs(finder);
//    }
//
//    @Override
//    public void clearCraftingSlots() {
////        this.craftingResult.clear();
////        this.craftingInput.clear();
//    }
//
//    @Override
//    public boolean matches(Recipe<? super MxtPlayerInventory> recipe) {
//        return recipe.matches(this.mxtPlayerInventory, this.owner.world);
//    }

//    @Override // TODO crafting?
//    public void onContentChanged(Inventory inventory) {
////        CraftingScreenHandler.updateResult(this, this.owner.world, this.owner, this.inventory, this.craftingResult); // TODO
//    }

    @Override
    public void close(PlayerEntity player) {
        ItemStack itemStack;
        if (player instanceof ServerPlayerEntity && !(itemStack = this.getCursorStack()).isEmpty()) {
            if (!player.isAlive() || ((ServerPlayerEntity)player).isDisconnected()) {
                player.dropItem(itemStack, false);
            } else {
                ((DuckPlayerEntityMixin)player).getMxtPlayerInventory().offerOrDrop(itemStack);
            }
            this.setCursorStack(ItemStack.EMPTY);
        }
    }

    @Override
    protected void dropInventory(PlayerEntity player, Inventory inventory) {
        if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            for (int i = 0; i < inventory.size(); ++i) {
                player.dropItem(inventory.removeStack(i), false);
            }
            return;
        }
        for (int i = 0; i < inventory.size(); ++i) {
            MxtPlayerInventory playerInventory = ((DuckPlayerEntityMixin)player).getMxtPlayerInventory();
            if (!(playerInventory.player instanceof ServerPlayerEntity)) continue;
            playerInventory.offerOrDrop(inventory.removeStack(i));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override // TODO
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
        /*ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot) this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            int i;
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack);
            if (slot == 0) {
                if (!this.insertItem(itemStack2, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot >= 1 && slot < 5 ? !this.insertItem(itemStack2, 9, 45, false) : (slot >= 5 && slot < 9 ? !this.insertItem(itemStack2, 9, 45, false) : (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !((Slot) this.slots.get(8 - equipmentSlot.getEntitySlotId())).hasStack() ? !this.insertItem(itemStack2, i = 8 - equipmentSlot.getEntitySlotId(), i + 1, false) : (equipmentSlot == EquipmentSlot.OFFHAND && !((Slot) this.slots.get(45)).hasStack() ? !this.insertItem(itemStack2, 45, 46, false) : (slot >= 9 && slot < 36 ? !this.insertItem(itemStack2, 36, 45, false) : (slot >= 36 && slot < 45 ? !this.insertItem(itemStack2, 9, 36, false) : !this.insertItem(itemStack2, 9, 45, false))))))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
            if (slot == 0) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;*/
    }

//    @Override
//    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
//        return !((slot.inventory == this.mxtPlayerInventory && (slot.getIndex() == 18 || slot.getIndex() == 28 || slot.getIndex() == 38) && !(((owner.world.getDifficulty() == Difficulty.NORMAL || owner.world.getDifficulty() == Difficulty.HARD) && (((DuckPlayerEntityMixin) owner).mxtGetDemonHeartConsumed())) || owner.world.getDifficulty() == Difficulty.HARD))
//                || (slot.inventory == this.mxtPlayerInventory && (slot.getIndex() == 19 || slot.getIndex() == 29 || slot.getIndex() == 39) && !((owner.world.getDifficulty() == Difficulty.NORMAL || owner.world.getDifficulty() == Difficulty.HARD) && (((DuckPlayerEntityMixin) owner).mxtGetDemonHeartConsumed()) && owner.world.getDifficulty() == Difficulty.HARD)));
//        {
//            return false;
//        }
//        return slot.inventory != this.craftingResult && super.canInsertIntoSlot(stack, slot);
//    }

//    @Override
//    public int getCraftingResultSlotIndex() {
//        return 0;
//    }

//    @Override
//    public int getCraftingWidth() {
//        return 0/*this.craftingInput.getWidth()*/;
//    }
//
//    @Override
//    public int getCraftingHeight() {
//        return 0/*this.craftingInput.getHeight()*/;
//    }
//
//    @Override
//    public int getCraftingSlotCount() {
//        return 5;
//    }
//
////    public CraftingInventory getCraftingInput() {
////        return this.inventory;
////    }
//
//    @Override
//    public RecipeBookCategory getCategory() {
//        return RecipeBookCategory.CRAFTING;
//    }

//    @Override
//    public boolean canInsertIntoSlot(int index) {
//        return index != this.getCraftingResultSlotIndex();
//    }
}