package mrthomas20121.ae2stuff.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockCrystalGrowthChamber extends Block {

    public BlockCrystalGrowthChamber() {
        super(Material.IRON);
        this.setSoundType(SoundType.METAL);
        this.setHardness(5);
        this.setHarvestLevel("pickaxe", 2);
    }
}
