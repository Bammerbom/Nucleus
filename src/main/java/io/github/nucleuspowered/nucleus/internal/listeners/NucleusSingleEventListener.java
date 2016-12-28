/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.listeners;

import io.github.nucleuspowered.nucleus.Nucleus;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;

public interface NucleusSingleEventListener<T extends Event> extends EventListener<T> {

    boolean condition(Nucleus nucleus);
}
