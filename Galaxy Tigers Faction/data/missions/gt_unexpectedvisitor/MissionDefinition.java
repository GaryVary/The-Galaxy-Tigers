package data.missions.gt_unexpectedvisitor;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.StarTypes;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

	public void defineMission(MissionDefinitionAPI api) {

		// Set up the fleets so we can add ships and fighter wings to them.
		// In this scenario, a fleet is defending and another one attacking, but
		// in other scenarios, a fleet may be defending or trying to escape
		api.initFleet(FleetSide.PLAYER, "GTS", FleetGoal.ATTACK, false);	// Fleet goal can also be "FleetGoal.ESCAPE" for pursuit battles.
		api.initFleet(FleetSide.ENEMY, "DSS", FleetGoal.ATTACK, true);
		
		// Set a small blurb for each fleet that shows up on the mission detail and
		// mission results screens to identify each side.
		api.setFleetTagline(FleetSide.PLAYER, "Galaxy Tigers 36th Claw Pack");
		api.setFleetTagline(FleetSide.ENEMY, "Mothership and its escort");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Disable the mothership, hopefully this will calm these drones down");
		api.addBriefingItem("The GTS Tigris Warden must survive");
		
		// Set up the player's fleet.  Variant names come from the
		// files in data/variants
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_conquest_Offensive", FleetMemberType.SHIP, "GTS Tigris Warden", true);			// Player's flagship
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_aurora_Strike", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_puma_Polyvalent", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_bandit_combat_Hammering", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_bandit_combat_AntiShield", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_cerbherd_Polyvalent2", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_cerbherd_Polyvalent2", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_cerbherd_Strike2", FleetMemberType.SHIP, false);						// Another friendly ship (not named)
		api.addToFleet(FleetSide.PLAYER, "galaxytigers_smolslaught_Strike", FleetMemberType.SHIP, false);						// Another friendly ship (not named)

		// Set up the enemy fleet.
		api.addToFleet(FleetSide.ENEMY, "galaxytigers_survey_mothership_Standard", FleetMemberType.SHIP, "New Earth Prospector", true);		// Enemy ship
		api.addToFleet(FleetSide.ENEMY, "rampart_Standard", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "rampart_Standard", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "bastillon_Standard", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "bastillon_Standard", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "berserker_Assault", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "berserker_Assault", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "defender_PD", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "defender_PD", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "picket_Assault", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "picket_Assault", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "picket_Assault", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "sentry_FS", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "sentry_FS", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "warden_Defense", FleetMemberType.SHIP, false);		// Another enemy ship
		api.addToFleet(FleetSide.ENEMY, "warden_Defense", FleetMemberType.SHIP, false);		// Another enemy ship

		api.defeatOnShipLoss("GTS Tigris Warden");	// The mission ends if the ship with this name dies. Can be friendly or enemy.
		api.defeatOnShipLoss("New Earth Prospector");
		
		// Set up the map.
		float width = 12000f;	// 1000 units = about one grid cell on the tactical map
		float height = 12000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		float minX = -width/2;
		float minY = -height/2;
		
		// Add an asteroid field
		api.addAsteroidField(minX, minY + height / 2, 0, 8000f,
							 20f, 100f, 50);
		
	}

}
