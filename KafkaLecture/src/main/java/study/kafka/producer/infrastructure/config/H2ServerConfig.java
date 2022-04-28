package study.kafka.producer.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import java.sql.SQLException;

@Slf4j
@Configuration
@EnableR2dbcAuditing
public class H2ServerConfig {
    private Server webServer;
    Integer h2ConsolePort = 8090;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("starting h2 console at port {}", h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", h2ConsolePort.toString());
        this.webServer.start();
    }

    //종료이벤트
    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port {}", h2ConsolePort);
        this.webServer.stop();
    }
}

