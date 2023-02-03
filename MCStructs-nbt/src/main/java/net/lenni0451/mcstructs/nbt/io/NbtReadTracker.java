package net.lenni0451.mcstructs.nbt.io;

import net.lenni0451.mcstructs.nbt.exceptions.NbtReadException;

/**
 * The Nbt read tracker is used to limit the size of Nbt data that is read.<br>
 * If you do not want any size constraints you can use {@link #unlimited()}.
 */
public class NbtReadTracker {

    /**
     * The default max bytes that can be read.
     */
    public static final int DEFAULT_MAX_BYTES = 2_097_152;
    /**
     * The default max depth that can be read.
     */
    public static final int DEFAULT_MAX_DEPTH = 512;

    /**
     * Create an unlimited read tracker.<br>
     * The depth ({@link #DEFAULT_MAX_DEPTH}) is still limited.
     *
     * @return The unlimited read tracker
     */
    public static NbtReadTracker unlimited() {
        return new NbtReadTracker(0) {
            @Override
            public void read(int bytes) {
            }
        };
    }


    private final int maxDepth;
    private final int maxBytes;
    private int depth;
    private int size;

    /**
     * Create a new read tracker with the default max bytes ({@link #DEFAULT_MAX_BYTES}) and max depth ({@link #DEFAULT_MAX_DEPTH}).
     */
    public NbtReadTracker() {
        this(DEFAULT_MAX_BYTES);
    }

    /**
     * Create a new read tracker with the given max bytes and the default max depth ({@link #DEFAULT_MAX_DEPTH}).
     *
     * @param maxBytes The max bytes that can be read
     */
    public NbtReadTracker(final int maxBytes) {
        this(DEFAULT_MAX_DEPTH, maxBytes);
    }

    /**
     * Create a new read tracker with the given max bytes and max depth.
     *
     * @param maxDepth The max depth that can be read
     * @param maxBytes The max bytes that can be read
     */
    public NbtReadTracker(final int maxDepth, final int maxBytes) {
        this.maxDepth = maxDepth;
        this.maxBytes = maxBytes;
    }

    /**
     * Push a new depth level.
     *
     * @throws NbtReadException If the max depth is reached
     */
    public void pushDepth() throws NbtReadException {
        this.depth++;
        if (this.depth > 512) throw new NbtReadException("Tried to read Nbt with more depth than allowed (" + this.maxDepth + ")");
    }

    /**
     * Pop a depth level.
     */
    public void popDepth() {
        this.depth--;
    }

    /**
     * Read the given amount of bytes.
     *
     * @param bytes The amount of bytes that were read
     * @throws NbtReadException If the max bytes were exceeded
     */
    public void read(final int bytes) throws NbtReadException {
        this.size += bytes;
        if (this.size > this.maxBytes) {
            throw new NbtReadException("Tried to read larger Nbt than allowed. Needed bytes " + this.size + " but max is " + this.maxBytes + " bytes");
        }
    }

}
