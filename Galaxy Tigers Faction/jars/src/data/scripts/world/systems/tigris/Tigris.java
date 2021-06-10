package data.scripts.world.systems.tigris;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.world.systems.addMarketplace;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Tigris {

    public void generate(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Tigris");
        system.getLocation().set(15000, -9500);
        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");

        // Main star
        PlanetAPI tigris = system.initStar("galaxytigers_tigris", "star_orange", 1000f, 500f);
		system.setLightColor(new Color(255, 168, 59));

        // Other star with its gas giant because why not
        PlanetAPI blanche = system.addPlanet("galaxytigers_blanche", tigris, "Blanche", "star_white", 500, 500f, 14000, 425);
        system.addCorona(blanche, 250f, 100f, 0.2f, 2);
        blanche.setCustomDescriptionId(null);
        PlanetAPI gassus = system.addPlanet("galaxytigers_gassus", blanche, "Gassus", "galaxytigers_gas_giant", 355, 500, 3000, 110);
        gassus.setCustomDescriptionId(null);
        PlanetAPI gassusmoon = system.addPlanet("galaxytigers_gassusmoon", gassus, "Gassus Satellite", "barren2", 100, 100, 1000, 30);
        gassusmoon.setCustomDescriptionId(null);
        system.addAsteroidBelt(gassus, 60, 1250, 280, 300, 320);
        system.addRingBand(gassus, "misc", "rings_asteroids0", 260f, 3, Color.orange, 300f, 1250, 190, Terrain.RING, "Melghorr's Ring");

		//Tergess tundra
        PlanetAPI tergess = system.addPlanet("galaxytigers_tergess", tigris, "Tergess", "galaxytigers_tundra", 40, 350, 7000, 300);
		tergess.setCustomDescriptionId("galaxytigers_planet_tergess");
		//Tergess tundra

		//Kelmine cryovolcanic
        PlanetAPI kelmine = system.addPlanet("galaxytigers_kelmine", tigris, "Kelmine", "cryovolcanic", 200, 250, 8500, 400);
		kelmine.setCustomDescriptionId(null);
		//Kelmine cryovolcanic
		
		//Assekia barren
        PlanetAPI assekia = system.addPlanet("galaxytigers_assekia", tigris, "Assekia", "barren", 200, 200, 2500, 70);
		assekia.setCustomDescriptionId("galaxytigers_planet_assekia");
		//Assekia barren

        //Enkluss volcanic
        PlanetAPI enkluss = system.addPlanet("galaxytigers_enkluss", tigris, "Enkluss", "lava_minor", 210, 210, 5000, 120);
        enkluss.setCustomDescriptionId(null);
        //Enkluss volcanic

        SectorEntityToken relay = system.addCustomEntity("galaxytigers_tigris_relay", "Tigris Relay", "comm_relay", "galaxytigers");
        relay.setCircularOrbit(tigris, 340, 5000, 120);
		
		SectorEntityToken buoy = system.addCustomEntity("galaxytigers_tigris_buoy", "Tigris Nav Buoy", "nav_buoy", "galaxytigers");
        buoy.setCircularOrbit(tigris, 90, 5000, 120);
                
        SectorEntityToken stableloc3 = system.addCustomEntity(null,null, "stable_location",Factions.NEUTRAL); 
		stableloc3.setCircularOrbitPointingDown(tigris, 183, 5000, 120);

		SectorEntityToken gate = system.addCustomEntity("galaxytigers_tigris_gate", "Tigris Gate", Entities.INACTIVE_GATE, Factions.NEUTRAL);
		gate.setCircularOrbit(tigris, 400f, 14000, 425);

		// Main asteroid belt and its decorations
        system.addAsteroidBelt(tigris, 100, 6000, 300, 460, 500);
        system.addRingBand(tigris, "misc", "rings_asteroids0", 250f, 0, Color.orange, 350f, 6000, 200, Terrain.RING, "Tiger's Separation");

        // Inner jump point
		JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint("galaxytigers_tigris_inner_jump", "Tigris Inner System Jump-point");
		OrbitAPI orbit1 = Global.getFactory().createCircularOrbit(tigris, 170, 2500, 70);
		jumpPoint1.setOrbit(orbit1);
		jumpPoint1.setRelatedPlanet(assekia);
		jumpPoint1.setStandardWormholeToHyperspaceVisual();
		system.addEntity(jumpPoint1);

		// Jump point near Tergess
		JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint("galaxytigers_tigris_tergess_jump", "Tergess Jump-point");
		OrbitAPI orbit2 = Global.getFactory().createCircularOrbit(tigris, 50, 7000, 300);
		jumpPoint2.setOrbit(orbit2);
		jumpPoint2.setRelatedPlanet(tergess);
		jumpPoint2.setStandardWormholeToHyperspaceVisual();
		system.addEntity(jumpPoint2);       

        float radiusAfter = StarSystemGenerator.addOrbitingEntities(system, tigris, StarAge.AVERAGE,
                                                                    3, 5, // min/max entities to add
                                                                    18000, // radius to start adding at
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
