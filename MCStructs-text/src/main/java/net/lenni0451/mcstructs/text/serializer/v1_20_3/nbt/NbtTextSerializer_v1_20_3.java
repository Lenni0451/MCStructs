package net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;

public class NbtTextSerializer_v1_20_3 implements ITypedSerializer<INbtTag, ATextComponent> {

    private final ITypedSerializer<INbtTag, Style> styleSerializer;

    public NbtTextSerializer_v1_20_3() {
        this.styleSerializer = new NbtStyleSerializer_v1_20_3(this);
    }

    @Override
    public INbtTag serialize(ATextComponent object) {
        return null;
    }

//    public void serialize(ATextComponent component, IMapData data) {
//        if (component instanceof StringComponent) {
//            StringComponent comp = (StringComponent) component;
//            data.put("text", DataType.STRING, comp.getText());
//        } else if (component instanceof TranslationComponent) {
//            TranslationComponent comp = (TranslationComponent) component;
//            data.put("translate", DataType.STRING, comp.getArgs());
//            if (comp.getFallback() != null) data.put("fallback", DataType.STRING, comp.getFallback());
//            if (comp.getArgs().length > 0) {
//                //TODO: Wait for https://bugs.mojang.com/browse/MC-265733 to be fixed or confirm that it's a "feature"
//            }
//        } else if (component instanceof KeybindComponent) {
//            KeybindComponent comp = (KeybindComponent) component;
//            data.put("keybind", DataType.STRING, comp.getKeybind());
//        } else if (component instanceof ScoreComponent) {
//            ScoreComponent comp = (ScoreComponent) component;
//            IMapData score = data.createMap();
//            score.put("name", DataType.STRING, comp.getName());
//            score.put("objective", DataType.STRING, comp.getObjective());
//            data.put("score", DataType.MAP, score);
//        } else if (component instanceof SelectorComponent) {
//            SelectorComponent comp = (SelectorComponent) component;
//            data.put("selector", DataType.STRING, comp.getSelector());
//            if (comp.getSeparator() != null) ;//TODO: Separator is a component
//        } else if (component instanceof NbtComponent) {
//            NbtComponent comp = (NbtComponent) component;
//            data.put("nbt", DataType.STRING, comp.getComponent());
//            if (comp.isResolve()) data.put("interpret", DataType.BOOLEAN, comp.isResolve());
//            if (comp.getSeparator() != null) ;//TODO: Separator is a component
//            if (comp instanceof EntityNbtComponent) {
//                EntityNbtComponent entityComp = (EntityNbtComponent) comp;
//                data.put("entity", DataType.STRING, entityComp.getSelector());
//            } else if (comp instanceof BlockNbtComponent) {
//                BlockNbtComponent blockComp = (BlockNbtComponent) comp;
//                data.put("block", DataType.STRING, blockComp.getPos());
//            } else if (comp instanceof StorageNbtComponent) {
//                StorageNbtComponent storageComp = (StorageNbtComponent) comp;
//                data.put("storage", DataType.STRING, storageComp.getId().get());
//            } else {
//                throw new IllegalArgumentException("Unknown Nbt component type: " + comp.getClass().getName());
//            }
//        } else {
//            throw new IllegalArgumentException("Unknown component type: " + component.getClass().getName());
//        }
//    }


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
            extra = new ATextComponent[extraTag.size()];
            for (int i = 0; i < extraTag.size(); i++) extra[i] = this.deserialize(extraTag.get(i));
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
                ListTag<INbtTag> with = tag.getList("with");
                for (int i = 0; i < with.size(); i++) {
                    //Unwrap marker compound tags (CompoundTag with one empty key)
                    INbtTag arg = with.get(i);
                    if (arg.isCompoundTag()) {
                        CompoundTag argTag = arg.asCompoundTag();
                        if (argTag.size() == 1 && argTag.contains("")) with.set(i, argTag.get(""));
                    }
                }

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
            boolean interpret = tag.getByte("interpret", (byte) 0) != 0;
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

}
