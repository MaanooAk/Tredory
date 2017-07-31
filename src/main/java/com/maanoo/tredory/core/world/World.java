// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.world;

import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class World implements IUpdate {

    public final Player player;

    public final WorldMap worldmap;

    public final Population population;

    public World(Player player, WorldMap worldmap, Population population) {
        this.player = player;
        this.worldmap = worldmap;
        this.population = population;
    }

    @Override
    public void update(int d) {
        // TODO population evolution
    }

}
