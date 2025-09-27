package com.github.osndok.pulp.cli.wui.pages.pulp.rpm.remote;

import com.github.osndok.pulp.cli.wui.base.BasicJsonGrid;
import com.github.osndok.pulp.cli.wui.model.actions.RpmRemoteAction;
import org.apache.tapestry5.json.JSONObject;

import java.util.List;

public
class RpmRemoteList extends BasicJsonGrid<RpmRemoteAction>
{
    @Override
    public
    List<String> getFieldOrder()
    {
        return List.of("name", "url");
    }

    @Override
    public
    Class<RpmRemoteAction> getActions()
    {
        return RpmRemoteAction.class;
    }

    @Override
    public
    String getActionIdentifier(final JSONObject object)
    {
        return object.getString("pulp_href");
    }
}
