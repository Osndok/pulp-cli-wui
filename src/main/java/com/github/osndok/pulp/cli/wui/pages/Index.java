package com.github.osndok.pulp.cli.wui.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.services.HttpError;

/**
 * Default page/handler for pulp-cli-wui.
 */
public class Index {

    // Handle call with an unwanted context
    Object onActivate(EventContext eventContext)
    {
        if (eventContext.getCount() > 0)
        {
            return new HttpError(404, "Resource not found");
        }

        return "pulp";
    }
}
