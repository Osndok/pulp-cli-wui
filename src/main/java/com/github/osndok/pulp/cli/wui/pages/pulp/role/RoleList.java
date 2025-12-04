package com.github.osndok.pulp.cli.wui.pages.pulp.role;

import com.github.osndok.pulp.cli.wui.base.BasicJsonGrid;
import com.github.osndok.pulp.cli.wui.model.actions.MavenRepositoryAction;
import com.github.osndok.pulp.cli.wui.model.actions.RoleAction;
import org.apache.tapestry5.json.JSONObject;

import java.util.List;

public
class RoleList extends BasicJsonGrid<RoleAction>
{
    @Override
    public
    List<String> getFieldOrder()
    {
        return List.of("name", "description");
    }

    @Override
    public
    Class<RoleAction> getActions()
    {
        return RoleAction.class;
    }

    @Override
    public
    String getActionSubjectIdentifier(final JSONObject object)
    {
        return object.getString("pulp_href");
    }
}
