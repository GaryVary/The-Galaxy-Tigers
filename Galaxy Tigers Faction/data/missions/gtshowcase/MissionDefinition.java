package data.missions.gtshowcase;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.StarTypes;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

	public void defineMission(MissionDefinitionAPI api) {

		// Set up the fleets so we can add ships and fighter wings to them.
		// In this scenario, the fleets are attacking each other, but
		// in other scenarios, a fleet may be defending or trying to escape
		api.initFleet(FleetSide.PLAYER, "GTS", FleetGoal.ATTACK, false);
		api.initFleet(FleetSide.ENEMY, "ISS", FleetGoal.ATTACK, true);

//		api.getDefaultCommander(FleetSide.PLAYER).getStats().setSkillLevel(Skills.COORDINATED_MANEUVERS, 3);
//		api.getDefaultCommander(FleetSide.PLAYER).getStats().setSkillLevel(Skills.ELECTRONIC_WARFARE, 3);
		
		// Set a small blurb for each fleet that shows up on the mission detail and
		// mission results screens to identify each side.
		api.setFleetTagline(FleetSide.PLAYER, "Testing fleet");
		api.setFleetTagline(FleetSide.ENEMY, "Dummy fleet");
		

		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Test ships fromthe Galaxy Tigers mod");
		api.addBriefingItem("Eventually take down opposing fleet");
		api.addBriefingItem("Enjoy");
		
		// Set up the player's fleet.  Variant names come from the
		// files in data/variants and data/variants/fighters
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_atlas_fortress_Balanced", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_atlas_h_Elite", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_atlas_h_xiv_Elite", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_conquest_Attack", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_ethrom_Standard", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_reinforcer_Standard", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_sanctum_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_aurora_Balanced", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_fortress_Offensive", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_puma_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_kandorig_Support", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_rampart_Strike", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_shield_maiden_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_barn_Conveying", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_bandit_combat_Explosive", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_bandit_mining_Battle", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_bastillon_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_berserker_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_keeper_Support", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_taurus_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_trebuchet_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_truck_Standard", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_cerbherd_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_defender_Assault", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_hawkeye_Standard", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_picket_Strike", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_rana_Standard", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_sentry_Assault", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_shank_Attack", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_smolslaught_p_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_smolslaught_Polyvalent", FleetMemberType.SHIP,false);
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_warden_Brawling", FleetMemberType.SHIP,false);
		
		// Set up the enemy fleet.
		api.addToFleet(FleetSide.ENEMY, "onslaught_Elite", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "onslaught_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "legion_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "legion_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "legion_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "conquest_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "conquest_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "atlas2_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "atlas2_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "atlas2_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "paragon_Elite", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "astral_Elite", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "astral_Elite", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "doom_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "doom_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "doom_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "dominator_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "dominator_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "hammerhead_Balanced", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "hammerhead_Balanced", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "hammerhead_Balanced", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "enforcer_Balanced", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "enforcer_Balanced", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "drover_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "drover_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "centurion_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "centurion_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "centurion_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_Standard", FleetMemberType.SHIP, false);

		
		
		// Set up the map.
		float width = 12000f;
		float height = 12000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		float minX = -width/2;
		float minY = -height/2;
		
		// Add an asteroid field
		api.addAsteroidField(minX, minY + height / 2, 0, 8000f,
							 20f, 70f, 100);
		
		api.addPlanet(-320, -140, 350f, "galaxytigers_tundra", 250f, true);
		
	}

}
