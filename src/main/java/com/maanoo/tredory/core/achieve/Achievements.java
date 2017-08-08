// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.achieve;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.maanoo.tredory.IStore;
import com.maanoo.tredory.core.InfoText;
import com.maanoo.tredory.core.achieve.Achievement.Status;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.entity.item.ItemType;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class Achievements implements Iterable<Achievement>, IStore {

    public static Achievements instance = new Achievements();

    private final ArrayList<Achievement> all;

    private Achievements() {
        all = new ArrayList<>();

        // @formatter:off

        AchievementFactory
            .create(InfoText.create("Kill I"), (p) -> p.stats.kills > 10)
            .child(InfoText.create("Kill II"), (p) -> p.stats.kills > 100)
            .child(InfoText.create("Kill III"), (p) -> p.stats.kills > 1000)
            .child(InfoText.create("Kill IV"), (p) -> p.stats.kills > 10000)
            .child(InfoText.create("Kill V"), (p) -> p.stats.kills > 100000)
                .collect((a) -> add(a));

        AchievementFactory
            .create(InfoText.create("Travel I"), (p) -> p.stats.maps > 2)
            .child(InfoText.create("Travel II"), (p) -> p.stats.maps > 20)
            .child(InfoText.create("Travel III"), (p) -> p.stats.maps > 200)
            .child(InfoText.create("Travel IV"), (p) -> p.stats.maps > 2000)
            .child(InfoText.create("Travel V"), (p) -> p.stats.maps > 20000)
                .collect((a) -> add(a));

        AchievementFactory
            .create(InfoText.create("Collect I"), (p) -> p.stats.coins > 10)
            .child(InfoText.create("Collect II"), (p) -> p.stats.coins > 100)
            .child(InfoText.create("Collect III"), (p) -> p.stats.coins > 1000)
            .child(InfoText.create("Collect IV"), (p) -> p.stats.coins > 10000)
            .child(InfoText.create("Collect V"), (p) -> p.stats.coins > 100000)
                .collect((a) -> add(a));

        AchievementFactory
            .create(InfoText.create("Red crystals I"), (p) -> p.crystals.count(ItemType.CrystalR) >= 12)
            .child(InfoText.create("Red crystals II"), (p) -> p.crystals.count(ItemType.CrystalR) >= 16)
                .collect((a) -> add(a));

        AchievementFactory
            .create(InfoText.create("Green crystals I"), (p) -> p.crystals.count(ItemType.CrystalG) >= 12)
            .child(InfoText.create("Green crystals II"), (p) -> p.crystals.count(ItemType.CrystalG) >= 16)
                .collect((a) -> add(a));

        AchievementFactory
            .create(InfoText.create("Blue crystals I"), (p) -> p.crystals.count(ItemType.CrystalB) >= 12)
            .child(InfoText.create("Blue crystals II"), (p) -> p.crystals.count(ItemType.CrystalB) >= 16)
                .collect((a) -> add(a));

        AchievementFactory
            .create(InfoText.create("White crystals I"), (p) -> p.crystals.count(ItemType.CrystalW) >= 12)
            .child(InfoText.create("White crystals II"), (p) -> p.crystals.count(ItemType.CrystalW) >= 16)
                .collect((a) -> add(a));

        // @formatter:on

    }

    private final void add(Achievement a) {
        all.add(a);
    }

    public boolean check(Player player) {
        int completed = 0;
        for (final Achievement i : all) {
            if (i.check(player)) {
                completed += 1;
            }
        }
        if (completed > 0) {
            updateVisibility();
            return true;
        } else {
            return false;
        }
    }

    private void updateVisibility() {
        for (final Achievement i : all) {
            if (i.status == Status.Hidden && i.parrent.status == Status.Complete) {
                i.status = Status.Viewable;
                // TODO count new available achievements
            }
        }
    }

    @Override
    public Iterator<Achievement> iterator() {
        return all.iterator();
    }

    @Override
    public JSONObject store() {
        final JSONArray array = new JSONArray();
        for (final Achievement i : all) {
            if (i.status == Status.Complete) {
                array.put(i.info.name);
            }
        }
        final JSONObject o = new JSONObject();
        o.put("complete", array);
        return o;
    }

    @Override
    public void load(JSONObject object) {
        for (final Object i : object.getJSONArray("complete")) {
            for (final Achievement a : all) {
                if (a.info.name.equals(i)) {
                    a.status = Status.Complete;
                }
            }
        }
        updateVisibility();
    }

}
