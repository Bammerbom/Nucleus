/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.dataservices;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.nucleuspowered.nucleus.dataservices.dataproviders.DataProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

public class NameBanService extends Service<Map<String, String>> {

    public NameBanService(DataProvider<Map<String, String>> dataProvider) throws Exception {
        super(dataProvider);
    }

    @Override public boolean load() {
        if (super.load()) {
            // Lowercase the keys.
            List<String> toRemove = Lists.newArrayList();
            Map<String, String> toAdd = Maps.newHashMap();
            data.forEach((k, v) -> {
                String lower = k.toLowerCase();
                if (!k.equals(lower)) {
                    toRemove.add(k);
                    toAdd.put(lower, v);
                }
            });

            toRemove.forEach(data::remove);
            data.putAll(toAdd);
            return true;
        }

        return false;
    }

    public Optional<String> getBanReason(@Nonnull String name) {
        Preconditions.checkNotNull(name);
        return Optional.ofNullable(data.get(name.toLowerCase()));
    }

    public boolean removeBan(@Nonnull String name) {
        return data.remove(name.toLowerCase()) != null;
    }

    public boolean setBan(@Nonnull String name, @Nonnull String reason) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(reason);
        Preconditions.checkArgument(!reason.isEmpty());

        if (data.containsKey(name.toLowerCase())) {
            return false;
        }

        data.put(name.toLowerCase(), reason);
        return true;
    }
}
