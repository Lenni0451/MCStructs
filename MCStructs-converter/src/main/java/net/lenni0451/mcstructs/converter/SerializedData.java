package net.lenni0451.mcstructs.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class SerializedData<T> {

    private final T data;
    private final DataConverter<T> converter;

    public <S> Result<S> convert(final DataConverter<S> converter) {
        if (this.converter == converter) return Result.success((S) this.data);
        return Result.success(this.converter.convertTo(converter, this.data));
    }

    public <O> Result<O> deserialize(final Codec<O> codec) {
        return codec.deserialize(this.converter, this.data);
    }

    @Override
    public String toString() {
        return "SerializedData{" + this.data + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SerializedData<?> that = (SerializedData<?>) o;
        return Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data);
    }

}
