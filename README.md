# Marvel-Game

This game uses Java, Swing, and MVC architecture. It heavily realies on the concepts of OOP - especially abstraction, inheritance, and polymorphism. It was built over the course of
7 weeks as a group project for our CSEN401 (Computer programming lab) course.


## Game Description

The players take turns to fight the other player’s champions. The turns will keep going back and forth until a player is able to defeat all of the other player’s
champions which will make him the winner of the battle. During the battle, each player will use his champions to attack the opponent champions either
by using normal attacks or using special attacks/abilities. The battle takes place on a 5x5 grid. Each cell in the grid can either be empty, or contain a champion or obstacle/cover.

#### Champions

Champions are the fighters that each player will form his team from. Each champion will have
a certain type which influences how the champion deals damage to other types as well as how
much damage it will receive from them. The available types are:-
- Heroes: they deal extra damage when attacking villains.
- Villains: they deal extra damage when attacking heroes.
- Anti-Heroes: when being attacked or attacking a hero or villain, the antihero will always act as the opposite type. If attacking an antihero, damage is calculated normally.

Each Champion has these attributes : Health points, Mana (to use abilities), Normal attack damage,
Normal attack range, Speed, Condition, Actions per turn

#### Abilities 

These are special attacks that a champion can use. They are categorized under the following
categories:-
- Damaging abilities: Abilities that deal damage to the opponent champion(s) or covers.
- Healing abilities: Abilities that restore health points to friendly champion(s).
- Effect abilities: Abilities that can empower or weaken their targets by applying different
effects. These effects can last for multiple turns and will affect how the affected champion
interacts or reacts to abilities or attacks.
Example of some effects: stun, weaken, embrace, shield, silence, disarm.


Abilities have different targets and ranges. Some abilities are single target abilities which
affect only a single champion (or a cover in some cases) per use. Or can affect any champion
standing in a certain area (area of effect). These areas can be directional (Horizontal or
Vertical), or Circuilar (affect an area surrounding a central point). Finally, some abilities
can affect all friendly or opposing champions.
Each ability requires a certain amount of action points to be present in the champion
casting them as well as some mana. Also, each ability has a specific range of cells that
the target needs to be present in it in order for the ability to affect it.


#### Gameplay Flow

Each player will select his three champions to form his team. The champions will take turns
based on their speed. The champion with the highest speed (from all selected champions) will
begin acting first followed by the champion with the second highest speed and so on. When
the turn goes to a champion, the player controlling the champion can use him to carry out any
action as long as the champion has enough action points needed for this action and also enough
mana in case of using any of his abilities. After that, the champion can end his turn and the
turn will go to the next champion.
The turns will keep passing over the living champions till a player is able to defeat all of the
three champions of the opponent player. In this case, the game ends and the player with the
living champion will be declared the winner.

