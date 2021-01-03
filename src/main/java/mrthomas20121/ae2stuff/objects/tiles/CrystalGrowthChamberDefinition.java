package mrthomas20121.ae2stuff.objects.tiles;

import appeng.api.definitions.ITileDefinition;
import appeng.items.materials.ItemMaterial;
import appeng.items.materials.MaterialType;
import mrthomas20121.ae2stuff.objects.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Optional;

public class CrystalGrowthChamberDefinition implements ITileDefinition {

    private ItemStack stack;

    public CrystalGrowthChamberDefinition() {
        this.stack = MaterialType.CARD_SPEED.stack(1);
    }

    @Nonnull
    @Override
    public String identifier() {
        return "crystal_growth_chamber";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSameAs(ItemStack comparableStack) {
        return comparableStack == this.stack;
    }

    @Override
    public Optional<Item> maybeItem() {
        return Optional.empty();
    }

    @Override
    public Optional<ItemStack> maybeStack(int stackSize) {
        return Optional.empty();
    }

    @Override
    public Optional<? extends Class<? extends TileEntity>> maybeEntity() {
        return Optional.of(CrystalGrowthChamberTile.class);
    }

    @Override
    public boolean isSameAs(IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public Optional<Block> maybeBlock() {
        return Optional.empty();
    }

    @Override
    public Optional<ItemBlock> maybeItemBlock() {
        return Optional.empty();
    }
}
