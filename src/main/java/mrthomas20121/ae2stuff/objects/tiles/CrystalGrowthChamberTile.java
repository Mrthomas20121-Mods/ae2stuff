package mrthomas20121.ae2stuff.objects.tiles;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.Upgrades;
import appeng.api.definitions.ITileDefinition;
import appeng.api.implementations.tiles.ICrystalGrowthAccelerator;
import appeng.api.networking.GridFlags;
import appeng.items.materials.MaterialType;
import appeng.parts.automation.DefinitionUpgradeInventory;
import appeng.parts.automation.UpgradeInventory;
import appeng.tile.grid.AENetworkPowerTile;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.util.inv.InvOperation;
import appeng.util.inv.WrapperChainedItemHandler;
import appeng.util.inv.filter.IAEItemFilter;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.IItemHandler;
import appeng.api.implementations.items.IGrowableCrystal;

import javax.annotation.Nonnull;

public class CrystalGrowthChamberTile extends AENetworkPowerTile implements ITickable, ICrystalGrowthAccelerator {

    private final AppEngInternalInventory inputInventory = new AppEngInternalInventory( this, 1 );
    private final AppEngInternalInventory cellInventory = new AppEngInternalInventory( this, 1 );
    private final IItemHandler internalInventory = new WrapperChainedItemHandler( this.inputInventory, this.cellInventory);
    private final UpgradeInventory upgrades;

    private boolean isCached = false;
    private int priority = 0;
    private long ticks = 0;


    public CrystalGrowthChamberTile() {
        final ITileDefinition crystalGrowthChamberDefinition = new CrystalGrowthChamberDefinition();

        this.upgrades = new DefinitionUpgradeInventory( crystalGrowthChamberDefinition, this, this.getUpgradeSlots() );
        this.setInternalMaxPower(10000);
        this.setInternalPowerFlow(AccessRestriction.WRITE);
        this.getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        this.inputInventory.setFilter(new InputInventoryFilter());
    }

    private int getUpgradeSlots()
    {
        return 5;
    }

    @Override
    public void onChangeInventory(IItemHandler inv, int slot, InvOperation mc, ItemStack removed, ItemStack added) {
        if( inv == this.cellInventory ) {
            this.isCached = false;
        }
    }

    @Nonnull
    @Override
    public IItemHandler getInternalInventory() {
        return internalInventory;
    }

    @Override
    public void update() {
        int installedUpgrades = upgrades.getInstalledUpgrades(Upgrades.SPEED);
        int power = 100*(installedUpgrades);
        double half_power = this.getInternalMaxPower()/2;

        if(this.getInternalCurrentPower() >= power && this.getInternalCurrentPower() >= half_power) {
            for(int i = 0; i<cellInventory.getSlots(); i++) {
                ItemStack stack = cellInventory.getStackInSlot(i);
                if(stack.isEmpty()) continue;
                if(stack.getItem() instanceof IGrowableCrystal) {
                    for(int j = 0; j<installedUpgrades; j++) {
                        IGrowableCrystal item = (IGrowableCrystal)stack.getItem();
                        item.triggerGrowth(stack);
                        this.extractAEPower(power, Actionable.SIMULATE);
                    }
                }
                else {
                    int redstone = containStack(new ItemStack(Items.REDSTONE, 1));
                    int nether_quartz = containStack(new ItemStack(Items.QUARTZ, 1));
                    int charged_certus_quartz = containStack(MaterialType.CERTUS_QUARTZ_CRYSTAL_CHARGED.stack(1));

                    if(redstone > 0 && nether_quartz > 0 && charged_certus_quartz > 0) {
                        decreaseStack(redstone);
                        decreaseStack(nether_quartz);
                        decreaseStack(charged_certus_quartz);
                        cellInventory.insertItem(findFirstEmptySlot(), MaterialType.FLUIX_CRYSTAL.stack(1), false);
                    }
                }
            }
        }
    }

    public int findFirstEmptySlot() {
        for(int i = 0; i<cellInventory.getSlots(); i++) {
            ItemStack itemStack = cellInventory.getStackInSlot(i);
            if(itemStack.isEmpty()) return i;
        }
        return cellInventory.getSlots();
    }

    public void decreaseStack(int slot) {
        ItemStack stack = cellInventory.getStackInSlot(slot);
        int count = stack.getCount();
        stack.setCount(count-1);
        cellInventory.setStackInSlot(slot, stack);
    }

    public int containStack(ItemStack stack) {
        for(int i = 0; i<cellInventory.getSlots(); i++) {
            ItemStack itemStack = cellInventory.getStackInSlot(i);
            if(stack.isEmpty()) continue;
            if(itemStack.isItemEqual(stack)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean isPowered() {
        return this.getInternalMaxPower()>0;
    }

    @SuppressWarnings("unchecked")
    public void updateHandler() {
        if(!this.isCached) {

        }
    }

    private class InputInventoryFilter implements IAEItemFilter
    {
        @Override
        public boolean allowExtract( IItemHandler inv, int slot, int amount )
        {
            return false;
        }

        @Override
        public boolean allowInsert( IItemHandler inv, int slot, ItemStack stack )
        {
            if(CrystalGrowthChamberTile.this.isPowered())
            {
                CrystalGrowthChamberTile.this.updateHandler();
            }
            return false;
        }
    }
}
