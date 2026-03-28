package org.vaadin.example;

import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@CssImport("./themes/principal/global.css")
@CssImport(value = "./themes/principal/component/vaadin-grid.css",themeFor = "vaadin-grid")
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
