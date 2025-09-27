package com.github.osndok.pulp.cli.wui.pages.pulp.rpm.remote;

import com.github.osndok.pulp.cli.wui.base.BasicFormBasedExecution;
import com.github.osndok.pulp.cli.wui.model.BooleanEnum;
import com.github.osndok.pulp.cli.wui.model.FileOrContents;
import org.apache.tapestry5.beaneditor.Validate;

// Todo: support the 'remote' option: --type [rpm|uln]? (must come between 'remote' and 'create' :(
public
class RpmRemoteCreate extends BasicFormBasedExecution
{
    public static
    class Command
    {
        @Validate("required")
        public String name;
        @Validate("required")
        public String url;
        public FileOrContents ca_cert;
        public FileOrContents client_cert;
        public Double connect_timeout;
        public Integer download_concurrency;
        public String password;
        public Policy policy;
        public String proxy_url;
        public String proxy_username;
        public String proxy_password;
        public Integer rate_limit;
        public String sles_auth_token;
        public Double sock_connect_timeout;
        public Double sock_read_timeout;
        public BooleanEnum tls_validation;
        public Double total_timeout;
        public String username;
    }

    public
    enum Policy
    {
        immediate,
        on_demand,
        streamed
    }

    private Command command = new Command();

    @Override
    public
    Object getCommandObject()
    {
        return command;
    }
}
