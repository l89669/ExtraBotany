package com.meteor.extrabotany;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibBlockName;
import com.meteor.extrabotany.common.lib.LibReference;

public class ExtraBotanyCreativeTab extends CreativeTabs{
	List list;
	
	public ExtraBotanyCreativeTab() {
		super(LibReference.MOD_ID);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return ItemBlockSpecialFlower.ofType(LibBlockName.NUMERON_DANDELIFE);
	}

	@Override
	public Item getTabIconItem() {
		return getIconItemStack().getItem();
	}
	
	@Override
	public void displayAllReleventItems(List list) {
		this.list = list;
		//Flowers
		addBlock(ModBlocks.specialFlower);
		//Mana Using Item
		addItem(ModItems.gaiatablet);
		addItem(ModItems.teleportpearl);
		addBlock(ModBlocks.pylon);
		//Relics
		addItem(ModItems.dice20);
		addItem(ModItems.hestiachastity);
		addItem(ModItems.hermestravelclothing);
		addItem(ModItems.aphroditegrace);
		addItem(ModItems.vrangerboots);
		addItem(ModItems.maxwelldemon);
		addItem(ModItems.excaliber);
		addItem(ModItems.excaliberfake);
		addItem(ModItems.vhandgun);
		addItem(ModItems.vpowerbattleaxe);
		addItem(ModItems.athenabless);
		addItem(ModItems.cronusphantom);
		addItem(ModItems.lokighostrick);
		addItem(ModItems.olympusguard);
		addItem(ModItems.cthulhueye);
		//Test
		addItem(ModItems.itemtest);
		//Basic
		addItem(ModItems.material);
		addItem(ModItems.boxs);
		addItem(ModItems.dungeonbox);
		addItem(ModItems.pill);
		//Spawn Card
		addItem(ModItems.lycorisgreen);
		addItem(ModItems.lycorispurple);
		addItem(ModItems.lycorisred);
		addItem(ModItems.lycorisrandom);		
		//Blocks
		addBlock(ModBlocks.gaiaquartz);
		addBlock(ModBlocks.gaiaquartzstairs);
		addBlock(ModBlocks.gaiaquartzslab);
		addBlock(ModBlocks.elvenquartz);
		addBlock(ModBlocks.elvenquartzstairs);
		addBlock(ModBlocks.elvenquartzslab);
		//Baubles
		addItem(ModItems.dog);
		//Bullets
		addItem(ModItems.bullet);
		//Weapons
		addItem(ModItems.gunphoenixblaster);
		addItem(ModItems.guntacticalshotgun);
		addItem(ModItems.snowballlauncher);
		addItem(ModItems.gunboomstick);
		addItem(ModItems.gunflintlock);
		addItem(ModItems.gunpistol);
		addItem(ModItems.gunshotgun);
		addItem(ModItems.recordA);
		addItem(ModItems.recordB);
		addItem(ModItems.recordC);
	}
	
	private void addItem(Item item) {
		item.getSubItems(item, this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(stack.getItem(), this, list);
	}

	private void addStack(ItemStack stack) {
		list.add(stack);
	}

}