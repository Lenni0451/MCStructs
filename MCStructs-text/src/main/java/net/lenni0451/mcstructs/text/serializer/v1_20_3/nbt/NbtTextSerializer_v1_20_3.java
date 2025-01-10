package net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtSource;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;
import net.lenni0451.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.CodecUtils_v1_20_3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NbtTextSerializer_v1_20_3 implements ITextComponentSerializer<NbtTag>, CodecUtils_v1_20_3 {

    private final IStyleSerializer<NbtTag> styleSerializer;

    public NbtTextSerializer_v1_20_3(final Function<NbtTextSerializer_v1_20_3, IStyleSerializer<NbtTag>> styleSerializer) {
        this.styleSerializer = styleSerializer.apply(this);
    }

    public IStyleSerializer<NbtTag> getStyleSerializer() {
        return this.styleSerializer;
    }

    @Override
    public NbtTag serialize(TextComponent object) {
        CompoundTag out = new CompoundTag();
        if (object instanceof StringComponent) {
            StringComponent component = (StringComponent) object;
            if (component.getSiblings().isEmpty() && component.getStyle().isEmpty()) return new StringTag(component.getText());
            else out.addString("text", component.getText());
        } else if (object instanceof TranslationComponent) {
            TranslationComponent component = (TranslationComponent) object;
            out.addString("translate", component.getKey());
            if (component.getFallback() != null) out.addString("fallback", component.getFallback());
            if (component.getArgs().length > 0) {
                List<NbtTag> args = new ArrayList<>();
                for (Object arg : component.getArgs()) args.add(this.convert(arg));
                out.add("with", this.optimizeAndConvert(args));
            }
        } else if (object instanceof KeybindComponent) {
            KeybindComponent component = (KeybindComponent) object;
            out.addString("keybind", component.getKeybind());
        } else if (object instanceof ScoreComponent) {
            ScoreComponent component = (ScoreComponent) object;
            CompoundTag score = new CompoundTag();
            score.addString("name", component.getName());
            score.addString("objective", component.getObjective());
            out.add("score", score);
        } else if (object instanceof SelectorComponent) {
            SelectorComponent component = (SelectorComponent) object;
            out.addString("selector", component.getSelector());
            if (component.getSeparator() != null) out.add("separator", this.serialize(component.getSeparator()));
        } else if (object instanceof NbtComponent) {
            NbtComponent component = (NbtComponent) object;
            out.addString("nbt", component.getComponent());
            if (component.isResolve()) out.addBoolean("interpret", true);
            if (component.getDataSource() instanceof EntityNbtSource) {
                EntityNbtSource entityNbtSource = (EntityNbtSource) component.getDataSource();
                out.addString("entity", entityNbtSource.getSelector());
            } else if (component.getDataSource() instanceof BlockNbtSource) {
                BlockNbtSource blockNbtSource = (BlockNbtSource) component.getDataSource();
                out.addString("block", blockNbtSource.getPos());
            } else if (component.getDataSource() instanceof StorageNbtSource) {
                StorageNbtSource storageNbtSource = (StorageNbtSource) component.getDataSource();
                out.addString("storage", storageNbtSource.getId().get());
            } else {
                throw new IllegalArgumentException("Unknown Nbt component type: " + component.getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("Unknown component type: " + object.getClass().getName());
        }

        CompoundTag style = this.styleSerializer.serialize(object.getStyle()).asCompoundTag();
        if (!style.isEmpty()) out.addAll(style);

        if (!object.getSiblings().isEmpty()) {
            List<NbtTag> siblings = new ArrayList<>();
            for (TextComponent sibling : object.getSiblings()) siblings.add(this.serialize(sibling));
            out.add("extra", this.optimizeAndConvert(siblings));
        }

        return out;
    }

    protected NbtTag convert(final Object object) {
        if (object instanceof Boolean) return new ByteTag((byte) ((boolean) object ? 1 : 0));
        else if (object instanceof Byte) return new ByteTag((byte) object);
        else if (object instanceof Short) return new ShortTag((short) object);
        else if (object instanceof Integer) return new IntTag((int) object);
        else if (object instanceof Long) return new LongTag((long) object);
        else if (object instanceof Float) return new FloatTag((float) object);
        else if (object instanceof Double) return new DoubleTag((double) object);
        else if (object instanceof String) return new StringTag((String) object);
        else if (object instanceof TextComponent) return this.serialize((TextComponent) object);
        else throw new IllegalArgumentException("Unknown object type: " + object.getClass().getName());
    }

    protected NbtTag optimizeAndConvert(final List<NbtTag> tags) {
        NbtType commonType = this.getCommonType(tags);
        if (commonType == null) {
            ListTag<CompoundTag> out = new ListTag<>();
            for (NbtTag tag : tags) {
                if (tag instanceof CompoundTag) {
                    out.add((CompoundTag) tag);
                } else {
                    CompoundTag marker = new CompoundTag();
                    marker.add("", tag);
                    out.add(marker);
                }
            }
            return out;
        } else if (NbtType.BYTE.equals(commonType)) {
            byte[] bytes = new byte[tags.size()];
            for (int i = 0; i < tags.size(); i++) bytes[i] = tags.get(i).asByteTag().getValue();
            return new ByteArrayTag(bytes);
        } else if (NbtType.INT.equals(commonType)) {
            int[] ints = new int[tags.size()];
            for (int i = 0; i < tags.size(); i++) ints[i] = tags.get(i).asIntTag().getValue();
            return new IntArrayTag(ints);
        } else if (NbtType.LONG.equals(commonType)) {
            long[] longs = new long[tags.size()];
            for (int i = 0; i < tags.size(); i++) longs[i] = tags.get(i).asLongTag().getValue();
            return new LongArrayTag(longs);
        } else {
            ListTag<NbtTag> out = new ListTag<>();
            for (NbtTag tag : tags) out.add(tag);
            return out;
        }
    }

    protected NbtType getCommonType(final List<NbtTag> tags) {
        if (tags.size() == 1) return tags.get(0).getNbtType();
        NbtType type = tags.get(0).getNbtType();
        for (int i = 1; i < tags.size(); i++) {
            if (!type.equals(tags.get(i).getNbtType())) return null;
        }
        return type;
    }

    @Override
    public TextComponent deserialize(NbtTag object) {
        if (object.isStringTag()) {
            return new StringComponent(object.asStringTag().getValue());
        } else if (object.isListTag()) {
            if (object.asListTag().isEmpty()) throw new IllegalArgumentException("Empty list tag");
            ListTag<NbtTag> listTag = object.asListTag();
            TextComponent[] components = new TextComponent[listTag.size()];
            for (int i = 0; i < listTag.size(); i++) components[i] = this.deserialize(listTag.get(i));
            if (components.length == 1) {
                return components[0];
            } else {
                TextComponent parent = components[0];
                for (int i = 1; i < components.length; i++) parent.append(components[i]);
                return parent;
            }
        } else if (!object.isCompoundTag()) {
            throw new IllegalArgumentException("Unknown component type: " + object.getNbtType().name());
        }

        TextComponent component = null;
        CompoundTag tag = object.asCompoundTag();
        String type = tag.getString("type", null);
        if (tag.contains("text", NbtType.STRING) && (type == null || type.equals("text"))) {
            component = new StringComponent(tag.getString("text"));
        } else if (tag.contains("translate", NbtType.STRING) && (type == null || type.equals("translatable"))) {
            String key = tag.getString("translate");
            String fallback = tag.getString("fallback", null);
            if (tag.contains("with")) {
                List<NbtTag> with = unwrapMarkers(this.getArrayOrList(tag, "with"));
                Object[] args = new Object[with.size()];
                for (int i = 0; i < with.size(); i++) {
                    NbtTag arg = with.get(i);
                    if (arg.isNumberTag()) args[i] = arg.asNumberTag().numberValue();
                    else if (arg.isStringTag()) args[i] = arg.asStringTag().getValue();
                    else args[i] = this.deserialize(arg);
                }
                component = new TranslationComponent(key, args).setFallback(fallback);
            } else {
                component = new TranslationComponent(key).setFallback(fallback);
            }
        } else if (tag.contains("keybind", NbtType.STRING) && (type == null || type.equals("keybind"))) {
            component = new KeybindComponent(tag.getString("keybind"));
        } else if (tag.contains("score", NbtType.COMPOUND) && tag.getCompound("score").contains("name", NbtType.STRING) && tag.getCompound("score").contains("objective", NbtType.STRING) && (type == null || type.equals("score"))) {
            CompoundTag score = tag.getCompound("score");
            String name = score.getString("name");
            String objective = score.getString("objective");
            component = new ScoreComponent(name, objective);
        } else if (tag.contains("selector", NbtType.STRING) && (type == null || type.equals("selector"))) {
            String selector = tag.getString("selector");
            TextComponent separator = null;
            if (tag.contains("separator")) separator = this.deserialize(tag.get("separator"));
            component = new SelectorComponent(selector, separator);
        } else if (tag.contains("nbt", NbtType.STRING) && (type == null || type.equals("nbt"))) {
            String nbt = tag.getString("nbt");
            boolean interpret = tag.getBoolean("interpret");
            TextComponent separator = null;
            if (tag.contains("separator")) {
                try {
                    separator = this.deserialize(tag.get("separator"));
                } catch (Throwable ignored) {
                    //If the separator fails to parse we just ignore it
                }
            }
            String source = tag.getString("source", null);

            boolean typeFound = false;
            if (tag.contains("entity", NbtType.STRING) && (source == null || source.equals("entity"))) {
                component = new NbtComponent(nbt, interpret, separator, new EntityNbtSource(tag.getString("entity")));
                typeFound = true;
            } else if (tag.contains("block", NbtType.STRING) && (source == null || source.equals("block"))) {
                component = new NbtComponent(nbt, interpret, separator, new BlockNbtSource(tag.getString("block")));
                typeFound = true;
            } else if (tag.contains("storage", NbtType.STRING) && (source == null || source.equals("storage"))) {
                try {
                    component = new NbtComponent(nbt, interpret, separator, new StorageNbtSource(Identifier.of(tag.getString("storage"))));
                    typeFound = true;
                } catch (Throwable ignored) {
                    //If the storage identifier fails to parse we just ignore it
                }
            }
            if (!typeFound) throw new IllegalArgumentException("Unknown Nbt component type: " + tag.getNbtType().name());
        } else {
            throw new IllegalArgumentException("Unknown component type: " + tag.getNbtType().name());
        }

        Style style = this.styleSerializer.deserialize(tag);
        if (!style.isEmpty()) component.setStyle(style);

        if (tag.contains("extra")) {
            if (!tag.contains("extra", NbtType.LIST)) throw new IllegalArgumentException("Expected list tag for 'extra' tag");
            ListTag<NbtTag> extraTag = tag.getList("extra");
            if (extraTag.isEmpty()) throw new IllegalArgumentException("Empty extra list tag");

            TextComponent[] extra;
            List<NbtTag> unwrapped = unwrapMarkers(extraTag);
            extra = new TextComponent[unwrapped.size()];
            for (int i = 0; i < unwrapped.size(); i++) extra[i] = this.deserialize(unwrapped.get(i));
            component.append(extra);
        }

        return component;
    }

    protected ListTag<?> getArrayOrList(final CompoundTag tag, final String key) {
        if (tag.contains(key, NbtType.LIST)) return tag.getList(key);
        else if (tag.contains(key, NbtType.BYTE_ARRAY)) return tag.get(key).asByteArrayTag().toListTag();
        else if (tag.contains(key, NbtType.INT_ARRAY)) return tag.get(key).asIntArrayTag().toListTag();
        else if (tag.contains(key, NbtType.LONG_ARRAY)) return tag.get(key).asLongArrayTag().toListTag();
        else throw new IllegalArgumentException("Expected array or list tag for '" + key + "' tag");
    }

}
