
package client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "MyServiceService", targetNamespace = "http://server/", wsdlLocation = "http://localhost:8080/PizzaChat?wsdl")
public class MyServiceService
    extends Service
{

    private final static URL MYSERVICESERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(client.MyServiceService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = client.MyServiceService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/PizzaChat?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/PizzaChat?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        MYSERVICESERVICE_WSDL_LOCATION = url;
    }

    public MyServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MyServiceService() {
        super(MYSERVICESERVICE_WSDL_LOCATION, new QName("http://server/", "MyServiceService"));
    }

    /**
     * 
     * @return
     *     returns MyService
     */
    @WebEndpoint(name = "MyServicePort")
    public MyService getMyServicePort() {
        return super.getPort(new QName("http://server/", "MyServicePort"), MyService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MyService
     */
    @WebEndpoint(name = "MyServicePort")
    public MyService getMyServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://server/", "MyServicePort"), MyService.class, features);
    }

}