import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.moxy.xml.MoxyXmlFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JerseyServer {
    public static void main(String[] args) {
        ResourceConfig config = new ResourceConfig();
        config.register(MoxyXmlFeature.class);
        config.register(JacksonFeature.class);
        config.packages("user");

        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/services/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            server.destroy();
        }
    }
}
