package data.scripts.world.systems.test;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class Testo {

    public void generate(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Test");
        system.getLocation().set(20000, -9500);
        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");

        // Main star
        PlanetAPI tigris = system.initStar("galaxytigers_testo", "star_orange", 1000f, 500f);
		system.setLightColor(new Color(255, 168, 59));

        // Other star with its gas giant because why not
        PlanetAPI blanche = system.addPlanet("galaxytigers_test2", tigris, "Test2", "star_white", 500, 500f, 14000, 425);
        system.addCorona(blanche, 250f, 100f, 0.2f, 2);
        blanche.setCustomDescriptionId(null);
        PlanetAPI gassus = system.addPlanet("galaxytigers_gassus2", blanche, "Gassus", "gas_giant", 355, 500, 3000, 110);
        gassus.setCustomDescriptionId(null);
        gassus.setFaction(Factions.NEUTRAL);
        PlanetAPI gassusmoon = system.addPlanet("galaxytigers_gassusmoon2", gassus, "Gassus Satellite", "barren2", 100, 100, 1000, 30);
        gassusmoon.setCustomDescriptionId(null);
        gassusmoon.setFaction(Factions.NEUTRAL);
        system.addAsteroidBelt(gassus, 60, 1250, 280, 300, 320);
        system.addRingBand(gassus, "misc", "rings_asteroids0", 260f, 1, Color.orange, 260f, 1275, 190);
		
		//Tergess tundra
        PlanetAPI tergess = system.addPlanet("galaxytigers_tergess2", tigris, "Tergess", "tundra", 40, 350, 7000, 300);
		tergess.setCustomDescriptionId(null);
        tergess.setFaction(Factions.NEUTRAL);
        SectorEntityToken mining = system.addCustomEntity("galaxytigers_miningtest", "Abandoned mining station", "galaxytigers_mining", Factions.NEUTRAL);
        mining.setCircularOrbit(tergess, 340, 750, 120);
        SectorEntityToken researchbase = system.addCustomEntity("galaxytigers__researchtest", "Abandoned research station", "galaxytigers_research", Factions.NEUTRAL);
        researchbase.setCircularOrbit(tergess, 240, 750, 120);
        SectorEntityToken habitatest = system.addCustomEntity("galaxytigers_habitest", "Abandoned habitat", "galaxytigers_habitat", Factions.NEUTRAL);
        habitatest.setCircularOrbit(tergess, 140, 750, 120);
		//Tergess tundra
		
		//Kelmine cryovolcanic
        PlanetAPI kelmine = system.addPlanet("galaxytigers_kelmine2", tigris, "Kelmine", "cryovolcanic", 200, 250, 8500, 400);
		kelmine.setCustomDescriptionId(null);
        kelmine.setFaction(Factions.NEUTRAL);
		//Kelmine cryovolcanic
		
		//Assekia barren
        PlanetAPI assekia = system.addPlanet("galaxytigers_assekia2", tigris, "Assekia", "barren", 200, 200, 2500, 70);
		assekia.setCustomDescriptionId(null);
        assekia.setFaction(Factions.NEUTRAL);
		//Assekia barren

        //Enkluss volcanic
        PlanetAPI enkluss = system.addPlanet("galaxytigers_enkluss2", tigris, "Enkluss", "lava_minor", 210, 210, 5000, 120);
        enkluss.setCustomDescriptionId(null);
        enkluss.setFaction(Factions.NEUTRAL);
        //Enkluss volcanic

        SectorEntityToken relay = system.addCustomEntity("galaxytigers_tigris_relay2", "Tigris Relay", "comm_relay", Factions.NEUTRAL);
        relay.setCircularOrbit(tigris, 340, 5000, 120);
		
		SectorEntityToken buoy = system.addCustomEntity("galaxytigers_tigris_buoy2", "Tigris Nav Buoy", "nav_buoy", Factions.NEUTRAL);
        buoy.setCircularOrbit(tigris, 90, 5000, 120);
                
        SectorEntityToken stableloc3 = system.addCustomEntity(null,null, "stable_location",Factions.NEUTRAL); 
		stableloc3.setCircularOrbitPointingDown(tigris, 183, 5000, 120);

		// Main asteroid belt and its decorations
        system.addAsteroidBelt(tigris, 100, 6000, 300, 460, 500);
        system.addRingBand(tigris, "misc", "rings_asteroids0", 280f, 1, Color.orange, 280f, 6050, 200);

        // Inner jump point
		JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint("galaxytigers_tigris_inner_jump2", "Test Inner System Jump-point");
		OrbitAPI orbit1 = Global.getFactory().createCircularOrbit(tigris, 50, 7000, 300);
		jumpPoint1.setOrbit(orbit1);
		jumpPoint1.setRelatedPlanet(assekia);
		jumpPoint1.setStandardWormholeToHyperspaceVisual();
		system.addEntity(jumpPoint1);

		// Jump point near Tergess
		JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint("galaxytigers_tigris_tergess_jump2", "Tergess Jump-point");
		OrbitAPI orbit2 = Global.getFactory().createCircularOrbit(tigris, 70, 1500, 65);
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
