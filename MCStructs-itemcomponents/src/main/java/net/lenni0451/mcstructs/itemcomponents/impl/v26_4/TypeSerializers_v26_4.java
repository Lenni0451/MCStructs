package net.lenni0451.mcstructs.itemcomponents.impl.v26_4;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_3.TypeSerializers_v26_3;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.DispenseBehavior;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.DispenseSound;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.FloatProvider;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.Vec3d;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TypeSerializers_v26_4 extends TypeSerializers_v26_3 {

    protected static final String VEC3D = "vec3d";
    protected static final String FLOAT_PROVIDER = "float_provider";
    protected static final String DISPENSE_SOUND = "dispense_sound";
    protected static final String DISPENSE_BEHAVIOR = "dispense_behavior";
    protected static final String DISPENSE_BEHAVIORS = "dispense_behaviors";

    public TypeSerializers_v26_4(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<Vec3d> vec3d() {
        return this.init(VEC3D, () -> Codec.DOUBLE.listOf(3, 3).map(
                vec -> Arrays.asList(vec.getX(), vec.getY(), vec.getZ()),
                list -> new Vec3d(list.get(0), list.get(1), list.get(2))
        ));
    }

    public Codec<FloatProvider> floatProvider() {
        return this.init(FLOAT_PROVIDER, () -> {
            Map<FloatProvider.Type, MapCodec<? extends FloatProvider>> codecs = new EnumMap<>(FloatProvider.Type.class);
            codecs.put(FloatProvider.Type.CONSTANT, MapCodecMerger.mapCodec(
                    Codec.FLOAT.mapCodec(FloatProvider.ConstantFloat.VALUE).required(), FloatProvider.ConstantFloat::getValue,
                    FloatProvider.ConstantFloat::new
            ));
            codecs.put(FloatProvider.Type.UNIFORM, MapCodecMerger.mapCodec(
                    Codec.FLOAT.mapCodec(FloatProvider.UniformFloat.MIN_INCLUSIVE).required(), FloatProvider.UniformFloat::getMinInclusive,
                    Codec.FLOAT.mapCodec(FloatProvider.UniformFloat.MAX_EXCLUSIVE).required(), FloatProvider.UniformFloat::getMaxExclusive,
                    FloatProvider.UniformFloat::new
            ).verified(uniform -> {
                if (uniform.getMaxExclusive() <= uniform.getMinInclusive()) {
                    return Result.error("Max must be larger than min, min: " + uniform.getMinInclusive() + ", max: " + uniform.getMaxExclusive());
                }
                return null;
            }));
            codecs.put(FloatProvider.Type.CLAMPED_NORMAL, MapCodecMerger.mapCodec(
                    Codec.FLOAT.mapCodec(FloatProvider.ClampedNormalFloat.MEAN).required(), FloatProvider.ClampedNormalFloat::getMean,
                    Codec.FLOAT.mapCodec(FloatProvider.ClampedNormalFloat.DEVIATION).required(), FloatProvider.ClampedNormalFloat::getDeviation,
                    Codec.FLOAT.mapCodec(FloatProvider.ClampedNormalFloat.MIN).required(), FloatProvider.ClampedNormalFloat::getMin,
                    Codec.FLOAT.mapCodec(FloatProvider.ClampedNormalFloat.MAX).required(), FloatProvider.ClampedNormalFloat::getMax,
                    FloatProvider.ClampedNormalFloat::new
            ).verified(clamped -> {
                if (clamped.getMax() < clamped.getMin()) {
                    return Result.error("Max must be larger than min: [" + clamped.getMin() + ", " + clamped.getMax() + "]");
                }
                return null;
            }));
            codecs.put(FloatProvider.Type.TRAPEZOID, MapCodecMerger.mapCodec(
                    Codec.FLOAT.mapCodec(FloatProvider.TrapezoidFloat.MIN).required(), FloatProvider.TrapezoidFloat::getMin,
                    Codec.FLOAT.mapCodec(FloatProvider.TrapezoidFloat.MAX).required(), FloatProvider.TrapezoidFloat::getMax,
                    Codec.FLOAT.mapCodec(FloatProvider.TrapezoidFloat.PLATEAU).required(), FloatProvider.TrapezoidFloat::getPlateau,
                    FloatProvider.TrapezoidFloat::new
            ).verified(trapezoid -> {
                if (trapezoid.getMax() < trapezoid.getMin()) {
                    return Result.error("Max must be larger than min: [" + trapezoid.getMin() + ", " + trapezoid.getMax() + "]");
                } else if (trapezoid.getPlateau() > trapezoid.getMax() - trapezoid.getMin()) {
                    return Result.error("Plateau can at most be the full span: [" + trapezoid.getMin() + ", " + trapezoid.getMax() + "]");
                }
                return null;
            }));
            Codec<FloatProvider> dispatchCodec = Codec.identified(FloatProvider.Type.values()).typed(FloatProvider::getType, codecs::get);
            return Codec.either(Codec.FLOAT, dispatchCodec).map(
                    provider -> provider instanceof FloatProvider.ConstantFloat ? Either.left(((FloatProvider.ConstantFloat) provider).getValue()) : Either.right(provider),
                    either -> either.xmap(FloatProvider.ConstantFloat::new, provider -> provider)
            );
        });
    }

    public Codec<DispenseSound> dispenseSound() {
        return this.init(DISPENSE_SOUND, () -> MapCodecMerger.codec(
                this.soundEvent().mapCodec(DispenseSound.SOUND).required(), DispenseSound::getSound,
                this.floatProvider().mapCodec(DispenseSound.VOLUME).required(), DispenseSound::getVolume,
                this.floatProvider().mapCodec(DispenseSound.PITCH).required(), DispenseSound::getPitch,
                DispenseSound::new
        ));
    }

    public Codec<DispenseBehavior> dispenseBehavior() {
        return this.init(DISPENSE_BEHAVIOR, () -> {
            Map<DispenseBehavior.Type, MapCodec<? extends DispenseBehavior>> codecs = new EnumMap<>(DispenseBehavior.Type.class);
            for (DispenseBehavior.Type type : DispenseBehavior.Type.values()) {
                if (type.isSimple()) codecs.put(type, MapCodec.unit(() -> new DispenseBehavior.Simple(type)));
            }
            codecs.put(DispenseBehavior.Type.SHOOT_PROJECTILE, MapCodecMerger.mapCodec(
                    this.vec3d().mapCodec(DispenseBehavior.ShootProjectile.OFFSET).required(), DispenseBehavior.ShootProjectile::getOffset,
                    Codec.DOUBLE.mapCodec(DispenseBehavior.ShootProjectile.SCALE).required(), DispenseBehavior.ShootProjectile::getScale,
                    Codec.minFloat(0).mapCodec(DispenseBehavior.ShootProjectile.UNCERTAINTY).required(), DispenseBehavior.ShootProjectile::getUncertainty,
                    Codec.minFloat(0).mapCodec(DispenseBehavior.ShootProjectile.POWER).required(), DispenseBehavior.ShootProjectile::getPower,
                    this.dispenseSound().mapCodec(DispenseBehavior.ShootProjectile.SOUND).optional().defaulted(null), DispenseBehavior.ShootProjectile::getSound,
                    DispenseBehavior.ShootProjectile::new
            ));
            codecs.put(DispenseBehavior.Type.TRANSFORM_BLOCK, MapCodecMerger.mapCodec(
                    this.registry.getRegistries().blockTransformer.entryCodec().mapCodec(DispenseBehavior.TransformBlock.TRANSFORMER).required(), DispenseBehavior.TransformBlock::getTransformer,
                    DispenseBehavior.TransformBlock::new
            ));
            return Codec.identified(DispenseBehavior.Type.values()).typed(DispenseBehavior::getType, codecs::get);
        });
    }

    public Codec<Holder<List<DispenseBehavior>>> dispenseBehaviors() {
        return this.init(DISPENSE_BEHAVIORS, () -> Holder.fileCodec(this.registry.getRegistries().dispenseBehavior, this.dispenseBehavior().listOf()));
    }

}
