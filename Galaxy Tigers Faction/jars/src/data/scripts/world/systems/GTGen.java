package data.scripts.world.systems;

import data.scripts.GTModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.Constellation;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SectorThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.ThemeGenContext;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.util.Pair;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import data.scripts.campaign.procgen.themes.GalaxyTigersThemeGenerator;
import data.scripts.world.systems.croissann.Croissann;
import data.scripts.world.systems.test.Testo;
import data.scripts.world.systems.tigris.Tigris;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import static com.fs.starfarer.api.impl.campaign.procgen.SectorProcGen.*;

public class GTGen implements SectorGeneratorPlugin {

    public static void initFactionRelationships(SectorAPI sector) {

        FactionAPI galaxyT = sector.getFaction("galaxytigers");
        FactionAPI galaxyTAutomated = sector.getFaction("galaxytigers_drone_guard");

        // Vanilla factions
        galaxyT.setRelationship(Factions.LUDDIC_PATH, RepLevel.HOSTILE); // If they hate technology, they hate us
        //galaxyTAutomated.setRelationship(Factions.LUDDIC_PATH, RepLevel.HOSTILE); // If they hate technology, they hate us
        galaxyT.setRelationship(Factions.HEGEMONY, RepLevel.HOSTILE); // Been enemies for years, not near a change yet
        //galaxyTAutomated.setRelationship(Factions.HEGEMONY, RepLevel.HOSTILE); // Been enemies for years, not near a change yet
        galaxyT.setRelationship(Factions.PIRATES, RepLevel.HOSTILE); // They try to steal some of our stuff, so nope
        //galaxyTAutomated.setRelationship(Factions.PIRATES, RepLevel.HOSTILE); // They try to steal some of our stuff, so nope
        galaxyT.setRelationship(Factions.LUDDIC_CHURCH, RepLevel.SUSPICIOUS); // Better terms with them but they still highly disagree with us using AIs
        //galaxyTAutomated.setRelationship(Factions.LUDDIC_CHURCH, RepLevel.SUSPICIOUS); // Better terms with them but they still highly disagree with us using AIs
        galaxyT.setRelationship(Factions.PERSEAN, RepLevel.FAVORABLE); // Helped each other during AI wars and continued on this way ever since
        //galaxyTAutomated.setRelationship(Factions.PERSEAN, RepLevel.FAVORABLE); // Helped each other during AI wars and continued on this way ever since
        galaxyT.setRelationship(Factions.INDEPENDENT, RepLevel.WELCOMING); // Cool guys helping everyone
        //galaxyTAutomated.setRelationship(Factions.INDEPENDENT, RepLevel.WELCOMING); // Cool guys helping everyone
        galaxyT.setRelationship(Factions.TRITACHYON, RepLevel.FRIENDLY); // Very good terms with 'em, but we still disagree with how they treat their competitors
        //galaxyTAutomated.setRelationship(Factions.TRITACHYON, RepLevel.FRIENDLY); // Very good terms with 'em, but we still disagree with how they treat their competitors

        // Mod factions
        FactionAPI anvil = sector.getFaction("anvil");
        FactionAPI blackrock = sector.getFaction("blackrock_driveyards");
        FactionAPI blackr_consortium = sector.getFaction("br_consortium");
        FactionAPI appro = sector.getFaction("approlight");
        FactionAPI DME = sector.getFaction("dassault_mikoyan");
        FactionAPI bureau = sector.getFaction("6eme_bureau");
        FactionAPI bladebreaker = sector.getFaction("blade_breakers");
        FactionAPI breakerdesert = sector.getFaction("the_deserter");
        FactionAPI diable = sector.getFaction("diableavionics");

        if (anvil != null) {
            galaxyT.setRelationship(anvil.getId(), RepLevel.NEUTRAL); // As long as they're good with us, we're good with 'em
            //galaxyTAutomated.setRelationship(anvil.getId(), RepLevel.NEUTRAL); // As long as they're good with us, we're good with 'em
        }
        if (blackrock != null) {
            galaxyT.setRelationship(blackrock.getId(), RepLevel.FAVORABLE); // We trade each other many useful stuff, so we're kinda associates
            //galaxyTAutomated.setRelationship(blackrock.getId(), RepLevel.FAVORABLE); // We trade each other many useful stuff, so we're kinda associates
        }
        if (blackr_consortium != null) {
            galaxyT.setRelationship(blackrock.getId(), RepLevel.FAVORABLE); // Gotta respect those protecting our friends too
            //galaxyTAutomated.setRelationship(blackrock.getId(), RepLevel.FAVORABLE); // Gotta respect those protecting our friends too
        }
        if (appro != null) {
            galaxyT.setRelationship(appro.getId(), RepLevel.NEUTRAL); // We don't agree with how they treat competitors, but very interesting tech
            //galaxyTAutomated.setRelationship(appro.getId(), RepLevel.NEUTRAL); // We don't agree with how they treat competitors, but very interesting tech
        }
        if (DME != null) {
            galaxyT.setRelationship(DME.getId(), RepLevel.WELCOMING); // They're very legit, they have nothing against competitors as we do and are very interesting partners
            //galaxyTAutomated.setRelationship(DME.getId(), RepLevel.WELCOMING); // They're very legit, they have nothing against competitors as we do and are very interesting partners
        }
        if (bureau != null) {
            galaxyT.setRelationship(bureau.getId(), RepLevel.FAVORABLE); // Partners of our partners are most of the time our partners
            //galaxyTAutomated.setRelationship(bureau.getId(), RepLevel.FAVORABLE); // Partners of our partners are most of the time our partners
        }
        if (bladebreaker != null) {
            galaxyT.setRelationship(bladebreaker.getId(), RepLevel.VENGEFUL); // But enemies of our partners can also be our enemies
            //galaxyTAutomated.setRelationship(bladebreaker.getId(), RepLevel.VENGEFUL); // But enemies of our partners can also be our enemies
        }
        if (breakerdesert != null) {
            galaxyT.setRelationship(breakerdesert.getId(), RepLevel.SUSPICIOUS); // Enemies of our enemies might not be that bad, but we keep an eye on 'em
            //galaxyTAutomated.setRelationship(breakerdesert.getId(), RepLevel.SUSPICIOUS); // Enemies of our enemies might not be that bad, but we keep an eye on 'em
        }
        if (diable != null) {
            galaxyT.setRelationship(diable.getId(), RepLevel.HOSTILE); // Sorry pals, but we absolutely don't have the same perspectives
            //galaxyTAutomated.setRelationship(diable.getId(), RepLevel.HOSTILE); // Sorry pals, but we absolutely don't have the same perspectives
        }
    }

    @Override
    public void generate(SectorAPI sector) {
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("galaxytigers");

        initFactionRelationships(sector);

        if (!GTModPlugin.NOABANDONEDGT)
            SectorThemeGenerator.generators.add(1, new GalaxyTigersThemeGenerator());

        //ThemeGenContext context = new ThemeGenContext();

        // stuff added from here; context is empty I think we need to try and make some constellations
        /*int count = 20; // how many?

        List<Constellation> constellations = new ArrayList();
        float w = Global.getSettings().getFloat("sectorWidth");
        float h = Global.getSettings().getFloat("sectorHeight");
        int cellsWide = (int) (w / CELL_SIZE);
        int cellsHigh = (int) (h / CELL_SIZE);

        boolean [][] cells = new boolean [cellsWide][cellsHigh];

        int vPad = CONSTELLATION_CELLS / 2;
        int hPad = CONSTELLATION_CELLS / 2;
        if (small) {
           hPad = (int) (31000 / CELL_SIZE);
           vPad = (int) (19000 / CELL_SIZE);
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (i <= hPad || j <= vPad || i >= cellsWide - hPad || j >= cellsHigh - vPad) {
                    cells[i][j] = true;
                }
            }
        }

        for (int k = 0; k < count; k++) {
            WeightedRandomPicker<Pair<Integer, Integer>> picker = new WeightedRandomPicker(StarSystemGenerator.random);
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    if (cells[i][j]) continue;

                    Pair<Integer, Integer> p = new Pair(i, j);
                    picker.add(p);
                }
            }

            Pair<Integer, Integer> pick = picker.pick();
            if (pick == null) continue;

            blotOut(cells, pick.one, pick.two, CONSTELLATION_CELLS);

            float x = pick.one * CELL_SIZE - w / 2f;
            float y = pick.two * CELL_SIZE - h / 2f;

            StarSystemGenerator.CustomConstellationParams params = new StarSystemGenerator.CustomConstellationParams(StarAge.ANY);

            StarAge age = StarAge.ANY;
            if (age == StarAge.ANY) {
                WeightedRandomPicker<StarAge> agePicker = new WeightedRandomPicker(StarSystemGenerator.random);
                agePicker.add(StarAge.YOUNG);
                agePicker.add(StarAge.AVERAGE);
                agePicker.add(StarAge.OLD);
                age = agePicker.pick();
            }

            params.age = age;

            params.location = new Vector2f(x, y);
            Constellation c = new StarSystemGenerator(params).generate();
            constellations.add(c);
        }

        context.constellations = constellations;

        // hopefully now context has some stuff in it

        GalaxyTigersThemeGenerator face = new GalaxyTigersThemeGenerator();
        System.out.println("Setting up those heckin' abandoned stuff");
        face.generateForSector(context, 3.0f);*/

        // Checking if Nexerelin random sector is enabled.
        if (sector.getStarSystem("Corvus") != null) {
            new Tigris().generate(sector);
            //new Testo().generate(sector);
            new Croissann().generate(sector);
        }
    }
}
