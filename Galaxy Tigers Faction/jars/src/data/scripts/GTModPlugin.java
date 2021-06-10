package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import data.scripts.plugins.galaxytigers_campaignPlugin;
import data.scripts.world.systems.GTGen;
import org.apache.log4j.Level;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GTModPlugin extends BaseModPlugin {

    private static final String GT_SETTINGS = "GTsettings.ini";
    public static boolean NOABANDONEDGT;

    private static void loadGTSettings() throws IOException, JSONException {
        JSONObject setting = Global.getSettings().loadJSON(GT_SETTINGS);
        NOABANDONEDGT = setting.getBoolean("noAbandonedGT");
    }

    private static void initGT() {
        new GTGen().generate(Global.getSector());
        Global.getSector().addScript(new galaxytigers_campaignPlugin());
    }

    @Override
    public void onNewGame() {
        initGT();
    }

    public void onApplicationLoad() {
        if (!Global.getSettings().getModManager().isModEnabled("lw_lazylib")) {
            throw new RuntimeException("Galaxy Tigers requires LazyLib to run properly." +  "\nYou can get it here : http://fractalsoftworks.com/forum/index.php?topic=5444");
        }

        try {
            loadGTSettings();
        } catch (IOException | JSONException e) {
            Global.getLogger(GTModPlugin.class).log(Level.ERROR, "GTsettings.ini loading failed!" + e.getMessage());
        }
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        MarketAPI market = Global.getSector().getEconomy().getMarket("galaxytigers_tergess");
        if (market != null) {
            PersonAPI admin = Global.getFactory().createPerson();
            admin.setFaction("galaxytigers");
            admin.setGender(FullName.Gender.MALE);
            admin.setPostId(Ranks.POST_FACTION_LEADER);
            admin.setRankId(Ranks.FACTION_LEADER);
            admin.getName().setFirst("Melvin");
            admin.getName().setLast("Grear");
            admin.setPortraitSprite("graphics/portraits/portrait13.png");

            admin.getStats().setSkillLevel(Skills.SPACE_OPERATIONS, 1);
            admin.getStats().setSkillLevel(Skills.PLANETARY_OPERATIONS, 1);
            admin.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);

            market.setAdmin(admin);
            market.getCommDirectory().addPerson(admin, 0);
            market.addPerson(admin);
        }
    }
}

