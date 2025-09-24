package com.github.osndok.pulp.cli.wui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.time.ZonedDateTime;

/**
 * Start page of application pulp-cli-wui.
 */
public class Index {

    private static final Logger logger = LogManager.getLogger(Index.class);

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @InjectPage
    private About about;

    @Inject
    private Block block;

    // Handle call with an unwanted context
    Object onActivate(EventContext eventContext)
    {
        if (eventContext.getCount() > 0)
        {
            return new HttpError(404, "Resource not found");
        }

        return "pulp";
    }

    Object onActionFromLearnMore()
    {
        about.setLearn("LearnMore");

        return about;
    }

    @Log
    void onComplete()
    {
      logger.info("Complete call on Index page");
    }

    @Log
    void onAjax()
    {
        logger.info("Ajax call on Index page");

        ajaxResponseRenderer.addRender("middlezone", block);
    }

    public ZonedDateTime getCurrentTime()
    {
        return ZonedDateTime.now();
    }
}
