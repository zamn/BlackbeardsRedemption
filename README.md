Blackbeard's Redemption
======================

A 2D platformer featuring Blackbeard as he fights his way back to civilization.

Wiki: www.wiki.zamn.net

=======================

My goals: 

1. Implement Menu data structure
2. Implement save file structure and saving functionality

Prerequisites
-------------
* Java 7 

# Game Story

You're a pirate captain who was overthrown by his crew and thrown overboard with nothing but your clothes and your (overly intelligent) talking parrot. You wake up washed up on a remote island in the middle of the ocean, but LUCKILY, on the island is the entrance to a tunnel that goes under the ocean and (hopefully) leads back to civilization. Platforming adventure ensues.

Pirate Ship Name: Red Death of the South

Your parrot will act predominantly as a tutorial navi/puzzle gimmick/straight man to comically counter the player character's pirate stupidity.

The game will be a straightforward level-based action-platformer.

## Characters

### Blackbeard
Bad ass mother fucker with a parrot.

Weapons:

* Hook → Starts off with hook. 
* Sword → upgradeable?

### Dat
Bad ass parrot companion to Blackbeard.

Attacks: 
* Charge 
* Acid Shit 
- Upgradable? 
– Can we change its color?

### Arbok Widow snake. We're an invader of his territory therefore he hates you. They've owned this island since they crashed from a plane many years ago.

Attacks: 
* Shoots venom at you. (part 1)) 
* Wrap you up and squeeze you.(part 1) 
* Bite guy. (part 1) 
* Tail whip. (part 2) 
* Laserzs. (part 2) 
* Turns you to stone! (part 3) 
=Three Mini-Boss Battles=

###Sensor Rat

Arboc met the rat when he was a little snake and decided not to kill it. Through the years Senor Rat has worked for Arboc catching food for him as he is his master.

Attacks: 
* Ganks you with a knife. 

# Game Design
Multiple levels, not a single level

##Entity

This class is going to be the superclass for all objects with humanoid properties in the game. Such as:

* Enemies
* Environment Obstacles (Spikes, lava)

### Instance Variables
```Java
String name; // for debugging purposes 
int health; // could possibly be double if attacks are possible of doing partial damage.
boolean dead; // maybe?
boolean attackable; // if its a spike then you can't really attack this (OR CAN YOU!?)
```

###Member Functions
```Java
/**
* Decreases enemies hitpoints using weapon.
*/
public abstract void attack(Entity e);
/**
* Not sure how we'll lay out each level and such so this needs to be refined.
*/
public abstract void move(int steps);
public abstract boolean isAttackable();
public abstract boolean isDead();
public abstract String getName();
public abstract int getHealth();
public abstract void setHealth(int newHealth);
public abstract void setWeapon(Weapon w);
```

## Level

This class is going to be the interface for each level.

### Instance Variables

```Java
// fill in
```

### Member Functions
```Java
//fill in
```


#Tasks

<table>
	<tr>
		<th >Name </th><th >Difficulty </th><th >Assigned To </th>
	</tr>
	<tr>
		<td >Title Screen (+Menus/Buttons) </td><td > Easy-Medium </td><td > Sean </td>
	</tr>
	<tr>
		<td >Music/Sound Effects loader + player</td><td >Medium</td><td >Adam</td>
	</tr>
	<tr>
		<td >Animations </td><td > Super easy </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Load/Save Settings </td><td > Medium </td><td > Sean</td>
	</tr>
	<tr>
		<td >Choose/Apply Settings </td><td > Super Hard </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Load/Save Map </td><td > Medium </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Scroll Map </td><td > Super Easy </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Map Editor </td><td > Super Hard </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Game Clock </td><td > Easy-Medium </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Character Movement </td><td > Easy </td><td > Nobody</td>
	</tr>
	<tr>
		<td >Entity Class </td><td > Easy </td><td > Bryan, Adam</td>
	</tr>
	<tr>
		<td >Monster Class </td><td > ? </td><td > Bryan, Adam</td>
	</tr>
	<tr>
		<td >Player Class </td><td > ? </td><td > Bryan, Adam</td>
	</tr>
	<tr>
		<td >Weapons </td><td > ? </td><td > Bryan, Adam </td>
	</tr>
</table>

