# Java2DTopDownShooter
### Game Title: A Fistful O'Brains
#### Solo Project

### Descprition
“A Fistful O’ Brains” is an endless survival game in which we (as the player) are challenged with defending the homestead ranch from ever-increasing waves of zombies. The zombies spawn randomly around the perimeter of the screen and move towards the base in the middle. Using your six-shooter, the player is free to move around the map and can shoot in the direction of the mouse (from the payer) to eradicate the attacking zombies. The remaining health of the base is represented by a health bar. As the player progresses, the dynamic difficulty increases. There are a total of 5 difficulties represented by a zombie head in the top left of the screen that evolves as the in-game difficulty increases.

### Art / Aesthetic
I created my own pixel art for all of the sprites in my game. Using the website piskelapp.com, I was able to create and animate sprites to be used across my game.

### Mechanics
#### Shooting
By using the .Math() library, I was able to calculate the angle from the player to the mouse. This was done using the Math.atan() to calculate the inverse the tangent. By using these angles, the bullet will travel in the direction of the mouse from the player.

#### Movement
The player can move using WASD or the arrow keys. This was done by creating new KeyPressed() and KeyReleased() methods for the arrow keys. For shooting, the player can aim and shood using either the trackpad and SPACE or by using a mouse and left-click.

#### Boundaries
The player cannot leave the boundary of the screen and cannot pass through the base in the middle of the screen. Bullets will despawn when colliding with the screen boundary, base, and enemies (also despawns the enemy).

#### Power Ups
The default time cooldown between shots is set to .500s, this is to prevent the player from spamming bullets, which would make the game far too easy. However the firerate power-up decreases this cooldown to .175s and stays active for 10 seconds. The Base health is capped at 100 and can be increased (repaired) by 25 upon picking up a hammer power-up. The probability for a drop starts at 0%, after each kill a check occurs to drop a power-up, if this check fails the probability is increased by 1%. When a power-up does drop, the probability is reset to 0%.

#### Difficulty
The game starts at difficulty 1 which has a max capacity of zombies (in movement) of 2, when a zombie collides with the base, they remain there and continue to do damage until they are shot or until the base is destroyed. However, when a moving zombie collides with the base, they are moved into the “AttackingEnemy” list and another ‘moving’ zombie will spawn. The following are the scores required for each difficulty and the max zombies in movement for each difficulty.
- LVL 1 (score < 25): Max 2 (moving zombies)
= LVL 2 (25 <score < 50): Max 3 (moving zombies)
= LVL 3 (50< score < 100): Max 4 (moving zombies)
- LVL 4 (100 < score < 150): Max 5 (moving zombies)
- LVL 5 (150 <score ): Max 6 (moving zombies)

#### Enemies
There are 4 enemy sprites, all facing a different direction (North, South, East, West). Depending on the spawn location of the zombie, it will be assigned the appropriate frame (as seen above in art section) to ensure that they are facing the base. For example, if a zombie spawns to the left of the base they will be facing right. 

#### Sound
The sound design was the final addition to my game. The ambient music is an acoustic cover of a spaghetti western theme song. There is a sound queue that is activated when a power up is picked up, each with their own sound. This was done to notify players that a powerup has been activated in the case that they kill an enemy in close proximity and pick up the power-up unknowingly. The sound files for the gunshot, power-ups, and game over were sourced from royalty free sources on YouTube and edited to remove empty sound at the beginning of each file.

#### Game Over Screen
The game over screen displays the players score, as well as their accuracy. There is a try again button that will restart the game. This resets the environment by removing all enemies, powerups, and resets score and difficulty.


