package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.JsonBeanModel;
import com.github.osndok.pulp.cli.wui.model.actions.ActionEnum;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.util.TextStreamResponse;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
    String getActionSubjectIdentifier(JSONObject object);

    @Property
    private
    Map<String,TAction> actionsByName = new LinkedHashMap<>();

    @Property
    private
    String action;

    @Inject
    private
    Messages messages;

    @Inject
    private
    PageRenderLinkSource pageRenderLinkSource;

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
            actionsByName.put(((Enum) enumConstant).name(), enumConstant);
        }

        var json = executeCliCommand();
        var array = new JSONArray(json);
        gridDataSource = array.toList();

        // Create the bean model
        var fieldsAndActions = new ArrayList<>(getFieldOrder());

        fieldsAndActions.add("actions");
        beanModel = new JsonBeanModel(fieldsAndActions, Set.of("actions"), messages);
    }

    public
    List<String> getActionNames()
    {
        return new ArrayList<>(actionsByName.keySet());
    }

    private
    String executeCliCommand()
    {
        var subCommandChain = getSubCommandChain();
        log.debug("executeCliCommand(): {}", subCommandChain);
        var procBuilder = new ProcBuilder("pulp").withArgs(subCommandChain);
        return procBuilder.run().getOutputString();
    }

    public
    Object onSuccess()
    {
        var action = actionsByName.get(this.action);
        var actionPageClass = action.getActionPageClass();
        var id = getActionSubjectIdentifier(row);
        log.info("onSuccess(): {}, {}, {}", action, actionPageClass, id);

        if (actionPageClass == null)
        {
            var actionEnumClassName = getActions().getSimpleName();
            var message = String.format("TODO: %s.%s has no implemented page/handler", actionEnumClassName, action);
            return new TextStreamResponse("text/plain", message);
        }

        return pageRenderLinkSource.createPageRenderLinkWithContext(actionPageClass, id);
    }
}
