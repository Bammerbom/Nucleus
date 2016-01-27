package uk.co.drnaylor.minecraft.quickstart.internal;

import com.google.common.base.Preconditions;
import uk.co.drnaylor.minecraft.quickstart.QuickStart;
import uk.co.drnaylor.minecraft.quickstart.internal.annotations.Permissions;

import java.util.*;

public final class PermissionUtil {

    private final Permissions ps;
    private final String commandAlias;

    PermissionUtil(Permissions ps, String commandAlias) {
        Preconditions.checkNotNull(ps);
        this.ps = ps;
        this.commandAlias = commandAlias;
    }

    public Set<String> getBasePermissions() {
        Set<String> perms = ps.useDefault() ? getPermissionWithSuffix("base") : new HashSet<>();
        perms.addAll(Arrays.asList(ps.value()));
        return perms;
    }

    public Set<String> getWarmupPermissions() {
        Set<String> perms = ps.useDefaultWarmupExempt() ? getPermissionWithSuffix("exempt.warmup") : new HashSet<>();
        perms.addAll(Arrays.asList(ps.warmupExempt()));
        return perms;
    }

    public Set<String> getCooldownPermissions() {
        Set<String> perms = ps.useDefaultCooldownExempt() ? getPermissionWithSuffix("exempt.cooldown") : new HashSet<>();
        perms.addAll(Arrays.asList(ps.cooldownExempt()));
        return perms;
    }

    public Set<String> getCostPermissions() {
        Set<String> perms = ps.useDefaultCostExempt() ? getPermissionWithSuffix("exempt.cost") : new HashSet<>();
        perms.addAll(Arrays.asList(ps.cooldownExempt()));
        return perms;
    }

    public Set<String> getPermissionWithSuffix(String suffix) {
        Set<String> perms = new HashSet<>();
        StringBuilder perm = new StringBuilder(QuickStart.PERMISSIONS_PREFIX);
        if (!ps.root().isEmpty()) {
            perm.append(ps.root()).append(".");
        }

        perm.append(commandAlias).append(".");

        if (!ps.sub().isEmpty()) {
            perm.append(ps.sub()).append(".");
        }

        perms.add(perm.append(suffix).toString());

        if (ps.includeAdmin()) {
            perms.add(QuickStart.PERMISSIONS_ADMIN);
        }

        return perms;
    }
}