// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import java.util.ArrayList;

import com.maanoo.tredory.core.entity.Collision;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.entities.Item;
import com.maanoo.tredory.core.entity.entities.ItemType;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.map.Map;
import com.maanoo.tredory.core.map.MapMaker;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * @author MaanooAk
 */
public class Core implements IUpdate {

    public static Core c; // TODO remove this at some point

    public static void addEntity(Entity ent) {
        c.entities.add(ent);
    }

    public Player player;
    public Map map;

    public final Entities entities;

    public Point camera;

    private boolean request_newMap = false;

    public float arrow_angle;

    public Core() {

        entities = new Entities();

    }

    public void init() {

        entities.clear();

        player = new Player(Team.Good, new Point(2000, 2000), 0, new SpriteBundleEntity(Assets.chara.get()));
        entities.addPlayer(player);

        newMap();
    }

    private void newMap() {

        entities.clearNonPlayers();

        map = MapMaker.make();

        player.location.set(map.spawn);

        entities.addAll(map.l);
    }

    public void requestNewMap() {
        request_newMap = true;
    }

    @Override
    public void update(int d) {

        if (request_newMap) {
            request_newMap = false;

            Stats.exitMap(map);

            Assets.trans.play();

            newMap();
        }

        camera = player.location;

        arrow_angle = map.hotspots.get(0).location.clone().sub(player.location).angle();

        map.update(d);

        for (final Entity i : entities.getAll()) {
            if (!i.dead) i.update(d);
        }

        entities.removeDead();

        // collision detection
        Collision.perform(entities.getAll());

        entities.perform();

    }

    public Entity findClossest(Entity ent, Team team) {
        return findClossest(ent, team, Float.MAX_VALUE);
    }

    public Entity findClossest(Entity ent, Team team, float radius) {

        Entity min = null;
        float min_dis = Float.MAX_VALUE;

        for (final Entity i : entities.getAll()) {

            if (i == ent) continue;
            if (i.team != team) continue;
            if (i.undead) continue;

            final float dis = i.location.distance(ent.location);
            if (dis <= radius && dis < min_dis) {
                min_dis = dis;
                min = i;
            }

        }

        return min;
    }

    // TODO stop returning arraylists, pass the list as parameter?

    public ArrayList<Entity> findAll(Entity ent, Team team, float radius) {
        final ArrayList<Entity> ret = new ArrayList<>();

        for (final Entity i : entities.getAll()) {

            if (i == ent) continue;
            if (i.team != team) continue;
            if (i.undead) continue;

            final float dis = i.location.distance(ent.location);
            if (dis <= radius) {
                ret.add(i);
            }

        }

        return ret;
    }

    public ArrayList<Item> findItems(Point location, int radius) {
        final ArrayList<Item> ret = new ArrayList<>();

        for (final Entity i : entities.getAll()) {

            if (!i.pickable) continue;

            final float dis = i.location.distance(location);
            if (dis <= radius) {
                ret.add((Item) i);
            }

        }

        return ret;
    }

    // TODO pass the entity who triggers
    public void activateStepables(Point location, int radius) {

        for (final Entity i : entities.getAll()) {

            if (!i.stepable) continue;

            final float dis = i.location.distance(location);
            if (dis <= radius) {
                i.activate();
            }
        }
    }

    // TODO pass the entity who triggers
    public void activateActivatables(Point location, int radius) {

        for (final Entity i : entities.getAll()) {

            if (!i.activatable) continue;

            final float dis = i.location.distance(location);
            if (dis <= radius) {
                i.activate();
            }
        }
    }

    public void addItem(Entity con, int tier) {
        Item item;
        final Point p = con.location.clone();

        switch (tier) {
        case 0:

            if (Ra.chance(0.05f)) {

                final int value = Ra.range(0, 5) + Ra.range(1, 5);
                Drops.dropCoins(this, con, 0, 360, .1f, .2f, value);
            }

            break;
        case 1:

            if (Ra.chance(0.75f)) {

                final ItemType type = Ra.list(new ItemType[] { ItemType.Shield0, ItemType.Shield1, ItemType.Shield1,
                        ItemType.Shield1, ItemType.Shield2, ItemType.Shield2, ItemType.Shield3 });

                item = new Item(type, p);
                item.push(con.angle + 180, 0.2f);
                entities.add(item);
            } else {
                int value = Ra.range(2, 22) + Ra.range(2, 22);

                while (value % 10 > 0) {
                    item = new Item(ItemType.Copper, p.clone().add(new Point(Ra.range(-8, 8), Ra.range(-8, 8))));
                    item.push(con.angle + 180 + Ra.range(-40f, 40f), Ra.range(0.1f, 0.4f));
                    entities.add(item);

                    value -= 1;
                }
                while (value >= 10) {
                    item = new Item(ItemType.Gold, p.clone().add(new Point(Ra.range(-4, 4), Ra.range(-4, 4))));
                    item.push(con.angle + 180 + Ra.range(-40f, 40f), Ra.range(0.1f, 0.4f));
                    entities.add(item);

                    value -= 10;
                }

            }
            break;
        case 2:

            final ItemType type = Ra.list(new ItemType[] { ItemType.Crystal1, ItemType.Crystal2, ItemType.Crystal3 });

            item = new Item(type, p);
            item.push(con.angle + 180, 0.1f);
            entities.add(item);

            break;
        case 4:

            item = new Item(ItemType.Stone, p);
            item.push(con.angle + 180, 0.1f);
            entities.add(item);

            break;
        }
    }

    public void dropItem(Entity ent, Item i) {
        i.dead = false;
        i.location = ent.location.clone();

        i.push(ent.angle + Ra.range(-30, 30), 0.3f);

        entities.add(i);

    }

    public void removeItem(Item i) {
        i.location = null;
        i.dead = true;
        entities.remove(i);

        Assets.pick.play();
    }
}
