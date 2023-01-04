package net.lenni0451.mcstructs.all.nbt;

import net.lenni0451.mcstructs.nbt.NbtIO;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;

import java.io.File;

/**
 * Create a new servers.dat file by parsing a string.
 */
public class SNbtServers {

    public static void main(String[] args) throws Throwable {
        CompoundTag serversTag = SNbtSerializer.V1_8.deserialize("{servers:[{name:localhost,ip:127.0.0.1:25565}]}");
        NbtIO.writeFile(new File("newServers.dat"), "", serversTag);
    }

}
