package net.lenni0451.mcstructs.all.nbt;

import net.lenni0451.mcstructs.nbt.NbtIO;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;

import java.io.File;
import java.io.InputStream;

/**
 * Read a servers.dat file and rename all servers.
 */
public class ServerRenaming {

    public static void main(String[] args) throws Throwable {
        InputStream exampleServers = ServerRenaming.class.getClassLoader().getResourceAsStream("servers.dat");
        if (exampleServers == null) {
            System.out.println("Could not find servers.dat");
            return;
        }

        CompoundTag serversTag = (CompoundTag) NbtIO.read(exampleServers, false, NbtReadTracker.unlimited());
        ListTag<CompoundTag> servers = serversTag.getList("servers", NbtType.COMPOUND);
        int i = 0;
        for (CompoundTag server : servers) server.addString("name", "Server #" + ++i);
        NbtIO.writeFile(new File("newServers.dat"), "", serversTag);
    }

}
