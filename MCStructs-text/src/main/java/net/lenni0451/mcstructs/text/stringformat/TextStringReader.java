package net.lenni0451.mcstructs.text.stringformat;

public class TextStringReader {

    private final String s;
    private int index;
    private int mark;

    public TextStringReader(final String s) {
        this.s = s;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLength() {
        return this.s.length();
    }

    public TextStringReader mark() {
        this.mark = this.index;
        return this;
    }

    public TextStringReader reset() {
        this.index = this.mark;
        return this;
    }

    public String getMark() {
        return this.s.substring(this.mark, this.index);
    }

    public boolean canRead() {
        return this.canRead(1);
    }

    public boolean canRead(final int count) {
        return this.index + count <= this.s.length();
    }

    public char peek() {
        this.verifyIndex(1);
        return this.s.charAt(this.index);
    }

    public String peek(final int length) {
        this.verifyIndex(length);
        return this.s.substring(this.index, Math.min(this.index + length, this.s.length()));
    }

    public char peekAt(final int offset) {
        this.verifyIndex(offset);
        return this.s.charAt(this.index + offset);
    }

    public TextStringReader skip() {
        this.verifyIndex(1);
        this.index++;
        return this;
    }

    public TextStringReader skip(final int count) {
        this.verifyIndex(count);
        this.index += count;
        return this;
    }

    public char read() {
        this.verifyIndex(1);
        return this.s.charAt(this.index++);
    }

    public String read(final int length) {
        this.verifyIndex(length);
        String result = this.s.substring(this.index, Math.min(this.index + length, this.s.length()));
        this.index += length;
        return result;
    }

    public String readUntil(final char c) {
        StringBuilder builder = new StringBuilder();
        while (this.canRead()) {
            char read = this.peek();
            if (read == c) break;
            this.skip();
            builder.append(read);
        }
        return builder.toString();
    }


    private void verifyIndex(final int offset) {
        if (!this.canRead(offset)) throw new IndexOutOfBoundsException("Index " + (this.index + offset + 1) + " out of bounds for length " + this.s.length());
    }

}
