package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.JsonBeanModel;
import com.github.osndok.pulp.cli.wui.model.actions.ActionEnum;
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
class BasicJsonGrid<TAction extends ActionEnum> extends BasePage
{
    private static final
    Logger log = LoggerFactory.getLogger(BasicJsonGrid.class);

    public abstract
    List<String> getFieldOrder();

    public abstract
    Class<TAction> getActions();

    // If using individual action links, this gives the opportunity to specify the desired context
    // this will likely go away soon, but not quite deprecated?!?!
    public abstract
    String getActionIdentifier(JSONObject object);

    @Property
    private
    List<String> actionStrings = new ArrayList<>();

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
    Block actionsPopupCell;

    @OnEvent("activate")
    public final
    void onActivateBasicJsonGrid()
    {
        for (TAction enumConstant : getActions().getEnumConstants())
        {
            actionStrings.add(((Enum) enumConstant).name());
        }

        var json = executeCliCommand();
        var array = new JSONArray(json);
        gridDataSource = array.toList();

        // Create the bean model
        var fieldsAndActions = new ArrayList<>(getFieldOrder());

        fieldsAndActions.add("actions");
        beanModel = new JsonBeanModel(fieldsAndActions, Set.of("actions"), messages);
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
    Object getActionIdentifier()
    {
        return getActionIdentifier(row);
    }

    public
    Object onSuccess()
    {
        log.info("onSuccess(): {} / {}", action, row);
        //return performAction(action, row);
        return null;
    }
}
