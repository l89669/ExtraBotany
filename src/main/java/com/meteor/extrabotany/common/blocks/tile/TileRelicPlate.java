package com.meteor.extrabotany.common.blocks.tile;

import java.util.List;

import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.lib.LibBlockName;
import com.meteor.extrabotany.common.recipes.ModRelicPlateRecipe;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.BlockSpecialFlower;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.item.ModItems;

public class TileRelicPlate extends TileMod implements ISparkAttachable {

	public static final int MAX_MANA = TilePool.MAX_MANA / 2;
	private static final int[][] LAPIS_BLOCKS = {
		{ 1, 0, }, { -1, 0 }, { 0, 1 }, { 0, -1 }
	};

	private static final int[][] LIVINGROCK_BLOCKS = {
		{ 0, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 }
	};

	private static final String TAG_MANA = "mana";

	int mana;

	public static MultiblockSet makeMultiblockSet() {
		Multiblock mb = new Multiblock();

		for(int[] l : LAPIS_BLOCKS)
			mb.addComponent(l[0], 0, l[1], ModBlocks.gaiaquartz, 0);
		for(int[] l : LIVINGROCK_BLOCKS)
			mb.addComponent(l[0], 0, l[1], vazkii.botania.common.block.ModBlocks.livingrock, 0);

		mb.addComponent(0, 1, 0, ModBlocks.relicplate, 0);
		mb.setRenderOffset(0, 1, 0);

		return mb.makeSet();
	}

	@Override
	public void updateEntity() {
		boolean removeMana = true;

		if(hasValidPlatform()) {
			List<EntityItem> items = getItems();
			if(ModRelicPlateRecipe.craft1(items) || ModRelicPlateRecipe.craft2(items)) {
				removeMana = false;
				ISparkEntity spark = getAttachedSpark();
				if(spark != null) {
					List<ISparkEntity> sparkEntities = SparkHelper.getSparksAround(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
					for(ISparkEntity otherSpark : sparkEntities) {
						if(spark == otherSpark)
							continue;

						if(otherSpark.getAttachedTile() != null && otherSpark.getAttachedTile() instanceof IManaPool)
							otherSpark.registerTransfer(spark);
					}
				}
				if(mana > 0)
					doParticles();

				if(mana >= MAX_MANA && !worldObj.isRemote) {
					EntityItem item = items.get(0);
					for(EntityItem otherItem : items)
						if(otherItem != item)
							otherItem.setDead();
						else if(ModRelicPlateRecipe.craft1(items))item.setEntityItemStack(new ItemStack(com.meteor.extrabotany.common.items.ModItems.material,1,3));
						else if(ModRelicPlateRecipe.craft2(items))item.setEntityItemStack(new ItemStack(com.meteor.extrabotany.common.items.ModItems.dice20));
					item.worldObj.playSoundAtEntity(item, "botania:terrasteelCraft", 1F, 1F);
					mana = 0;
					worldObj.func_147453_f(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(worldObj, xCoord, yCoord, zCoord);
				}
			}
		}

		if(removeMana)
			recieveMana(-500);
	}

	void doParticles() {
		if(worldObj.isRemote) {
			int ticks = (int) (100.0 * ((double) getCurrentMana() / (double) MAX_MANA));

			int totalSpiritCount = 3;
			double tickIncrement = 360D / totalSpiritCount;

			int speed = 5;
			double wticks = ticks * speed - tickIncrement;

			double r = Math.sin((ticks - 100) / 10D) * 2;
			double g = Math.sin(wticks * Math.PI / 180 * 0.55);

			for(int i = 0; i < totalSpiritCount; i++) {
				double x = xCoord + Math.sin(wticks * Math.PI / 180) * r + 0.5;
				double y = yCoord + 0.25 + Math.abs(r) * 0.7;
				double z = zCoord + Math.cos(wticks * Math.PI / 180) * r + 0.5;

				wticks += tickIncrement;
				float[] colorsfx = new float[] {
						0F, (float) ticks / (float) 100, 1F - (float) ticks / (float) 100
				};
				Botania.proxy.wispFX(worldObj, x, y, z, colorsfx[2], colorsfx[2], colorsfx[1], 0.85F, (float)g * 0.05F, 0.25F);
				Botania.proxy.wispFX(worldObj, x, y, z, colorsfx[2], colorsfx[2], colorsfx[1], (float) Math.random() * 0.1F + 0.1F, (float) (Math.random() - 0.5) * 0.05F, (float) (Math.random() - 0.5) * 0.05F, (float) (Math.random() - 0.5) * 0.05F, 0.9F);

				if(ticks == 100)
					for(int j = 0; j < 15; j++)
						Botania.proxy.wispFX(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, colorsfx[2], colorsfx[2], colorsfx[1], (float) Math.random() * 0.15F + 0.15F, (float) (Math.random() - 0.5F) * 0.125F, (float) (Math.random() - 0.5F) * 0.125F, (float) (Math.random() - 0.5F) * 0.125F);
			}
		}
	}
	
	@Override
	public boolean canRecieveManaFromBursts() {
		List<EntityItem> items = getItems();
		if(ModRelicPlateRecipe.craft1(items) == true)
			return ModRelicPlateRecipe.craft1(getItems());
		else if(ModRelicPlateRecipe.craft2(items) == true)
			return ModRelicPlateRecipe.craft2(getItems());
		else return ModRelicPlateRecipe.craft1(getItems());
	}
	
	@Override
	public boolean areIncomingTranfersDone() {
		List<EntityItem> items = getItems();
		if(ModRelicPlateRecipe.craft1(items) == true)
			return !ModRelicPlateRecipe.craft1(getItems());
		else if(ModRelicPlateRecipe.craft2(items) == true)
			return !ModRelicPlateRecipe.craft2(getItems());
		else return !ModRelicPlateRecipe.craft1(getItems());
	}

	List<EntityItem> getItems() {
		return worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1));
	}

	boolean hasValidPlatform() {
		return checkAll(LAPIS_BLOCKS, Blocks.lapis_block) && checkAll(LIVINGROCK_BLOCKS, ModBlocks.gaiaquartz);
	}

	boolean checkAll(int[][] positions, Block block) {
		for (int[] position : positions) {
			int[] positions_ = position;
			if(!checkPlatform(positions_[0], positions_[1], block))
				return false;
		}

		return true;
	}

	boolean checkPlatform(int xOff, int zOff, Block block) {
		return worldObj.getBlock(xCoord + xOff, yCoord - 1, zOff + zCoord) == block;
	}

	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		cmp.setInteger(TAG_MANA, mana);
	}

	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		mana = cmp.getInteger(TAG_MANA);
	}

	@Override
	public int getCurrentMana() {
		return mana;
	}

	@Override
	public boolean isFull() {
		return mana >= MAX_MANA;
	}

	@Override
	public void recieveMana(int mana) {
		this.mana = Math.max(0, Math.min(MAX_MANA, this.mana + mana));
		worldObj.func_147453_f(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
	}


	@Override
	public boolean canAttachSpark(ItemStack stack) {
		return true;
	}

	@Override
	public void attachSpark(ISparkEntity entity) {
		// NO-OP
	}

	@Override
	public ISparkEntity getAttachedSpark() {
		List<ISparkEntity> sparks = worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
		if(sparks.size() == 1) {
			Entity e = (Entity) sparks.get(0);
			return (ISparkEntity) e;
		}

		return null;
	}

	@Override
	public int getAvailableSpaceForMana() {
		return Math.max(0, MAX_MANA - getCurrentMana());
	}
}