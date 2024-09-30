# Banked Experience [![Plugin Installs](http://img.shields.io/endpoint?url=https://api.runelite.net/pluginhub/shields/installs/plugin/banked-experience)](https://runelite.net/plugin-hub/TheStonedTurtle) [![Plugin Rank](http://img.shields.io/endpoint?url=https://api.runelite.net/pluginhub/shields/rank/plugin/banked-experience)](https://runelite.net/plugin-hub)
Adds a new side panel for calculating how much experience you have banked. While this plugin tries to include the most
common training methods it is not perfect and some may be missing. 


## How It Works
This plugin searches through your bank for specific items that reward experience, such as: logs, ores, herbs, and bones.
Items that have multiple training Activities will be given a drop-down so you can select the proper training method.

**In order for the UI to register Inventory changes you must select a new skill via the dropdown**.
This was done on purpose to prevent performance issues from constantly refreshing the UI.

You must view your bank, or other inventories, while the plugin is enabled for it to register what items you have.
This data does not persist between client sessions so closing the client will require you to redo this step.


## Activities
An `Activity` is a way to consume an item that either rewards experience, produces items, or both. 

Items that take multiple steps have their Activities split between the different core items. For example when fletching logs into bows the logs have the cutting activity while the unstrung bows have the stringing activity. 


## Config Options
Like most plugins the Banked Experience plugin includes some config options to help customize its functionality.
See the relative section for each config option for an in-depth description.

| Name 														| Enabled by Default|
| :--- 														| :-----: |
| [Include output items](#include-output-items)				| &check; |
| [Show required secondaries](#show-required-secondaries)	| X |
| [Respect level requirements](#respect-level-requirements)	| &check; |
| [Include seed vault](#include-seed-vault)					| &check; |
| [Include player inventory](#include-player-inventory)		| X |
| [Include looting bag](#include-looting-bag)				| X |


### Include output items
<details>
<p>
<summary>Details</summary>

This option is **Enabled** by default.

Some Activities produce items that reward experience, such as converting logs into unstrung bows. This config option controls
whether or not the plugin will include the items Activities produce are included in the quantity of items banked.

For example, you have 100 yew logs and 100 yew longbows (u) and you are converting the yew logs to more yew longbows (u).
With the option enabled you would see 200 yew longbows (100 from logs 100 from bank), with it disabled you would just see 100 (from bank)
</p>
</details>

### Show required secondaries
<details>
<summary>Details</summary>
<p>

This option is **Disabled** by default

Some activities require additional items to complete the activity, such as making potions. While this plugin does not support
limiting experience based on required secondaries it can calculate how many you will need for the activity.

When enabled a new UI section will be added that displays how many secondaries are required, when disabled this section is hidden.
</p>
</details>

### Respect level requirements
<details>
<summary>Details</summary>
<p>

This option is **Enabled** by default

Most activities require a specific level in order to be completed. When this option is enabled activities which you lack
the required level for will be excluded from the list, when disabled all activities will be available regardless of your current level.

**Level limitation is based off current level and does not account for levels gained from other banked experience**
</p>
</details>

### Include seed vault
<details>
<summary>Details</summary>
<p>

This option is **Enabled** by default

Controls whether the items stored inside the Seed Vault (located inside the Farming Guild) will be included in the calculations.
This requires visiting the seed vault during your client session.
</p>
</details>

### Include player inventory
<details>
<summary>Details</summary>
<p>

This option is **Disabled** by default.

**It is highly recommend to keep this option disabled as the Inventory changes frequently. This feature was added for UIM who don't have a bank**

Controls whether the items inside your Inventory will be included in the calculations. 
</p>
</details>

### Include looting bag
<details>
<summary>Details</summary>
<p>

This option is **Disabled** by default

Controls whether the items stored inside your Looting Bag will be included in the calculations.
This feature requires checking your looting bag, using items on it or picking up items while its opened does not work.
</p>
</details>


## F.A.Q.
<details>
  <summary>Q: How do I ignore specific items from the calculator?</summary>
  <p>

You can right-click items inside the grid to Ignore/Include them. Ignored items will have a red background color.
  </p>
</details>

<details>
  <summary>Q: I only want to use part of my banked resources, how can i specify how many of a specific item I want to use?</summary>
  <p>

To limit the amount of a specific items you should withdraw all but the amount you want to calculate from your bank. Items in your inventory are not included in the calculations by default
  </p>
</details>

<details>
  <summary>Q: I visited my bank but the UI hasn't updated, what do I do?</summary>
  <p>

The UI does not automatically update when your bank content changes, you must change skills for the updates to be applied or click the Refresh Button underneath the item grid. This was done intentionally to minimize performance issues from constantly refreshing the UI.
  </p>
</details>

<details>
  <summary>Q: How can I limit experience based on the secondaries I have banked?</summary>
  <p>

This is not currently possible. Some secondaries, like coal, are split between multiple activities which would require a prioritization system that I'm not sure how to implement.  
  </p>
</details>

<details>
  <summary>Q: What does the orange background color inside the item grid mean?</summary>
  <p>

This means the current selected activity is RNG based and may affect the accuracy of the calculations. 
  </p>
</details>
