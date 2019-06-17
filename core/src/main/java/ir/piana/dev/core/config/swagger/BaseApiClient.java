package ir.piana.dev.core.config.swagger;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:53 PM
 **/
public interface BaseApiClient {
    String getBasePath();
    String getServicePath();
    String getServiceHost();
    Integer getServicePort();
    BaseApiClient setBasePath(String basePath);
}
