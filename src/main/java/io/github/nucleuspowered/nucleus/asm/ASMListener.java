/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.asm;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.listeners.ListenerBase;
import io.github.nucleuspowered.nucleus.internal.listeners.NucleusSingleEventListener;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

import java.util.function.Predicate;

public class ASMListener implements NucleusSingleEventListener<Event> {

    private final ListenerBase base;
    private final Predicate<Nucleus> condition;

    public ASMListener(ListenerBase base, Predicate<Nucleus> condition) {
        this.base = base;
        this.condition = condition;
    }

    @Override
    public boolean condition(Nucleus nucleus) {
        return condition.test(nucleus);
    }

    @Override
    @Listener(order = Order.AFTER_PRE, beforeModifications = false)
    public void handle(Event event) throws Exception {
        base.equals(event);
    }
}
