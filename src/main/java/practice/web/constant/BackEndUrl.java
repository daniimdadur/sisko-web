package practice.web.constant;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BackEndUrl {
    @Value("${spring.application.back-end-url}")
    private String baseUrl;

    public String fakultasUrl() {
        return Strings.concat(baseUrl, "/fakultas");
    }

    public String majorsUrl() {
        return Strings.concat(baseUrl, "/majors");
    }

    public String studentUrl() {
        return Strings.concat(baseUrl, "/student");
    }

    public String courseUrl() {
        return Strings.concat(baseUrl, "/course");
    }
}
