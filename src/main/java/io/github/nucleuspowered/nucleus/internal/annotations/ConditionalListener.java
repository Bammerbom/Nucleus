/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.annotations;

import io.github.nucleuspowered.nucleus.Nucleus;
import org.spongepowered.api.event.Listener;

import java.lang.annotation.*;
import java.util.function.Predicate;

/**
 * A {@link ConditionalListener} allows Nucleus to not load unnecessary listeners, when listeners only exist for compatibility fixes etc. This allows
 * us to try to keep the number of required listeners down to a minimum.
 *
 * <p>
 *     This will get unloaded and loaded during a reload event, but as this happens on the sync thread,
 *     the listeners should get called regardless.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ConditionalListener {

    /**
     * The class that determines whether to load a listener or not. The {@link Predicate#test(Object)}
     * method will determine whether the listener should be fired.
     *
     * <p>
     *     This <strong>must</strong> be a No-Args constructor class, as the class will be instantiated
     *     via reflection.
     * </p>
     *
     * @return The {@link Class} of the {@link Predicate} that will determine whether a listener should
     * be loaded.
     */
    Class<? extends Predicate<Nucleus>> value();

    /**
     * If this is annotated on a method, then this is the standard {@link Listener}
     * that would be used to register the event listeener method.
     *
     * @return The {@link Listener}
     */
    Listener listener() default @Listener;
}
