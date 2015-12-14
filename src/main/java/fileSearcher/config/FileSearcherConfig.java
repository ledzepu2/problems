package fileSearcher.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;

import static reactor.Environment.get;

/**
 * Created by menona on 12/13/15.
 */
@EnableAutoConfiguration
@ComponentScan({"fileSearcher"})
@EnableReactor
@EnableScheduling
public class FileSearcherConfig  {

    static {
        Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    public EventBus eventBus() {
        return EventBus.config()
                .env(get())
                .dispatcher(Environment.THREAD_POOL)
                .get();
    }

    @Bean public Logger log() {
        return LoggerFactory.getLogger(FileSearcherConfig.class);
    }

    public static void main(String... args) throws InterruptedException {

        SpringApplication.run(FileSearcherConfig.class, args);

    }






}
