package com.github.osndok.pulp.cli.wui.pages.pulp.maven.repository;

import com.github.osndok.pulp.cli.wui.base.BasicJsonGrid;
import com.github.osndok.pulp.cli.wui.model.actions.MavenRepositoryAction;
import org.apache.tapestry5.json.JSONObject;

import java.util.List;

public
class MavenRepositoryList extends BasicJsonGrid<MavenRepositoryAction>
{
    @Override
    public
    List<String> getFieldOrder()
    {
        return List.of("name", "description");
    }

    @Override
    public
    Class<MavenRepositoryAction> getActions()
    {
        return MavenRepositoryAction.class;
    }

    @Override
    public
    String getActionSubjectIdentifier(final JSONObject object)
    {
        return object.getString("pulp_href");
    }
}
