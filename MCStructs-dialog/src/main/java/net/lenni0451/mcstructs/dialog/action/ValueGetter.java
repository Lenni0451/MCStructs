package net.lenni0451.mcstructs.dialog.action;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.StringTag;

import java.util.function.Supplier;

public interface ValueGetter {

    static ValueGetter of(final String s) {
        return new ValueGetter() {
            @Override
            public String asTemplateSubstitution() {
                return s;
            }

            @Override
            public NbtTag asNbtTag() {
                return new StringTag(s);
            }
        };
    }

    static ValueGetter of(final Supplier<String> supplier) {
        return new ValueGetter() {
            @Override
            public String asTemplateSubstitution() {
                return supplier.get();
            }

            @Override
            public NbtTag asNbtTag() {
                return new StringTag(supplier.get());
            }
        };
    }


    String asTemplateSubstitution();

    NbtTag asNbtTag();

}
