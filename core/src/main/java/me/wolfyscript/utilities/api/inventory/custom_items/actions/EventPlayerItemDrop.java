/*
 *       WolfyUtilities, APIs and Utilities for Minecraft Spigot plugins
 *                      Copyright (C) 2021  WolfyScript
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.wolfyscript.utilities.api.inventory.custom_items.actions;

import me.wolfyscript.utilities.util.NamespacedKey;
import org.bukkit.event.player.PlayerDropItemEvent;

public class EventPlayerItemDrop extends EventPlayer<me.wolfyscript.utilities.api.inventory.custom_items.actions.DataPlayerEvent<PlayerDropItemEvent>> {

    public static final NamespacedKey KEY = NamespacedKey.wolfyutilties("player/item_drop");

    protected EventPlayerItemDrop() {
        super(KEY, (Class<me.wolfyscript.utilities.api.inventory.custom_items.actions.DataPlayerEvent<PlayerDropItemEvent>>)(Object) DataPlayerEvent.class);
    }

}
