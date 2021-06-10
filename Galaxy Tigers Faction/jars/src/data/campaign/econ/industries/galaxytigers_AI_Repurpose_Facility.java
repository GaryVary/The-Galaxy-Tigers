package data.campaign.econ.industries;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import data.campaign.econ.galaxytigers_items;

import javax.jws.soap.SOAPBinding;

public class galaxytigers_AI_Repurpose_Facility extends BaseIndustry {
    public void apply() {
        super.apply(true);

        int size = market.getSize();

        demand(Commodities.SUPPLIES, size);
        demand(Commodities.CREW, size);
        demand(Commodities.MARINES, size + 1);

        supply(galaxytigers_items.REPURPOSEDAI, size);
    }


    @Override
    public void unapply() {
        super.unapply();
    }

    @Override
    public boolean isAvailableToBuild() {
        return false;
    }

    @Override
    public boolean showWhenUnavailable() {
        return false;
    }
}
