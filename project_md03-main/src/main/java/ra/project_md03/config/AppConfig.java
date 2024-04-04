package ra.project_md03.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ra.project_md03") // tu dong quet bat dau
@PropertySource("classpath:config.properties")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean

    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }


    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }

    @Bean
    public Storage storage() throws IOException {
        InputStream inputStream = new ClassPathResource("firebase-config.json").getInputStream();
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase-config.json");
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build()
                .getService();
    }

    //cau hinh upload file
    @Bean
    CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(52428800);
        return multipartResolver;
    }

    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**",
//                        "/fonts/**",
//                        "/images/**",
//                        "/js/**",
//                        "/svg/**",
//                        "/user/css/**",
//                        "/user/fonts/**",
//                        "/user/images/**",
//                        "/user/js/**",
//                        "/user/bootstrap/**",
//                        "/user/owlcarousel/**")
//                .addResourceLocations("classpath:/assets/admin/css/",
//                        "classpath:/assets/admin/fonts/",
//                        "classpath:/assets/admin/images/",
//                        "classpath:/assets/admin/js/", "classpath:/assets/admin/svg/","classpath:assets/user/","classpath:assets/user/","classpath:assets/user/","classpath:assets/user/","classpath:assets/user/","classpath:assets/user/");
//    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/ckeditor/**",
                        "/admin/**",
                        "/user/**")
                .addResourceLocations("classpath:/ckeditor/",
                        "classpath:/assets/admin/",
                        "classpath:/assets/user/"
                     );
    }


}
