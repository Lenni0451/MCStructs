package net.lenni0451.mcstructs.text.font;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.SerializedData;
import net.lenni0451.mcstructs.core.utils.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSpriteFont implements FontDescription {

    private SerializedData<?> profile;
    private boolean hat;

    @Override
    public String toString() {
        return ToString.of(this)
                .add("profile", this.profile)
                .add("hat", this.hat)
                .toString();
    }

}
