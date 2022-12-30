package com.canieat;

import com.google.inject.Provides;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
		name = "Can I Eat",
		description = "Send a notification when player is able to eat or drink",
		tags = {"health", "hitpoints", "notifications", "prayer"},
		enabledByDefault = false
)
public class CanIEatPlugin extends Plugin
{
	@Inject
	private Notifier notifier;

	@Inject
	private Client client;

	@Inject
	private CanIEatConfig config;

	private boolean notifyHitpoints = true;
	private boolean notifyPrayer = true;

	@Provides
	private CanIEatConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CanIEatConfig.class);
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{

		if (checkLowHitpoints())
		{
			notifier.notify("You can eat!");
		}

		if (checkLowPrayer())
		{
			notifier.notify("You can drink a prayer restore!");
		}
		{
			return;
		}
	}

	private boolean checkLowHitpoints()
	{
		int heal;
		switch (config.foodTypes())
		{
			case Potato_Cheese:
				heal = 16;
				break;

			case Pineapple_Pizza:
				heal = 11;
				break;

			default:
				heal = 0;
				break;
		}

		if (!config.getHitpointsEnabled())
		{
			return false;
		}

		if (client.getBoostedSkillLevel(Skill.HITPOINTS) + client.getVarbitValue(Varbits.NMZ_ABSORPTION) <= (client.getRealSkillLevel(Skill.HITPOINTS) - heal))
		{
			if (!notifyHitpoints)
			{
				notifyHitpoints = true;
				return true;
			}
		}
		else
		{
			notifyHitpoints = false;
		}

		return false;
	}

	private boolean checkLowPrayer()
	{
		int realPrayerLevel = client.getRealSkillLevel(Skill.PRAYER);

		double restore;
		switch (config.restoreTypes())
		{
			case Prayer_Potion:
				if (config.HolyWrench())
				{
					restore = (7 + (realPrayerLevel * 0.27));
				} else
				{
					restore = (7 + (realPrayerLevel * 0.25));
				}
				break;

			case Super_Restore:
				if (config.HolyWrench())
				{
					restore = (8 + (realPrayerLevel * 0.27));
				} else
				{
					restore = (8 + (realPrayerLevel * 0.25));
				}
				break;

			default:
				restore = 0;
				break;



		}

		if (!config.getPrayerEnabled())
		{
			return false;
		}

		if (client.getBoostedSkillLevel(Skill.PRAYER) <= (client.getRealSkillLevel(Skill.PRAYER) - restore))
		{
			if (!notifyPrayer)
			{
				notifyPrayer = true;
				return true;
			}
		}
		else
		{
			notifyPrayer = false;
		}

		return false;
	}
}

