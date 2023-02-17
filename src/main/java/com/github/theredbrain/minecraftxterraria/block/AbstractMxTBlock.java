package com.github.theredbrain.minecraftxterraria.block;

import com.github.theredbrain.minecraftxterraria.item.MxTBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbstractMxTBlock extends Block {
//    public static IntProperty MINING_PROGRESS;
    public static IntProperty RED_WIRE;
//    public static IntProperty GREEN_WIRE;
//    public static IntProperty BLUE_WIRE;
//    public static IntProperty YELLOW_WIRE;
    int hitPoints;

    public AbstractMxTBlock(Settings settings, int hitPoints) {
        super(settings);
        this.hitPoints = hitPoints;
//        this.setDefaultState(this.getStateManager().getDefaultState().with(MINING_PROGRESS, 0).with(RED_WIRE, 0).with(GREEN_WIRE, 0).with(BLUE_WIRE, 0).with(YELLOW_WIRE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
        builder.add(new Property[]{RED_WIRE});
//        builder.add(new Property[]{MINING_PROGRESS, RED_WIRE, GREEN_WIRE, BLUE_WIRE, YELLOW_WIRE});
    }

//    @Override
//    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
//
//        ItemStack itemStack = player.getMainHandStack();
//        if (itemStack.getItem() instanceof MxTBlockItem) {
//            player.
//        }
//    }
    public boolean canBeMined(World world, BlockPos blockPos, PlayerEntity player) {
        return true;
    }

    public int getHitPoints() {
        return hitPoints;
    }


    /*
    when slab or top slab next to a fluid block (water, lava or honey)
    block is fluid logged with a corresponding "fluid fall" liguid which is purely visible
    optional?
     */
}
