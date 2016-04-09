package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.ExtraBotany;
import com.meteor.extrabotany.common.entity.EntityLycorisradiata;
import com.meteor.extrabotany.common.entity.EntityLycorisradiataGreen;
import com.meteor.extrabotany.common.entity.EntityLycorisradiataPurple;
import com.meteor.extrabotany.common.entity.EntityLycorisradiataRed;
import com.meteor.extrabotany.common.items.relic.ItemDice20;
import com.meteor.extrabotany.common.items.relic.ItemExcaliber;
import com.meteor.extrabotany.common.items.relic.ItemExcaliberFake;
import com.meteor.extrabotany.common.items.relic.ItemHestiaChastity;
import com.meteor.extrabotany.common.items.relic.ItemVHandgun;
import com.meteor.extrabotany.common.items.relic.ItemVPowerBattleaxe;
import com.meteor.extrabotany.common.items.relic.ItemVRangerBoots;
import com.meteor.extrabotany.common.lib.LibEntityName;
import com.meteor.extrabotany.common.lib.LibItemName;
import com.meteor.extrabotany.common.lib.LibOreDictName;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {
	public static Item prismaticshard;
	public static Item blankcard;
	public static Item gaiaessence;
	public static Item astralforce;
	public static Item gaiatablet;
	public static Item petal_red;
	public static Item petal_green;
	public static Item petal_purple;
	public static Item empty_dice;
	public static Item dice20;
	public static Item excaliber;
	public static Item excaliberfake;
	public static Item vpowerbattleaxe;
	public static Item vhandgun;
	public static Item vrangerboots;
	public static Item lycorisgreen;
	public static Item lycorispurple;
	public static Item lycorisred;
	public static Item lycorisrandom;
	public static Item hestiachastity;
	
	public static void init() {
		prismaticshard = new ItemMods(LibItemName.PRISMATICSHARD);
		blankcard = new ItemMods(LibItemName.BLANKCARD);
		gaiaessence = new ItemMods(LibItemName.GAIAESSENCE);
		astralforce = new ItemMods(LibItemName.ASTRALFORCE);
		petal_red = new ItemMods(LibItemName.PETALRED);
		petal_green = new ItemMods(LibItemName.PETALGREEN);
		petal_purple = new ItemMods(LibItemName.PETALPURPLE);
		empty_dice = new ItemMods(LibItemName.EMPTYDICE);
		

		excaliber = new ItemExcaliber().setCreativeTab(ExtraBotany.tabExtraBotany);
		excaliberfake = new ItemExcaliberFake().setCreativeTab(ExtraBotany.tabExtraBotany);
		vpowerbattleaxe = new ItemVPowerBattleaxe().setCreativeTab(ExtraBotany.tabExtraBotany);
		vhandgun = new ItemVHandgun().setCreativeTab(ExtraBotany.tabExtraBotany);
		vrangerboots = new ItemVRangerBoots(3, LibItemName.VRANGERBOOTS).setCreativeTab(ExtraBotany.tabExtraBotany);
		dice20 = new ItemDice20().setCreativeTab(ExtraBotany.tabExtraBotany);
		hestiachastity = new ItemHestiaChastity(LibItemName.HESTIACHASTITY).setCreativeTab(ExtraBotany.tabExtraBotany);
		
		gaiatablet = new ItemGaiaTablet().setCreativeTab(ExtraBotany.tabExtraBotany);
		
		lycorisgreen = new ItemSpawnCardLycorisGreen(LibEntityName.LYCORISGREEN);
		lycorisred = new ItemSpawnCardLycorisRed(LibEntityName.LYCORISRED);
		lycorispurple = new ItemSpawnCardLycorisPurple(LibEntityName.LYCORISPURPLE);
		lycorisrandom = new ItemSpawnCardLycorisRandom("lycorisrandom");
	
		OreDictionary.registerOre(LibOreDictName.GAIA_ESSENCE, new ItemStack(gaiaessence));
		OreDictionary.registerOre(LibOreDictName.PRISMATIC_SHARD, new ItemStack(prismaticshard));
		OreDictionary.registerOre(LibOreDictName.BLANK_CARD, new ItemStack(blankcard));
		OreDictionary.registerOre(LibOreDictName.ASTRAL_FORCE, new ItemStack(astralforce));
	}

}