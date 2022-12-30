package com.canieat;

import net.runelite.client.config.*;

@ConfigGroup("canieat")
public interface CanIEatConfig extends Config
{

	enum FoodTypes
	{
		Potato_Cheese,
		Pineapple_Pizza
	}

	enum RestoreTypes
	{
		Prayer_Potion,
		Super_Restore
	}

	@ConfigItem(
			keyName = "hitpoints",
			name = "Enable Food Warning",
			description = "Enable a notification when able to eat food",
			position = 1
	)
	default boolean getHitpointsEnabled(){return false;}

	@ConfigSection(
			name = "Food Options",
			description = "Various food options",
			position = 3,
			closedByDefault = true
	)
	String typeFoods = "foodTypes";

	@ConfigItem(
			keyName = "typeFood",
			name = "Food Type?",
			description = "This is the active type of food",
			position = 4,
			section = typeFoods
	)
	default FoodTypes foodTypes() {return FoodTypes.Potato_Cheese;}

	@ConfigItem(
			keyName = "prayer",
			name = "Enable Prayer Warning",
			description = "Enable notifications for when to drink for prayer restoration",
			position = 2
	)
	default boolean getPrayerEnabled() {return false;}

	@ConfigSection(
			name = "Prayer Restoration Options",
			description = "Prayer Restoration Options",
			position = 5,
			closedByDefault = true
	)
	String typeRestores = "restoreTypes";

	@ConfigItem(
			keyName = "typeRestoration",
			name = "Restore Type?",
			description = "This is the active type of prayer restoration",
			position = 6,
			section = typeRestores
	)
	default RestoreTypes restoreTypes(){return RestoreTypes.Prayer_Potion;}

	@ConfigItem(
			keyName = "holyWrench",
			name = "Using Holy Wrench?",
			description = "This is in my inventory as well",
			position = 7,
			section = typeRestores
	)
	default boolean HolyWrench(){return false;}

}
