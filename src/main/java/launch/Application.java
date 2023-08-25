package launch;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import servlet.BookServlet;

import java.io.File;

public class Application {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);
        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));
        tomcat.getConnector();
        String servletName = "BookServlet";
        String urlPattern = "/books/*";
        tomcat.addServlet(contextPath,servletName, new BookServlet());
        context.addServletMappingDecoded(urlPattern,servletName);


        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work


        tomcat.start();
        tomcat.getServer().await();

    }
}
