package fr.tvbarthel.games.chasewhisply.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.tvbarthel.games.chasewhisply.model.bonus.Bonus;
import fr.tvbarthel.games.chasewhisply.model.bonus.BonusInventoryItemConsumer;
import fr.tvbarthel.games.chasewhisply.model.weapon.Weapon;

public class GameInformation implements Parcelable {
	protected long mTime;
	protected long mSpawningTime;
	protected long mTickingTime;
	protected Weapon mWeapon;
	protected int mMaxTargetOnTheField;
	protected TargetableItem mCurrentTarget;
	protected List<TargetableItem> mTargetableItems;
	protected List<DisplayableItem> mDisplayableItems;
	protected int mSceneWidth;
	protected int mSceneHeight;
	protected float mCurrentX;
	protected float mCurrentY;
	protected GameMode mGameMode;
	protected ScoreInformation mScoreInformation;

	/**
	 * Create a new GameInformation
	 *
	 * @param time         remaining time in millisecond
	 * @param spawningTime spawning time in millisecond
	 * @param weapon       weapon used for this game
	 */
	public GameInformation(long time, long spawningTime, Weapon weapon) {
		mScoreInformation = new ScoreInformation();
		mTime = time;
		mSpawningTime = spawningTime;
		mWeapon = weapon;
		mMaxTargetOnTheField = 0;
		mCurrentTarget = null;
		mTargetableItems = new ArrayList<TargetableItem>();
		mDisplayableItems = new ArrayList<DisplayableItem>();
	}

	/**
	 * Create a new GameInformation
	 *
	 * @param spawningTime spawning time in millisecond
	 * @param weapon       weapon used for this game
	 */
	public GameInformation(long spawningTime, Weapon weapon) {
		mScoreInformation = new ScoreInformation();
		mSpawningTime = spawningTime;
		mWeapon = weapon;
		mMaxTargetOnTheField = 0;
		mCurrentTarget = null;
		mTargetableItems = new ArrayList<TargetableItem>();
		mDisplayableItems = new ArrayList<DisplayableItem>();
	}

	public GameInformation(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		mScoreInformation = in.readParcelable(ScoreInformation.class.getClassLoader());
		mTime = in.readLong();
		mSpawningTime = in.readLong();
		mTickingTime = in.readLong();
		mWeapon = in.readParcelable(Weapon.class.getClassLoader());
		mMaxTargetOnTheField = in.readInt();
		mCurrentTarget = in.readParcelable(TargetableItem.class.getClassLoader());
		mTargetableItems = new ArrayList<TargetableItem>();
		in.readTypedList(mTargetableItems, TargetableItem.CREATOR);
		mDisplayableItems = new ArrayList<DisplayableItem>();
		in.readTypedList(mDisplayableItems, DisplayableItem.CREATOR);
		mGameMode = in.readParcelable(GameMode.class.getClassLoader());
		mSceneWidth = in.readInt();
		mSceneHeight = in.readInt();
		mCurrentX = in.readFloat();
		mCurrentY = in.readFloat();
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		out.writeParcelable(mScoreInformation, i);
		out.writeLong(mTime);
		out.writeLong(mSpawningTime);
		out.writeLong(mTickingTime);
		out.writeParcelable(mWeapon, i);
		out.writeInt(mMaxTargetOnTheField);
		out.writeParcelable(mCurrentTarget, i);
		out.writeTypedList(mTargetableItems);
		out.writeTypedList(mDisplayableItems);
		out.writeParcelable(mGameMode, i);
		out.writeInt(mSceneWidth);
		out.writeInt(mSceneHeight);
		out.writeFloat(mCurrentX);
		out.writeFloat(mCurrentY);
	}

	public static final Parcelable.Creator<GameInformation> CREATOR = new Parcelable.Creator<GameInformation>() {
		public GameInformation createFromParcel(Parcel in) {
			return new GameInformation(in);
		}

		public GameInformation[] newArray(int size) {
			return new GameInformation[size];
		}
	};

	/**
	 * Getters & Setters
	 */
	public Weapon getWeapon() {
		return mWeapon;
	}

	public long getTime() {
		return mTime;
	}

	public void setTime(long time) {
		mTime = time;
	}

	public long getSpawningTime() {
		return mSpawningTime;
	}

	public void setSpawningTime(long spawningTime) {
		mSpawningTime = spawningTime;
	}

	public void setTickingTime(long tickingTime) {
		mTickingTime = tickingTime;
	}

	public long getTickingTime() {
		return mTickingTime;
	}

	public void addTargetableItem(TargetableItem item) {
		mTargetableItems.add(item);
	}

	public void addDisplayableItem(DisplayableItem item) {
		mDisplayableItems.add(item);
	}

	public List<DisplayableItem> getItemsForDisplay() {
		final ArrayList<DisplayableItem> displayAll = new ArrayList<DisplayableItem>();
		displayAll.addAll(mDisplayableItems);
		displayAll.addAll(mTargetableItems);
		return displayAll;
	}

	public void setSceneWidth(int sceneWidth) {
		mSceneWidth = sceneWidth;
	}

	public int getSceneWidth() {
		return mSceneWidth;
	}

	public void setSceneHeight(int sceneHeight) {
		mSceneHeight = sceneHeight;
	}

	public int getSceneHeight() {
		return mSceneHeight;
	}

	public void setCurrentPosition(float x, float y) {
		mCurrentX = x;
		mCurrentY = y;
	}

	public float[] getCurrentPosition() {
		return new float[]{mCurrentX, mCurrentY};
	}

	/**
	 * get current target
	 *
	 * @return current target
	 */
	public TargetableItem getCurrentTarget() {
		return mCurrentTarget;
	}

	/**
	 * set current target
	 *
	 * @param t current TargetableItem targeted
	 */
	public void setCurrentTarget(TargetableItem t) {
		mCurrentTarget = t;
	}

	/**
	 * set current target to null
	 */
	public void removeTarget() {
		mCurrentTarget = null;
	}

	/**
	 * increase targets killed number
	 */
	public void targetKilled() {
		mTargetableItems.remove(mCurrentTarget);
		mScoreInformation.increaseNumberOfTargetsKilled();
		mScoreInformation.addLoot(mCurrentTarget.getDrop());
		mCurrentTarget = null;
	}

	/**
	 * used to know frag number
	 *
	 * @return number of frag
	 */
	public int getFragNumber() {
		return mScoreInformation.getNumberOfTargetsKilled();
	}

	/**
	 * increase bullets fired number
	 */
	public void bulletFired() {
		mScoreInformation.increaseNumberOfBulletsFired();
	}

	public void bulletMissed() {
		resetCombo();
		mScoreInformation.increaseNumberOfBulletsMissed();
	}

	public void earnExp(int expEarned) {
		mScoreInformation.increaseExpEarned(expEarned);
	}

	/**
	 * used to get combo number
	 *
	 * @return current combo
	 */
	public int getCurrentCombo() {
		return mScoreInformation.getCurrentCombo();
	}

	/**
	 * return maximum combo during the game
	 *
	 * @return max combo number
	 */
	public int getMaxCombo() {
		return mScoreInformation.getMaxCombo();
	}

	/**
	 * increase combo if conditions are filled
	 */
	public void stackCombo() {
		mScoreInformation.increaseCurrentCombo();
	}

	/**
	 * reset current combo counter
	 */
	public void resetCombo() {
		mScoreInformation.resetCurrentCombo();
	}

	/**
	 * increase score
	 *
	 * @param amount score you want to add to the current one
	 */
	public void increaseScore(int amount) {
		mScoreInformation.increaseScore(amount);
	}

	/**
	 * get current score
	 *
	 * @return current score
	 */
	public int getCurrentScore() {
		return mScoreInformation.getScore();
	}

	/**
	 * set  score
	 */
	public void setScore(int score) {
		mScoreInformation.setScore(score);
	}


	public int getBulletsFired() {
		return mScoreInformation.getmNumberOfBulletsFired();
	}

	public int getBulletsMissed() {
		return mScoreInformation.getNumberOfBulletsMissed();
	}

	public int getExpEarned() {
		return mScoreInformation.getExpEarned();
	}

	public int getMaxTargetOnTheField() {
		return mMaxTargetOnTheField;
	}

	public void setMaxTargetOnTheField(int maxTargetOnTheField) {
		mMaxTargetOnTheField = maxTargetOnTheField;
	}

	public int getCurrentTargetsNumber() {
		return mTargetableItems.size();
	}

	public ScoreInformation getScoreInformation() {
		return mScoreInformation;
	}

	public HashMap<Integer, Integer> getLoot() {
		final HashMap<Integer, Integer> lootQuantities = new HashMap<Integer, Integer>();
		for (Integer inventoryItemType : mScoreInformation.getLoot()) {
			int oldValue = 0;
			if (lootQuantities.containsKey(inventoryItemType)) {
				oldValue = lootQuantities.get(inventoryItemType);
			}
			lootQuantities.put(inventoryItemType, oldValue + 1);
		}
		return lootQuantities;
	}

	public int getNumberOfLoots() {
		return mScoreInformation.getLoot().size();
	}

	public GameMode getGameMode() {
		return mGameMode;
	}

	public void setGameMode(GameMode gameMode) {
		gameMode.getBonus().apply(this);
		mGameMode = gameMode;
	}

	public Bonus getBonus() {
		return mGameMode.getBonus();
	}

	public void useBonus(PlayerProfile playerProfile) {
		final Bonus currentBonus = mGameMode.getBonus();
		if (currentBonus instanceof BonusInventoryItemConsumer) {
			mGameMode.setBonus(((BonusInventoryItemConsumer) currentBonus).consume(playerProfile));
		}
	}

}