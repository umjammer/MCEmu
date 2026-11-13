[![Fabric](https://img.shields.io/badge/Mod_Loader-Fabric-blue)](https://fabricmc.net/)
[![Minecraft](https://img.shields.io/badge/Minecraft-26.3-green)](https://www.minecraft.net/)
![Java](https://img.shields.io/badge/Java-25-b07219)

# MCEmu

Run emulators in Minecraft!

![2022-05-02_19 53 53](https://user-images.githubusercontent.com/493908/166223486-5f2ee0b9-7c17-422d-953b-747ba1c6beb4.png)

## Status

🐣 just an incubation not a production! 

 * this mod is a showcase for create dynamic 2d rendering system
 * `BufferdImage` to `NativeImage` conversion

## Requirements

 * minecraft 26.3
 * fabricmc
 * romz
```shell
$ mkdir $MINECRAFT_HOME/config/mcemu/roms/nes
$ cp your_roms.nes ... $MINECRAFT_HOME/config/mcemu/roms/nes/
```

## TODO

 * ~~television entity renderer~~
 * ~~console block renderer~~
 * save nes resource when not viewing
 * multiple cartridges are not tested
 * using a new cartridge and reset does not work now
 * the nes emulator seems to run w/o wait