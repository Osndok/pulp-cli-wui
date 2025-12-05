package com.github.osndok.pulp.cli.wui.model.actions;

import com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.MavenDistributionDestroy;
import com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.MavenDistributionShow;
import com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.MavenDistributionUpdate;
import com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.label.MavenDistributionLabelSet;
import com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.label.MavenDistributionLabelShow;
import com.github.osndok.pulp.cli.wui.pages.pulp.maven.distribution.label.MavenDistributionLabelUnset;

public
enum MavenDistributionAction implements ActionEnum
{
    destroy(MavenDistributionDestroy.class),
    label_set(MavenDistributionLabelSet.class),
    label_show(MavenDistributionLabelShow.class),
    label_unset(MavenDistributionLabelUnset.class),
    show(MavenDistributionShow.class),
    update(MavenDistributionUpdate.class),
    ;

    private final Class actionPageClass;

    MavenDistributionAction()
    {
        actionPageClass = null;
    }

    MavenDistributionAction(Class actionPageClass)
    {
        this.actionPageClass = actionPageClass;
    }

    @Override
    public
    Class getActionPageClass()
    {
        return actionPageClass;
    }
}
