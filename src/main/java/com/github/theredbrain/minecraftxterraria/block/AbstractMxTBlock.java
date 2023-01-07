package com.github.theredbrain.minecraftxterraria.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;

public abstract class AbstractMxTBlock extends Block {
    public static IntProperty MINING_PROGRESS;
    public static IntProperty RED_WIRE;
    public static IntProperty GREEN_WIRE;
    public static IntProperty BLUE_WIRE;
    public static IntProperty YELLOW_WIRE;
    public AbstractMxTBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
        builder.add(new Property[]{MINING_PROGRESS, RED_WIRE, GREEN_WIRE, BLUE_WIRE, YELLOW_WIRE});
    }

    /*
    when slab or top slab next to a fluid block (water, lava or honey)
    block is fluid logged with a corresponding "fluid fall" liguid which is purely visible
    optional?
     */

    static {
        RED_WIRE = IntProperty.of("red_wire", 0, 2);
        GREEN_WIRE = IntProperty.of("green_wire", 0, 2);
        BLUE_WIRE = IntProperty.of("blue_wire", 0, 2);
        YELLOW_WIRE = IntProperty.of("yellow_wire", 0, 2);
    }
}
