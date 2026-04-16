package io.github.bengman.carpentryadditions.block;

import java.util.EnumMap;
import java.util.Map;

import io.github.bengman.carpentryadditions.blockentity.ResawBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ResawBlock extends BaseEntityBlock {

    /* ---- Block Geometry Code ---- */

    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    static {
        SHAPES.put(Direction.NORTH, Shapes.or(
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(6, 2, 8, 16, 17, 13)));

        SHAPES.put(Direction.SOUTH, Shapes.or(
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(0, 2, 3, 10, 17, 8)));

        SHAPES.put(Direction.WEST, Shapes.or(
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(8, 2, 0, 13, 17, 10)));

        SHAPES.put(Direction.EAST, Shapes.or(
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(3, 2, 6, 8, 17, 16)));
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ResawBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /* ---- Block entity and logic ---- */

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ResawBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {

        if (!level.isClientSide) {

            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof ResawBlockEntity resawBE) {

                NetworkHooks.openScreen(
                        (ServerPlayer) player,
                        resawBE,
                        pos);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}