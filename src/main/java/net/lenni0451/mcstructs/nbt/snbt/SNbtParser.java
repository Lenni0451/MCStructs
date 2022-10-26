package net.lenni0451.mcstructs.nbt.snbt;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.snbt.impl.SNbtParser_v1_7;
import net.lenni0451.mcstructs.nbt.snbt.impl.SNbtParser_v1_8;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

import java.util.function.Supplier;

public class SNbtParser<T extends INbtTag> {

    public static final SNbtParser<INbtTag> V1_7 = new SNbtParser<>(SNbtParser_v1_7::new);
    public static final SNbtParser<CompoundNbt> V1_8 = new SNbtParser<>(SNbtParser_v1_8::new);


    private final Supplier<ISNbtParser<T>> parserSupplier;
    private ISNbtParser<T> parser;

    public SNbtParser(final Supplier<ISNbtParser<T>> parserSupplier) {
        this.parserSupplier = parserSupplier;
    }

    public ISNbtParser<T> getParser() {
        if (this.parser == null) this.parser = this.parserSupplier.get();
        return this.parser;
    }

    public T parse(final String s) throws SNbtParseException {
        return this.getParser().parse(s);
    }

    public T tryParse(final String s) {
        try {
            return this.parse(s);
        } catch (Throwable t) {
            return null;
        }
    }

}
