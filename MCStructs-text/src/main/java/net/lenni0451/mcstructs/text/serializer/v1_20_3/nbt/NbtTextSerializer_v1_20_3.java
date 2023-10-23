package net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;

import java.util.ArrayList;
import java.util.List;

public class NbtTextSerializer_v1_20_3 implements ITypedSerializer<INbtTag, ATextComponent> {

    private final ITypedSerializer<INbtTag, Style> styleSerializer;

    public NbtTextSerializer_v1_20_3() {
        this.styleSerializer = new NbtStyleSerializer_v1_20_3(this);
    }

    @Override
    public INbtTag serialize(ATextComponent object) {
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
                List<INbtTag> args = new ArrayList<>();
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
            if (component.isResolve()) out.addByte("interpret", (byte) 1);
            if (component instanceof EntityNbtComponent) {
                EntityNbtComponent entityComponent = (EntityNbtComponent) component;
                out.addString("entity", entityComponent.getSelector());
            } else if (component instanceof BlockNbtComponent) {
                BlockNbtComponent blockNbtComponent = (BlockNbtComponent) component;
                out.addString("block", blockNbtComponent.getPos());
            } else if (component instanceof StorageNbtComponent) {
                StorageNbtComponent storageNbtComponent = (StorageNbtComponent) component;
                out.addString("storage", storageNbtComponent.getId().get());
            } else {
                throw new IllegalArgumentException("Unknown Nbt component type: " + component.getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("Unknown component type: " + object.getClass().getName());
        }

        CompoundTag style = this.styleSerializer.serialize(object.getStyle()).asCompoundTag();
        if (!style.isEmpty()) out.addAll(style);

        if (!object.getSiblings().isEmpty()) {
            List<INbtTag> siblings = new ArrayList<>();
            for (ATextComponent sibling : object.getSiblings()) siblings.add(this.serialize(sibling));
            out.add("extra", this.optimizeAndConvert(siblings));
        }

        return out;
    }

    private INbtTag convert(final Object object) {
        if (object instanceof Boolean) return new ByteTag((byte) ((boolean) object ? 1 : 0));
        else if (object instanceof Byte) return new ByteTag((byte) object);
        else if (object instanceof Short) return new ShortTag((short) object);
        else if (object instanceof Integer) return new IntTag((int) object);
        else if (object instanceof Long) return new LongTag((long) object);
        else if (object instanceof Float) return new FloatTag((float) object);
        else if (object instanceof Double) return new DoubleTag((double) object);
        else if (object instanceof String) return new StringTag((String) object);
        else if (object instanceof ATextComponent) return this.serialize((ATextComponent) object);
        else throw new IllegalArgumentException("Unknown object type: " + object.getClass().getName());
    }

    private INbtTag optimizeAndConvert(final List<INbtTag> tags) {
        NbtType commonType = this.getCommonType(tags);
        if (commonType == null) {
            ListTag<CompoundTag> out = new ListTag<>();
            for (INbtTag tag : tags) {
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
            ListTag<INbtTag> out = new ListTag<>();
            for (INbtTag tag : tags) out.add(tag);
            return out;
        }
    }

    private NbtType getCommonType(final List<INbtTag> tags) {
        if (tags.size() == 1) return tags.get(0).getNbtType();
        NbtType type = tags.get(0).getNbtType();
        for (int i = 1; i < tags.size(); i++) {
            if (!type.equals(tags.get(i).getNbtType())) return null;
        }
        return type;
    }

    @Override
    public ATextComponent deserialize(INbtTag object) {
        if (object.isStringTag()) {
            return new StringComponent(object.asStringTag().getValue());
        } else if (object.isListTag()) {
            if (object.asListTag().isEmpty()) throw new IllegalArgumentException("Empty list tag");
            ListTag<INbtTag> listTag = object.asListTag();
            ATextComponent[] components = new ATextComponent[listTag.size()];
            for (int i = 0; i < listTag.size(); i++) components[i] = this.deserialize(listTag.get(i));
            if (components.length == 1) {
                return components[0];
            } else {
                ATextComponent parent = components[0];
                for (int i = 1; i < components.length; i++) parent.append(components[i]);
                return parent;
            }
        } else if (!object.isCompoundTag()) {
            throw new IllegalArgumentException("Unknown component type: " + object.getNbtType().name());
        }

        CompoundTag tag = object.asCompoundTag();
        String type = tag.getString("type", null);
        Style style = this.styleSerializer.deserialize(tag);
        ATextComponent[] extra;
        if (tag.contains("extra")) {
            if (!tag.contains("extra", NbtType.LIST)) throw new IllegalArgumentException("Expected list tag for 'extra' tag");
            ListTag<INbtTag> extraTag = tag.getList("extra");
            if (extraTag.isEmpty()) throw new IllegalArgumentException("Empty extra list tag");
            List<INbtTag> unwrapped = this.unwrapMarkers(extraTag);
            extra = new ATextComponent[unwrapped.size()];
            for (int i = 0; i < unwrapped.size(); i++) extra[i] = this.deserialize(unwrapped.get(i));
        } else {
            extra = new ATextComponent[0];
        }

        if (tag.contains("text", NbtType.STRING) && (type == null || type.equals("text"))) {
            return new StringComponent(tag.getString("text")).setStyle(style).append(extra);
        } else if (tag.contains("translate", NbtType.STRING) && (type == null || type.equals("translatable"))) {
            String key = tag.getString("translate");
            String fallback = tag.getString("fallback", null);
            if (tag.contains("with")) {
                if (!tag.contains("with", NbtType.LIST)) throw new IllegalArgumentException("Expected list tag for 'with' tag");
                List<INbtTag> with = this.unwrapMarkers(tag.getList("with"));
                Object[] args = new Object[with.size()];
                for (int i = 0; i < with.size(); i++) {
                    INbtTag arg = with.get(i);
                    if (arg.isNumberTag()) args[i] = arg.asNumberTag().numberValue();
                    else if (arg.isStringTag()) args[i] = arg.asStringTag().getValue();
                    else args[i] = this.deserialize(arg);
                }
                return new TranslationComponent(key, fallback, args).setStyle(style).append(extra);
            } else {
                return new TranslationComponent(key, fallback).setStyle(style).append(extra);
            }
        } else if (tag.contains("keybind", NbtType.STRING) && (type == null || type.equals("keybind"))) {
            return new KeybindComponent(tag.getString("keybind")).setStyle(style).append(extra);
        } else if (tag.contains("score", NbtType.COMPOUND) && tag.getCompound("score").contains("name", NbtType.STRING) && tag.getCompound("score").contains("objective", NbtType.STRING) && (type == null || type.equals("score"))) {
            CompoundTag score = tag.getCompound("score");
            String name = score.getString("name");
            String objective = score.getString("objective");
            return new ScoreComponent(name, objective).setStyle(style).append(extra);
        } else if (tag.contains("selector", NbtType.STRING) && (type == null || type.equals("selector"))) {
            String selector = tag.getString("selector");
            ATextComponent separator = null;
            if (tag.contains("separator")) separator = this.deserialize(tag.get("separator"));
            return new SelectorComponent(selector, separator).setStyle(style).append(extra);
        } else if (tag.contains("nbt", NbtType.STRING) && (type == null || type.equals("nbt"))) {
            String nbt = tag.getString("nbt");
            boolean interpret = tag.getBoolean("interpret");
            ATextComponent separator = null;
            if (tag.contains("separator")) {
                try {
                    separator = this.deserialize(tag.get("separator"));
                } catch (Throwable ignored) {
                    //If the separator fails to parse we just ignore it
                }
            }
            String source = tag.getString("source", null);
            if (tag.contains("entity", NbtType.STRING) && (source == null || source.equals("entity"))) {
                return new EntityNbtComponent(nbt, interpret, separator, tag.getString("entity")).setStyle(style).append(extra);
            } else if (tag.contains("block", NbtType.STRING) && (source == null || source.equals("block"))) {
                return new BlockNbtComponent(nbt, interpret, separator, tag.getString("block")).setStyle(style).append(extra);
            } else if (tag.contains("storage", NbtType.STRING) && (source == null || source.equals("storage"))) {
                try {
                    return new StorageNbtComponent(nbt, interpret, separator, Identifier.of(tag.getString("storage"))).setStyle(style).append(extra);
                } catch (Throwable ignored) {
                    //If the storage identifier fails to parse we just ignore it
                }
            }
            throw new IllegalArgumentException("Unknown Nbt component type: " + tag.getNbtType().name());
        }

        throw new IllegalArgumentException("Unknown component type: " + object.getNbtType().name());
    }

    /**
     * Unwrap marker compound tags (CompoundTag with one empty key).
     *
     * @param list The list to unwrap
     * @return The unwrapped list
     */
    private List<INbtTag> unwrapMarkers(final ListTag<?> list) {
        List<INbtTag> out = new ArrayList<>();
        for (INbtTag tag : list) {
            if (tag.isCompoundTag()) {
                CompoundTag compound = tag.asCompoundTag();
                if (compound.size() == 1 && compound.contains("")) out.add(compound.get(""));
                else out.add(tag);
            } else {
                out.add(tag);
            }
        }
        return out;
    }

}
