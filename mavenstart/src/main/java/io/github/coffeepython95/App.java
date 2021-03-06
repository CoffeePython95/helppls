package io.github.coffeepython95;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        var webapp = new WebAppContext(); //setting up jetty\
        webapp.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false"); // needed to be able to change index.html on fly when app is running
        webapp.setResourceBase("src/main/webapp"); //rigid path to webapp
        webapp.setContextPath("/"); //all requests starts from '/'
        webapp.setConfigurations(new Configuration[]
                {
                        new AnnotationConfiguration(),
                        new WebInfConfiguration(),
                        new WebXmlConfiguration(),
                        new MetaInfConfiguration(),
                        new FragmentConfiguration(),
                        new EnvConfiguration(),
                        new PlusConfiguration(),
                        new JettyWebXmlConfiguration()
                }); //config
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");
        //webapp.addServlet(HelloServlet.class, "/api/*"); out cause we want to scan annotations
        var server = new Server(8080);
        server.setHandler(webapp);

        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStopped(LifeCycle event) {
                HibernateUtil.close();
            }
        });
        server.start();
        server.join();
    }
}
