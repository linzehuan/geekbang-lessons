package org.geektimes.projects.user.web.controller;

import org.eclipse.microprofile.config.Config;
import org.geektimes.configuration.microprofile.config.DefaultConfigProviderResolver;
import org.geektimes.configuration.microprofile.config.JavaConfig;
import org.geektimes.context.ComponentContext;
import org.geektimes.web.mvc.controller.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Lin Zehuan
 */
@Path("/config")
public class ConfigController implements RestController {



    @GET
    @Path("/get")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        DefaultConfigProviderResolver resolver = ComponentContext.getInstance().getComponent("bean/JavaConfig");
        Config javaConfig = resolver.getConfig();
        String version = javaConfig.getValue("version",String.class);
        System.out.println("version = " + version);
        boolean isOver = javaConfig.getValue("over", Boolean.class);
        System.out.println("isOver = " + isOver);
        return version;
    }
}
