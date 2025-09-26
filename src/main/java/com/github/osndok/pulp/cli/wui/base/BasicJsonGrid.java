package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.JsonBeanModel;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public abstract
class BasicJsonGrid extends BasePage
{
    @Inject
    private Logger log;

    public abstract
    List<String> getFieldOrder();

    public abstract
    List<String> getActions();

    public abstract
    Object getActionContext(JSONObject object);

    private
    LinkedHashSet<String> actions = new LinkedHashSet<>();

    @Property
    private
    String action;

    @Inject
    private
    Messages messages;

    @Property
    private
    BeanModel<JSONObject> beanModel;

    @Property
    private
    List<Object> gridDataSource;

    @Property
    private
    JSONObject row;

    @Inject
    private
    Block actionCell;

    @OnEvent("activate")
    public final
    void onActivateBasicJsonGrid()
    {
        actions.addAll(getActions());
        var json = executeCliCommand();
        var array = new JSONArray(json);
        gridDataSource = array.toList();

        // Create the bean model
        var fieldsAndActions = new ArrayList<>(getFieldOrder());
        fieldsAndActions.addAll(actions);

        beanModel = new JsonBeanModel(fieldsAndActions, actions, messages);
    }

    private
    String executeCliCommand()
    {
        var subCommandChain = getSubCommandChain();
        log.debug("executeCliCommand(): {}", subCommandChain);
        var procBuilder = new ProcBuilder("pulp").withArgs(subCommandChain);
        return procBuilder.run().getOutputString();
    }

    public final
    Object getActionContext()
    {
        return getActionContext(row);
    }

    public final
    PropertyOverrides getActionOverrides()
    {
        return new PropertyOverrides()
        {
            @Override
            public
            Messages getOverrideMessages()
            {
                return null;
            }

            @Override
            public
            Block getOverrideBlock(String s)
            {
                if (!s.endsWith("Cell"))
                {
                    return null;
                }
                var actionOrPropertyName = s.substring(0, s.length() - "Cell".length());
                if (!actions.contains(actionOrPropertyName))
                {
                    return null;
                }
                action = actionOrPropertyName; // UGLY SIDE-EFFECT HACK...
                return actionCell;
            }
        };
    }
}
