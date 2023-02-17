package com.github.theredbrain.minecraftxterraria.screen;
//
//import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
//import com.github.theredbrain.minecraftxterraria.entity.player.MxtPlayerInventory;
//import com.github.theredbrain.minecraftxterraria.registry.Tags;
//import com.mojang.datafixers.util.Pair;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.registry.tag.TagKey;
//import net.minecraft.screen.slot.Slot;
//import net.minecraft.util.Identifier;
//
//public class MxtThirdLoadoutPlayerScreenHandler extends MxtAbstractPlayerScreenHandler {
//    public MxtThirdLoadoutPlayerScreenHandler(MxtPlayerInventory mxtPlayerInventory, boolean onServer, PlayerEntity owner) {
//        super(mxtPlayerInventory, onServer, owner);
//        int i;
//        int j;
//        // hotbar
//        for (i = 0; i < 10; ++i) {
//            this.addSlot(new Slot(mxtPlayerInventory, i, 8 + i * 18, 18));
//        }
//        // main
//        for (i = 0; i < 4; ++i) {
//            for (j = 0; j < 10; ++j) {
//                this.addSlot(new Slot(mxtPlayerInventory, j + (i + 1) * 10, 8 + j * 18, 40 + i * 18));
//            }
//        }
//        // coins
//        for (i = 0; i < 4; ++i) {
//            this.addSlot(new Slot(mxtPlayerInventory, 50 + i, 192, 40 + i * 18) {
//
//                @Override
//                public boolean canInsert(ItemStack stack) {
//                    return stack.isIn(Tags.COIN_SLOT_ITEMS);
//                }
//            });
//        }
//        // ammunition
//        for (i = 0; i < 4; ++i) {
//            this.addSlot(new Slot(mxtPlayerInventory, 54 + i, 214, 40 + i * 18) {
//
//                @Override
//                public boolean canInsert(ItemStack stack) {
//                    return stack.isIn(Tags.AMMUNITION_SLOT_ITEMS);
//                }
//            });
//        }
//        // trash
//        this.addSlot(new Slot(mxtPlayerInventory, 58, 170, 116));
//
////        // equipment dye slots
////        for (i = 0; i < 5; ++i) {
////            final MxtEquipmentSlot equipmentSlot = EQUIPMENT_DYE_SLOT_ORDER[i];
////            this.addSlot(new Slot(mxtPlayerInventory, 99 + i, xGearInventoryOffset + 28, yGearInventoryOffset + 8 + i * 18) {
////
//////                @Override
//////                public boolean isEnabled() {
//////                    return activeGearInventory == 0;
//////                }
////
//////            @Override
//////            public void setStack(ItemStack stack) {
//////                ItemStack itemStack = this.getStack();
//////                super.setStack(stack);
//////                owner.onEquipStack(equipmentSlot, itemStack, stack);
//////            }
////
////                @Override
////                public int getMaxItemCount() {
////                    return 1;
////                }
////
////                @Override
////                public boolean canTakeItems(PlayerEntity playerEntity) {
////                    return isEnabled();
////                }
////
////                @Override
////                public boolean canInsert(ItemStack stack) {
////                    return stack.isIn(Tags.DYE_SLOT_ITEMS);
////                }
////
////                @Override
////                public Pair<Identifier, Identifier> getBackgroundSprite() {
////                    return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_DYE_SLOT_TEXTURE);
////                }
////            });
////        }
////
////        // equipment slots
////        for (i = 0; i < 5; ++i) {
////            final MxtEquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[i];
////            final TagKey validItems = EQUIPMENT_ITEMS[i];
////            this.addSlot(new Slot(mxtPlayerInventory, 104 + i, xGearInventoryOffset + 48, yGearInventoryOffset + 8 + i * 18) {
////
//////                @Override
//////                public boolean isEnabled() {
//////                    return activeGearInventory == 0;
//////                }
////
//////            @Override
//////            public void setStack(ItemStack stack) {
//////                ItemStack itemStack = this.getStack();
//////                super.setStack(stack);
//////                owner.onEquipStack(equipmentSlot, itemStack, stack);
//////            }
////
////                @Override
////                public int getMaxItemCount() {
////                    return 1;
////                }
////
////                @Override
////                public boolean canInsert(ItemStack stack) {
////                    return stack.isIn(validItems);
////                }
////
////                @Override
////                public boolean canTakeItems(PlayerEntity playerEntity) {
////                    return isEnabled();
////                }
////
//////            @Override // TODO
//////            public Pair<Identifier, Identifier> getBackgroundSprite() {
//////                return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
//////            }
////            });
////        }
//
//        // loadouts
////        for (j = 0; j < 3; ++j) {
////
////            int loadout = j;
//            // armor dye slots
//            for (i = 0; i < 3; ++i) {
//                final MxtEquipmentSlot equipmentSlot = ARMOR_DYE_SLOT_ORDER[i];
//                this.addSlot(new Slot(mxtPlayerInventory, 129 + i, xGearInventoryOffset + 8, yGearInventoryOffset + 8 + i * 18) {
//
////                    @Override
////                    public boolean isEnabled() {
////                        return activeGearInventory == loadout + 1;
////                    }
//
////            @Override
////            public void setStack(ItemStack stack) {
////                ItemStack itemStack = this.getStack();
////                super.setStack(stack);
////                owner.onEquipStack(equipmentSlot, itemStack, stack);
////            }
//
//                    @Override
//                    public int getMaxItemCount() {
//                        return 1;
//                    }
//
//                    @Override
//                    public boolean canInsert(ItemStack stack) {
//                        return stack.isIn(Tags.DYE_SLOT_ITEMS);
//                    }
//
//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }
//
//                    @Override
//                    public Pair<Identifier, Identifier> getBackgroundSprite() {
//                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_DYE_SLOT_TEXTURE);
//                    }
//                });
//            }
//
//            // vanity armor slots
//            for (i = 0; i < 3; ++i) {
//                final MxtEquipmentSlot equipmentSlot = VANITY_ARMOR_SLOT_ORDER[i];
//                final TagKey validItems = VANITY_ARMOR_ITEMS[i];
//                this.addSlot(new Slot(mxtPlayerInventory, 159 + i, xGearInventoryOffset + 28, yGearInventoryOffset + 8 + i * 18) {
//
////                    @Override
////                    public boolean isEnabled() {
////                        return activeGearInventory == loadout + 1;
////                    }
//
////            @Override
////            public void setStack(ItemStack stack) {
////                ItemStack itemStack = this.getStack();
////                super.setStack(stack);
////                owner.onEquipStack(equipmentSlot, itemStack, stack);
////            }
//
//                    @Override
//                    public int getMaxItemCount() {
//                        return 1;
//                    }
//
//                    @Override
//                    public boolean canInsert(ItemStack stack) {
//                        return stack.isIn(validItems);
//                    }
//
//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }
//
//                    @Override
//                    public Pair<Identifier, Identifier> getBackgroundSprite() {
//                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
//                    }
//                });
//            }
//
//            // armor slots
//            for (i = 0; i < 3; ++i) {
//                final MxtEquipmentSlot equipmentSlot = ARMOR_SLOT_ORDER[i];
//                final TagKey validItems = ARMOR_ITEMS[i];
//                this.addSlot(new Slot(mxtPlayerInventory, 189 + i, xGearInventoryOffset + 48, yGearInventoryOffset + 8 + i * 18) {
//
////                    @Override
////                    public boolean isEnabled() {
////                        return activeGearInventory == loadout + 1;
////                    }
//
////            @Override
////            public void setStack(ItemStack stack) {
////                ItemStack itemStack = this.getStack();
////                super.setStack(stack);
////                owner.onEquipStack(equipmentSlot, itemStack, stack);
////            }
//
//                    @Override
//                    public int getMaxItemCount() {
//                        return 1;
//                    }
//
//                    @Override
//                    public boolean canInsert(ItemStack stack) {
//                        return stack.isIn(validItems);
//                    }
//
//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }
//
//                    @Override
//                    public Pair<Identifier, Identifier> getBackgroundSprite() {
//                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
//                    }
//                });
//            }
//
//            // accessory dye slots
//            for (i = 0; i < 7; ++i) {
//                final MxtEquipmentSlot equipmentSlot = ACCESSORY_DYE_SLOT_ORDER[i];
//                this.addSlot(new Slot(mxtPlayerInventory, 132 + i, xGearInventoryOffset + 8, yGearInventoryOffset + 66 + i * 18) {
//
////                    @Override
////                    public boolean isEnabled() {
////                        return activeGearInventory == loadout + 1;
////                    }
//
////            @Override
////            public void setStack(ItemStack stack) {
////                ItemStack itemStack = this.getStack();
////                super.setStack(stack);
////                owner.onEquipStack(equipmentSlot, itemStack, stack);
////            }
//
//                    @Override
//                    public int getMaxItemCount() {
//                        return 1;
//                    }
//
//                    @Override // TODO check world difficulty
//                    public boolean canInsert(ItemStack stack) {
//                        return stack.isIn(Tags.DYE_SLOT_ITEMS);
//                    }
//
//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }
//
//                    @Override
//                    public Pair<Identifier, Identifier> getBackgroundSprite() {
//                        return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_DYE_SLOT_TEXTURE);
//                    }
//                });
//            }
//
//            // vanity accessory slots
//            for (i = 0; i < 7; ++i) {
//                final MxtEquipmentSlot equipmentSlot = VANITY_ACCESSORY_SLOT_ORDER[i];
//                this.addSlot(new Slot(mxtPlayerInventory, 162 + i, xGearInventoryOffset + 28, yGearInventoryOffset + 66 + i * 18) {
//
////                    @Override
////                    public boolean isEnabled() {
////                        return activeGearInventory == loadout + 1;
////                    }
//
////            @Override
////            public void setStack(ItemStack stack) {
////                ItemStack itemStack = this.getStack();
////                super.setStack(stack);
////                owner.onEquipStack(equipmentSlot, itemStack, stack);
////            }
//
//                    @Override
//                    public int getMaxItemCount() {
//                        return 1;
//                    }
//
//                    @Override // TODO check world difficulty
//                    public boolean canInsert(ItemStack stack) {
//                        return stack.isIn(Tags.VANITY_ACCESSORY_SLOT_ITEMS);
//                    }
//
//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }
//
////            @Override // TODO
////            public Pair<Identifier, Identifier> getBackgroundSprite() {
////                return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
////            }
//                });
//            }
//
//            // accessory slots
//            for (i = 0; i < 7; ++i) {
//                final MxtEquipmentSlot equipmentSlot = ACCESSORY_SLOT_ORDER[i];
//                this.addSlot(new Slot(mxtPlayerInventory, 192 + i, xGearInventoryOffset + 48, yGearInventoryOffset + 66 + i * 18) {
//
////                    @Override
////                    public boolean isEnabled() {
////                        return activeGearInventory == loadout + 1;
////                    }
//
////            @Override
////            public void setStack(ItemStack stack) {
////                ItemStack itemStack = this.getStack();
////                super.setStack(stack);
////                owner.onEquipStack(equipmentSlot, itemStack, stack);
////            }
//
//                    @Override
//                    public int getMaxItemCount() {
//                        return 1;
//                    }
//
//                    @Override // TODO check world difficulty
//                    public boolean canInsert(ItemStack stack) {
//                        return stack.isIn(Tags.ACCESSORY_SLOT_ITEMS);
//                    }
//
//                    @Override
//                    public boolean canTakeItems(PlayerEntity playerEntity) {
//                        return isEnabled();
//                    }
//
////            @Override // TODO
////            public Pair<Identifier, Identifier> getBackgroundSprite() {
////                return Pair.of(BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
////            }
//                });
//            }
////        }
//    }
//}
