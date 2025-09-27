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
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract
class BasicJsonGrid extends BasePage
{
    private static final
    Logger log = LoggerFactory.getLogger(BasicJsonGrid.class);

    public abstract
    List<String> getFieldOrder();

    // TODO: Could this work [better?] as an Enum instead of a collection of strings?
    public abstract
    List<String> getActions();

    public abstract
    Object performAction(String action, JSONObject object);

    // If using individual action links, this gives the opportunity to specify the desired context
    // this will likely go away soon, but not quite deprecated?!?!
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
    Block singleActionCell;

    @Inject
    private
    Block actionsPopupCell;

    public
    boolean isEachActionListedIndividually()
    {
        // if true, then each action will be laid out in its own cell
        // if false, a drop-down menu will be rendered to select from the (many) possible actions
        // the intent is for pages not wanting the default behavior to override this method
        return false;
    }

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

        if (isEachActionListedIndividually())
        {
            fieldsAndActions.addAll(actions);
            beanModel = new JsonBeanModel(fieldsAndActions, actions, messages);
        }
        else
        {
            fieldsAndActions.add("actions");
            beanModel = new JsonBeanModel(fieldsAndActions, Set.of("actions"), messages);
        }
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
                if (s.equals("actionsCell"))
                {
                    return actionsPopupCell;
                }
                var actionOrPropertyName = s.substring(0, s.length() - "Cell".length());
                if (!actions.contains(actionOrPropertyName))
                {
                    return null;
                }
                action = actionOrPropertyName; // UGLY SIDE-EFFECT HACK...
                return singleActionCell;
            }
        };
    }

    public
    Object onSuccess()
    {
        log.info("onSuccess(): {} / {}", action, row);
        return performAction(action, row);
    }
}
