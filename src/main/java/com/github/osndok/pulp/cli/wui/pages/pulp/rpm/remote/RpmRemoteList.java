package com.github.osndok.pulp.cli.wui.pages.pulp.rpm.remote;

import com.github.osndok.pulp.cli.wui.base.BasicJsonGrid;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;

import java.util.List;

public
class RpmRemoteList extends BasicJsonGrid
{
    @Override
    public
    List<String> getFieldOrder()
    {
        return List.of("name", "url");
    }

    @Override
    public
    List<String> getActions()
    {
        return List.of("show", "update", "destroy");
    }

    @Override
    public
    Object performAction(final String action, final JSONObject object)
    {
        var pulp_href = object.get("pulp_href");
        return new TextStreamResponse("text/plain", "todo: redirect to: "+action+" "+pulp_href);
    }

    // unused when using drop-down actions
    @Override
    public
    Object getActionContext(final JSONObject object)
    {
        return object.get("pulp_href");
    }
}
