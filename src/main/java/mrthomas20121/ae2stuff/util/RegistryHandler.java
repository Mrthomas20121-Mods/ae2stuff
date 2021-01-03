package mrthomas20121.ae2stuff.util;

import mrthomas20121.ae2stuff.Ae2Stuff;
import mrthomas20121.ae2stuff.objects.blocks.BlockCrystalGrowthChamber;
import mrthomas20121.ae2stuff.objects.blocks.Blocks;
import mrthomas20121.ae2stuff.objects.items.ItemBlockStuff;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class RegistryHandler {

    private static ArrayList<Item> items = new ArrayList<>();
    private static ArrayList<Block> blocks = new ArrayList<>();

    private static CreativeTabs tab = new CreativeTabs("ae2stuff") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.crystal_growth_chamber);
        }
    };

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        for(Block block: blocks) {
            ItemBlockStuff itemBlock = new ItemBlockStuff(block);
            itemBlock.setRegistryName(block.getRegistryName());
            itemBlock.setTranslationKey(itemBlock.getTranslationKey());
            r.register(itemBlock);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();
        register(r, new BlockCrystalGrowthChamber(), "crystal_growth_chamber");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for(Block block: blocks) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
    }

    public static void register(IForgeRegistry<Block> r, Block block, String name) {
        block.setRegistryName(Ae2Stuff.MODID, name);
        block.setTranslationKey(Ae2Stuff.MODID+"."+name);
        block.setCreativeTab(tab);
        r.register(block);
        blocks.add(block);
    }

    public static void register(IForgeRegistry<Item> r, Item item, String name) {
        item.setRegistryName(Ae2Stuff.MODID, name);
        item.setTranslationKey(Ae2Stuff.MODID+"."+name);
        item.setCreativeTab(tab);
        r.register(item);
        items.add(item);
    }
}
