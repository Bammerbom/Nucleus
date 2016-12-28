/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.home.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class HomeConfig {

    @Setting(value = "use-safe-warp", comment = "loc:config.home.safeTeleport")
    private boolean safeTeleport = true;

    @Setting(value = "respawn-at-home", comment = "loc:config.home.respawnAtHome")
    private boolean respawnAtHome = false;

    public boolean isSafeTeleport() {
        return safeTeleport;
    }

    public boolean isRespawnAtHome() {
        return respawnAtHome;
    }
}
