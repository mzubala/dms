package pl.com.bottega.dms.infrastructure;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.ReadingConfirmator;
import pl.com.bottega.dms.application.impl.StandardDocumentFlowProcess;
import pl.com.bottega.dms.application.impl.StandardReadingConfirmator;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.application.user.UserRepository;
import pl.com.bottega.dms.application.user.impl.StandardAuthProcess;
import pl.com.bottega.dms.application.user.impl.StandardCurrentUser;
import pl.com.bottega.dms.model.DocumentFactory;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.numbers.ISONumberGenerator;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;
import pl.com.bottega.dms.model.printing.RGBPrintCostCalculator;

import java.util.concurrent.Executor;

@org.springframework.context.annotation.Configuration
@EnableAsync
public class Configuration extends AsyncConfigurerSupport {

    @Bean
    public DocumentFlowProcess documentFlowProcess(DocumentFactory documentFactory,
                                                   PrintCostCalculator printCostCalculator,
                                                   DocumentRepository documentRepository,
                                                   CurrentUser currentUser,
                                                   ApplicationEventPublisher publisher
    ) {
        return new StandardDocumentFlowProcess(documentFactory, printCostCalculator,
                documentRepository, currentUser, publisher);
    }

    @Bean
    public NumberGenerator numberGenerator() {
        return new ISONumberGenerator();
    }

    @Bean
    public PrintCostCalculator printCostCalculator() {
        return new RGBPrintCostCalculator();
    }

    @Bean
    public DocumentCatalog documentCatalog() {
        return new JPADocumentCatalog();
    }

    @Bean
    public DocumentRepository documentRepository() {
        return new JPADocumentRepository();
    }

    @Bean
    public ReadingConfirmator readingConfirmator(DocumentRepository repo) {
        return new StandardReadingConfirmator(repo);
    }

    @Bean
    public AuthProcess authProcess(UserRepository userRepository, CurrentUser currentUser) {
        return new StandardAuthProcess(userRepository, currentUser);
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public CurrentUser currentUser() {
        return new StandardCurrentUser();
    }

    @Bean
    public DocumentFactory documentFactory(NumberGenerator numberGenerator) {
        return new DocumentFactory(numberGenerator);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*");
            }
        };
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("DMS-Async-Executor");
        executor.initialize();
        return executor;
    }


}
