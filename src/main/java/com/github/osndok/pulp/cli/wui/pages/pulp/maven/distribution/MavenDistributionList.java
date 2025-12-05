package com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution;

import com.github.osndok.pulp.cli.wui.base.BasicJsonGrid;
import com.github.osndok.pulp.cli.wui.meta.TodoMultiValue;
import com.github.osndok.pulp.cli.wui.model.actions.MavenDistributionAction;
import org.apache.tapestry5.commons.util.IntegerRange;
import org.apache.tapestry5.json.JSONObject;

import java.util.List;

public
class MavenDistributionList extends BasicJsonGrid<MavenDistributionAction>
{
    // TODO: DNW
    public static
    class Filter
    {
        public IntegerRange limit;
        public Integer offset;
        @TodoMultiValue
        public String ordering;
        @TodoMultiValue
        public String field;
        @TodoMultiValue
        public String exclude_field;
        public String name;
        public String name_contains;
        public String name_icontains;
        @TodoMultiValue
        public String name_in;
        public String label_select;
        public String base_path;
        public String base_path_contains;
    }

    @Override
    public
    List<String> getFieldOrder()
    {
        return List.of("name", "description");
    }

    @Override
    public
    Class<MavenDistributionAction> getActions()
    {
        return MavenDistributionAction.class;
    }

    @Override
    public
    String getActionSubjectIdentifier(final JSONObject object)
    {
        return object.getString("pulp_href");
    }
}
