package net.lenni0451.mcstructs.text.font;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFont implements FontDescription {

    public Identifier id;

    @Override
    public String toString() {
        return ToString.of(this)
                .add("id", this.id)
                .toString();
    }

}
