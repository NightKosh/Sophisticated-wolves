# [Sophisticated Wolves](http://sophisticated-wolves.nightkosh.com/) [![Curseforge](http://cf.way2muchnoise.eu/full_sophisticated-wolves_downloads.svg)](https://minecraft.curseforge.com/projects/sophisticated-wolves) [![Curseforge](http://cf.way2muchnoise.eu/versions/For%20MC_sophisticated-wolves_all.svg)](https://minecraft.curseforge.com/projects/sophisticated-wolves)

What is Sophisticated Wolves? It's a mod that aims to improve wolves in the games through a series of gameplay tweaks, AI improvements, and added features. The focus is not to have overpowered items or crazy abilities, but rather balanced additions that make sense in the world of MC. Essentially, wolves should be able to better respond to your actions and take care of themselves, so you don't have to consider them a liability when mining, exploring or fighting.

This is an updated and reworked version of [metroidfood](http://www.minecraftforum.net/members/metroidfood)'s ["Sophisticated Wolves"](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1276521) mod.

## Minecraft versions
"Master branch" contains mod sources for the latest version of the Minecraft I'm working on. Any previous versions contains in an own branches.

# API
[Sophisticated wolves API](https://github.com/NightKosh/Sophisticated-wolves-API)

## Requirements
1. [Forge](http://files.minecraftforge.net/) (check "build.gradle" file to know required forge version)
   * Latest versions of Forge requires [Gradle 2.0](https://gradle.org/) or higher(I used Gradle 7.5.1)
2. "Master branch" version of mod requires [jdk 1.17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)(do not forget to enable support of apropriate java version in your IDE)

## Dependencies.
**Be careful some of these API may not be updated yet, or may be broken!**

1. [Sophisticated wolves API](https://github.com/NightKosh/Sophisticated-wolves-API) (will be loaded as git submodule)

## Get started
1. Clone mod repository 
2. [Download forge](http://files.minecraftforge.net/) and copy "gradlew.bat", "gradlew" files and "gradle" directory to mod folder(and any other files which may requires)
3. Download mod's API
   * Download [submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules)
      * Run next commands from mod folder:
      ```
        git submodule init
        git submodule update
      ```
   * ~~Download other API's manually and place them into "src/main/java" folder~~
4. Run "./gradlew setupDecompWorkspace idea" from mod folder
5. Import mod to your ide as "new Gradle project"

## Gradle commands
1. Download the MDK from forge
    for eclipse:
    ```
        gradlew genEclipseRuns
    ```
    for IntelliJ IDEA:
    ```
        gradlew genIntellijRuns
    ```
2. Running client
    ```
        gradlew runClient
    ```
3. Running Server
    ```
        gradlew runServer
    ```
4. Build mod as .jar file
    ```
        gradlew build
    ```
   
For more information, look at "minecraft forge" README.txt file (it's not included to this repository) or [this link](https://gist.github.com/mcenderdragon/6c7af2daf6f72b0cadf0c63169a87583)
