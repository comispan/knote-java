package eugenephua.com.knote_java.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Configuration
public class CustomizedRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @PostConstruct
    private void postConstruct() {
        this.setBeforeMessagePrefix("API REQUEST DATA: ");
        this.setIncludePayload(true);
        this.setIncludeQueryString(true);
    }

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String message) {
        this.logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String message) {

    }
}