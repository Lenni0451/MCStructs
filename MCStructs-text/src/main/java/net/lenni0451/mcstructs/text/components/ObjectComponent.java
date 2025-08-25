package net.lenni0451.mcstructs.text.components;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.font.AtlasSpriteFont;
import net.lenni0451.mcstructs.text.font.FontDescription;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ObjectComponent extends TextComponent {

    private ObjectInfo objectInfo;

    @Override
    public String asSingleString() {
        return this.objectInfo.getDescription();
    }

    @Override
    public TextComponent copy() {
        return new ObjectComponent(this.objectInfo);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("objectInfo", this.objectInfo)
                .toString();
    }


    public interface ObjectInfo {
        FontDescription getUpdatedFont();

        String getDescription();

        @Override
        String toString();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class AtlasSprite implements ObjectInfo {
        public static final Identifier DEFAULT_ATLAS = Identifier.defaultNamespace("blocks");

        private Identifier atlas;
        private Identifier sprite;

        public AtlasSprite(final Identifier sprite) {
            this.atlas = DEFAULT_ATLAS;
            this.sprite = sprite;
        }

        @Override
        public FontDescription getUpdatedFont() {
            return new AtlasSpriteFont(this.atlas, this.sprite);
        }

        @Override
        public String getDescription() {
            String shortSprite = Identifier.stripDefaultNamespace(this.sprite);
            if (this.atlas.equals(DEFAULT_ATLAS)) {
                return "[" + shortSprite + "]";
            } else {
                String shortAtlas = Identifier.stripDefaultNamespace(this.atlas);
                return "[" + shortAtlas + "@" + shortSprite + "]";
            }
        }

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("atlas", this.atlas)
                    .add("sprite", this.sprite)
                    .toString();
        }
    }

}
