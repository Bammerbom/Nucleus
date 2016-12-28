/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.asm;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.listeners.ListenerBase;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.cause.Root;

import java.util.function.Predicate;

public class ASMListener implements Predicate<Nucleus> {

    private final ListenerBase base;
    private final Predicate<Nucleus> condition;

    public ASMListener(ListenerBase base, Predicate<Nucleus> condition) {
        this.base = base;
        this.condition = condition;
    }

    @Listener(order = Order.AFTER_PRE, beforeModifications = false)
    public void handle(Event event, @Root Player root) throws Exception {
        base.equals(event);
    }

    @Override public boolean test(Nucleus nucleus) {
        return condition.test(nucleus);
    }
}
