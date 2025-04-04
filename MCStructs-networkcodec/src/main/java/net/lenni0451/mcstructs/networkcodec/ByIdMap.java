package net.lenni0451.mcstructs.networkcodec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public class ByIdMap {
  private static <T> IntFunction<T> createMap(ToIntFunction<T> toIntFunction, T[] objects) {
    if (objects.length == 0) {
      throw new IllegalArgumentException("Empty value list");
    } else {
      Map<Integer, T> int2ObjectMap = new HashMap<>();

      for (T object : objects) {
        int i = toIntFunction.applyAsInt(object);
        T object2 = (T)int2ObjectMap.put(i, object);
        if (object2 != null) {
          throw new IllegalArgumentException("Duplicate entry on id " + i + ": current=" + object + ", previous=" + object2);
        }
      }

      return int2ObjectMap::get;
    }
  }

  public static <T> IntFunction<T> sparse(ToIntFunction<T> toIntFunction, T[] objects, T object) {
    IntFunction<T> intFunction = createMap(toIntFunction, objects);
    return i -> {
      T value = intFunction.apply(i);
      if (value == null) {
        return object;
      } else {
        return value;
      }
    };
  }

  private static <T> T[] createSortedArray(ToIntFunction<T> toIntFunction, T[] objects) {
    int i = objects.length;
    if (i == 0) {
      throw new IllegalArgumentException("Empty value list");
    } else {
      T[] arr = objects.clone();
      Arrays.fill(arr, null);

      for (T object : objects) {
        int val = toIntFunction.applyAsInt(object);
        if (val < 0 || val >= i) {
          throw new IllegalArgumentException("Values are not continous, found index " + val + " for value " + object);
        }

        T object2 = arr[val];
        if (object2 != null) {
          throw new IllegalArgumentException("Duplicate entry on id " + val + ": current=" + object + ", previous=" + object2);
        }

        arr[val] = object;
      }

      for (int k = 0; k < i; k++) {
        if (arr[k] == null) {
          throw new IllegalArgumentException("Missing value at index: " + k);
        }
      }

      return arr;
    }
  }

  public static <T> IntFunction<T> continuous(ToIntFunction<T> toIntFunction, T[] objects, ByIdMap.OutOfBoundsStrategy arg) {
    T[] arr = createSortedArray(toIntFunction, objects);
    int length = arr.length;

    switch (arg) {
      case ZERO:
        T object = arr[0];
        return j -> j >= 0 && j < length ? arr[j] : object;
      case WRAP:
        return j -> arr[Math.floorMod(j, length)];
      case CLAMP:
        return j -> arr[Math.min(Math.max(j, 0), length - 1)];
    }

    throw new IllegalArgumentException("Unknown argument " + arg);
  }

  public enum OutOfBoundsStrategy {
    ZERO,
    WRAP,
    CLAMP;
  }
}
