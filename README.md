# MinecraftSkillSystem

A Minecraft Plugin for the 26.1.2, which tracks Xp and level for the Skills Mining, Farming, Combat, Fishing and Foraging.

## Features
- XP are granted for specific blocks brocken, Mobs killed and items fished.
- ### Commands
  - `/levelinfo`<br>
    this command opens an inventory where you can see your level and xp.
    
  - `/giveXP`<br>
    this command gives you XP for a serten Skill.<br><br>
    Syntax:<br>
    `/giveXP <Skill in all Caps> <xp amount>`<br><br>
    Example:<br>
    `/giveXP MINING 10`<br><br>
    
  - `/setLevel` <br>
  this command sets your level for a serten skill to the given amount.<br><br>
  Syntax <br>
  `/setLevel <Skill in all Caps> <the level>` <br><br>
  Example:<br>
  `/setLevel MINING 10`<br><br>
  
  - `/maxblockvein`
    this command lets you controle how many blocks you want to maximum mine at once.<br><br>
    Syntax:<br>
    `/maxblockvein <nmber of blocks 1-9>`<br><br>
    Example:<br>
    `/maxblockvein 5`
## Installation (fresh server)
1. Download Paper 26.1.2 and put the JAR into a new empty folder
2. Create a file start.bat in the same folder(it's important that it has the endin .bat). <br>
   in there you put the text:
     `java -Xms2G -Xmx2G -jar paper-26.1.2.jar --nogui`
3. Run the script once. The server stops and creates `eula.txt`.
4. Open `eula.txt` and set `eula=true`.
5. Download `MinecraftSkillSystem.jar` from the latest GitHub release
   and put it into the `plugins/` folder.
6. Run the script again. The console should show the plugin as enabled.
7. Join with Minecraft 26.1.2 on `localhost`.
