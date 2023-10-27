# MCStructs
A minecraft data structure library with the goal of being compliant with the vanilla client.\
MCStructs is designed to have an open API, allowing for easy modification of internal structures.

## Modules
| Module       | Description                                              | Support Versions |
|--------------|----------------------------------------------------------|------------------|
| all          | Gradle module with all modules except data               |                  |
| core         | Some util classes usually needed by other modules        | *                |
| data         | Dumps of game data mainly used for inventories           | 1.7              |
| enchantments | Enchantment and registry implementation                  | *                |
| inventory    | Reimplementation of the minecraft inventory code         | 1.7, 1.8         |
| items        | Wrapper for the minecraft ItemStack class and item types | *                |
| nbt          | General purpose nbt implementation                       | *                |
| recipes      | Implementation of dynamic recipes and registry           | 1.7, 1.8 (WIP)   |
| snbt         | String Nbt parser for every version with snbt            | 1.7 - 1.20.3     |
| text         | Implementation of all text components                    | 1.6 - 1.20.3     |

## Releases
### Gradle/Maven
To use MCStructs with Gradle/Maven you can follow the instructions on [maven central](https://central.sonatype.com/search?q=net.lenni0451.mcstructs).\
Gradle Template:
`````groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "net.lenni0451.mcstructs:all:x.x.x"
}
`````
You should check [maven central](https://central.sonatype.com/search?q=net.lenni0451.mcstructs) for the latest version.
### Jar File
You can download the jar files for all modules from my [Jenkins](https://build.lenni0451.net/job/MCStructs/) server.\
Module dependencies are not included in the jar files. You have to add them yourself.

## Usage
Check the [wiki](https://github.com/Lenni0451/MCStructs/wiki/) for more information.
