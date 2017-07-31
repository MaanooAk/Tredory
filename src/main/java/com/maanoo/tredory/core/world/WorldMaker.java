// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.world;

import com.maanoo.tredory.core.IMaker;
import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class WorldMaker implements IMaker {

    public World make(Player player) {

        final WorldMap worldmap = new WorldMap();

        final Population population = new Population();
        population.all = 4_000_000;

        return new World(player, worldmap, population);
    }

}
