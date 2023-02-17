package com.github.theredbrain.minecraftxterraria.mixin.item;

import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.item.DuckItemMixin;
import com.github.theredbrain.minecraftxterraria.item.DuckItemStackMixin;
import com.github.theredbrain.minecraftxterraria.item.MxtUsableItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ItemStack.class)
public class ItemStackMixin implements DuckItemStackMixin {

    @Shadow
    private int count;

    @Shadow
    private NbtCompound nbt;

    @Shadow
    private void updateEmptyState() {
        throw new AssertionError();
    }

    @Shadow
    public Item getItem() {
        throw new AssertionError();
    }

    @Shadow
    public int getDamage() {
        throw new AssertionError();
    }

    @Shadow
    public void setDamage(int damage) {
        throw new AssertionError();
    }

    @Shadow
    public boolean hasNbt() {
        throw new AssertionError();
    }

    /**
     * @author TheRedBrain
     */
    @Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void ItemStack(NbtCompound nbt, CallbackInfo ci) {
        this.count = nbt.getShort("MxtCount");
        if (nbt.contains("tag", NbtElement.COMPOUND_TYPE)) {
            this.nbt = nbt.getCompound("tag");
            this.getItem().postProcessNbt(this.nbt);
        }
        if (this.getItem().isDamageable()) {
            this.setDamage(this.getDamage());
        }
        this.updateEmptyState();
    }

//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        return ((MxtUsableItem)this.getItem()).mxtUse(world, user);
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public NbtCompound writeNbt(NbtCompound nbt) {
        Identifier identifier = Registries.ITEM.getId(this.getItem());
        nbt.putString("id", identifier == null ? "minecraft:air" : identifier.toString());
        nbt.putShort("MxtCount", (short) this.count);
        // unfortunately necessary, because the constructor expects it and deletes the itemStack if not found
        nbt.putByte("Count", (byte)this.count);
        if (this.nbt != null) {
            nbt.put("tag", this.nbt.copy());
        }
        return nbt;
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> mxtGetAttributeModifiers(MxtEquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> multimap;
        if (this.hasNbt() && this.nbt.contains("AttributeModifiers", NbtElement.LIST_TYPE)) {
            multimap = HashMultimap.create();
            NbtList nbtList = this.nbt.getList("AttributeModifiers", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < nbtList.size(); ++i) {
                EntityAttributeModifier entityAttributeModifier;
                Optional<EntityAttribute> optional;
                NbtCompound nbtCompound = nbtList.getCompound(i);
                if (nbtCompound.contains("Slot", NbtElement.STRING_TYPE) && !nbtCompound.getString("Slot").equals(slot.getName()) || !(optional = Registries.ATTRIBUTE.getOrEmpty(Identifier.tryParse(nbtCompound.getString("AttributeName")))).isPresent() || (entityAttributeModifier = EntityAttributeModifier.fromNbt(nbtCompound)) == null || entityAttributeModifier.getId().getLeastSignificantBits() == 0L || entityAttributeModifier.getId().getMostSignificantBits() == 0L) continue;
                multimap.put(optional.get(), entityAttributeModifier);
            }
        } else {
            multimap = ((DuckItemMixin) this.getItem()).mxtGetAttributeModifiers(slot);
        }
        return multimap;
    }
}
