// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.account;

import org.json.JSONObject;

import com.maanoo.tredory.IStore;
import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.InfoText;
import com.maanoo.tredory.core.achieve.Achievements;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Account implements IStore {

    public static Account active = new Account();

    public InfoText info;

    public int knowledge;

    public final Achievements achievements;

    public Account() {
        info = InfoText.create("Player");
        knowledge = 0;
        achievements = Achievements.instance;
    }

    @Override
    public JSONObject store() {
        final JSONObject o = new JSONObject();
        o.put("name", info.name);
        o.put("knowledge", knowledge);
        o.put("achievements", achievements.store());
        o.put("ops", Op.store());
        return o;
    }

    @Override
    public void load(JSONObject o) {

        info = InfoText.create(o.getString("name"));
        knowledge = o.getInt("knowledge");

        if (o.has("achievements")) {
            achievements.load(o.getJSONObject("achievements"));
        }
        if (o.has("ops")) {
            Op.load(o.getJSONObject("ops"));
        }

    }

}
