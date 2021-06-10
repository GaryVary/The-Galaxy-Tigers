package data.scripts.world.systems.croissann;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.StarCoronaTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.fleet.CampaignFleet;
import data.scripts.world.systems.addMarketplace;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Croissann {

    public void generate(SectorAPI sector) {

        Random r = new Random();
        int w = r.nextInt(60000);
        int h = r.nextInt(40000);

        while (w<30000){w = r.nextInt(40000);}

        StarSystemAPI system = sector.createStarSystem("Croissann Alpha");
        system.getLocation().set(w, h);
        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");

        // Main star
        //PlanetAPI croissann = system.initStar("galaxytigers_croissannalpha", "star_neutron", 2000f, 1000f);
        //PlanetAPI croissann = system.initStar("galaxytigers_croissannalpha", "star_neutron", 2000f, 1000f, 30000f, 1);
        PlanetAPI croissann = system.initStar("galaxytigers_croissannalpha", StarTypes.NEUTRON_STAR, 500f, 1000, 20f, 1f, 6.0f);
		system.setLightColor(new Color(255, 255, 255));

        SectorEntityToken croissannpulsar = system.addTerrain(Terrain.PULSAR_BEAM,
                new StarCoronaTerrainPlugin.CoronaParams(27500,
                        12500,
                        croissann,
                        10f,
                        1f,
                        4f)
        );
        croissannpulsar.setCircularOrbit(croissann, 0, 0, 15);

        // Other star
        PlanetAPI croissannbis = system.addPlanet("galaxytigers_croissannbeta", croissann, "Croissann Beta", "star_white", 300, 1000f, 8000, 300);
        system.addCorona(croissannbis, 500f, 100f, 0.2f, 2);

        SectorEntityToken relay = system.addCustomEntity("galaxytigers_croissann_station", "Abandoned GT Questionnable Research Base", "galaxytigers_research_memes", Factions.NEUTRAL);
        relay.setCircularOrbit(croissannbis, 300, 3000, 200);

        float radiusAfter = StarSystemGenerator.addOrbitingEntities(system, croissann, StarAge.AVERAGE,
                                                                    3, 5, // min/max entities to add
                                                                    4000, // radius to start adding at
                                                                    5, // name offset - next planet will be <system name> <roman numeral of this parameter + 1>
                                                                    true); // whether to use custom or system-name based names

        system.autogenerateHyperspaceJumpPoints(true, true);
		
		//Getting rid of some hyperspace nebula, just in case
        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }

 //   void cleanup(StarSystemAPI system) {
      //  HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
      //  NebulaEditor editor = new NebulaEditor(plugin);
      //  float minRadius = plugin.getTileSize() * 2f;

      //  float radius = system.getMaxRadiusInHyperspace();
      //  editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 0.5f, 0, 360f);
     //   editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    //}
}
