package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.*;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Entities;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.procgen.NameAssigner;
import com.fs.starfarer.api.impl.campaign.procgen.NameGenData;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.rpg.Person;
import com.sun.deploy.perf.PerfRollup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class galaxytigers_campaignPlugin implements EveryFrameScript {

    /*@Override
    public String getId()
    {
        return "galaxytigers_CampaignPlugin";
    }

    @Override
    public boolean isTransient()
    {
        return true;
    }*/

    /*public void updateEntityFacts(SectorEntityToken entity, MemoryAPI memory) {
        System.out.println("AAAAAAH");
        Person commander = new Person();
        commander.setPortraitSprite("graphics/portraits/portrait_aigt.png");
        memory.getFleet(entity.getId()).getFleetData().addOfficer(commander);
        if (memory.getFleet(entity.getId()).getFaction().getId() == "galaxytigers") {
            List<FleetMemberAPI> dafleet =  memory.getFleet(entity.getId()).getFleetData().getMembersListCopy();
            for (FleetMemberAPI mem : dafleet) {
                if (mem.getVariant().hasHullMod("automated")) {
                    /*Person commander = new Person();
                    commander.setPortraitSprite("graphics/portraits/portrait_aigt.png");
                    mem.setCaptain(commander);
                }
            }
        }
    }*/

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return true;
    }

    @Override
    public void advance(float amount) {

        List <StarSystemAPI> starsystems = Global.getSector().getStarSystems();

        if (Global.getSector().isPaused()) {
            // To disable the GT hostile battle theme if it ever gets played outside of a campaign battle
            if (Global.getSoundPlayer().getCurrentMusicId().contains("bonnot_tonight_we_riot")) {
                System.out.println("Disabling hostile GT battle theme");
                //Global.getSoundPlayer().playCustomMusic(0, 0, "music_campaign");
                Global.getSoundPlayer().restartCurrentMusic();
            }

            /*for (FleetMemberAPI mem : Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy()) {
                if (mem.getVariant().hasHullMod("galaxytigers_picket_transport")) {
                    ShipVariantAPI variant = null;
                    variant.setHullVariantId("galaxytigers_picket_reinforcer_Offensive");
                    mem.getVariant().setModuleVariant("HANGAR", variant);
                }
            }*/

            //Checking every market that have player storage to check if there's a Kandorig missing its modules
            /*for (StarSystemAPI system : starsystems) {
                for (PlanetAPI planet : system.getPlanets()) {
                    if (planet.getMarket() != null) {
                        if (planet.getMarket().getSubmarket("storage") != null) {
                            for (FleetMemberAPI mem : planet.getMarket().getSubmarket("storage").getCargo().getMothballedShips().getMembersListCopy()) {
                                if (mem.getHullSpec().getHullId().contains("galaxytigers_kandorig")) {
                                    ShipVariantAPI variant = null;
                                    if (mem.getVariant().getModuleVariant("RIGHTPART") == null) {
                                        variant.setHullVariantId("galaxytigers_kandorig_right_Support");
                                        mem.getVariant().setModuleVariant("RIGHTPART", variant);
                                    }
                                    if (mem.getVariant().getModuleVariant("LEFTPART") == null) {
                                        variant.setHullVariantId("galaxytigers_kandorig_left_Support");
                                        mem.getVariant().setModuleVariant("LEFTPART", variant);
                                    }
                                }
                            }
                        }
                    }
                }
            }*/
        }
        else {
            //Checking every market that have player storage to check if there's a Kandorig missing its modules
            /*for (StarSystemAPI system : starsystems) {
                for (PlanetAPI planet : system.getPlanets()) {
                    if (planet.getMarket() != null) {
                        if (planet.getMarket().getSubmarket("storage") != null) {
                            for (FleetMemberAPI mem : planet.getMarket().getSubmarket("storage").getCargo().getMothballedShips().getMembersListCopy()) {
                                if (mem.getHullSpec().getHullId().contains("galaxytigers_kandorig")) {
                                    ShipVariantAPI variant = null;
                                    if (mem.getVariant().getModuleVariant("RIGHTPART") == null) {
                                        variant.setHullVariantId("galaxytigers_kandorig_right_Support");
                                        mem.getVariant().setModuleVariant("RIGHTPART", variant);
                                    }
                                    if (mem.getVariant().getModuleVariant("LEFTPART") == null) {
                                        variant.setHullVariantId("galaxytigers_kandorig_left_Support");
                                        mem.getVariant().setModuleVariant("LEFTPART", variant);
                                    }
                                }
                            }
                        }
                    }
                }
            }*/
            /*if (Global.getSector().getEntitiesWithTag("galaxytigers_ai_commander") != null) {
                for (SectorEntityToken ent : Global.getSector().getEntitiesWithTag("galaxytigers_ai_commander"))
                    System.out.println(ent.getId());
            }*/
            // To disable the GT hostile battle theme if it ever gets played outside of a campaign battle
            if (Global.getSoundPlayer().getCurrentMusicId().contains("bonnot_tonight_we_riot")) {
                System.out.println("Disabling hostile GT battle theme");
                //Global.getSoundPlayer().playCustomMusic(0, 0, "music_campaign");
                Global.getSoundPlayer().restartCurrentMusic();
            }
        }
        /*List<SectorEntityToken> entities = Global.getSector().getEntitiesWithTag("galaxytigers_derelicts");
        if (!entities.isEmpty()) System.out.println("AAAAAAAG");
        for (SectorEntityToken ent : entities) {
            if (ent.hasSensorStrength())
                System.out.println(ent.getTags());
        }*/
    }
}
