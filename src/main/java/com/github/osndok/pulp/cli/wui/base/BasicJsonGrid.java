package com.github.osndok.pulp.cli.wui.base;

import com.github.osndok.pulp.cli.wui.model.JsonBeanModel;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;

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

    @Inject
    private
    Messages messages;

    @Property
    private
    BeanModel<JSONObject> beanModel;

    @Property
    private
    List<Object> gridDataSource;

    @OnEvent("activate")
    public final
    void onActivateBasicJsonGrid()
    {
        var json = executeCliCommand();
        var array = new JSONArray(json);
        gridDataSource = array.toList();

        // Create the bean model
        beanModel = new JsonBeanModel(getFieldOrder(), messages);
    }

    private
    String executeCliCommand()
    {
        var subCommandChain = getSubCommandChain();
        log.debug("executeCliCommand(): {}", subCommandChain);
        var procBuilder = new ProcBuilder("pulp").withArgs(subCommandChain);
        return procBuilder.run().getOutputString();
    }
}
