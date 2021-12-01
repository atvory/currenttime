package com.atvory.currenttime.time;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;


@Configuration
public class BotConfig {

    private static final Logger log = LoggerFactory.getLogger( BotConfig.class );

    @Value("${discord.token}")
    private String discordToken;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<iEventListener<T>> eventListeners) {
        GatewayDiscordClient gatewayDiscordClient = null;

        try {
            gatewayDiscordClient = DiscordClientBuilder.create(discordToken)
              .build()
              .login()
              .block();

            for(iEventListener<T> listener : eventListeners) {
                gatewayDiscordClient.getEventDispatcher().on(listener.getEventType())
                  .flatMap(listener::execute)
                  .onErrorResume(listener::handleError)
                  .subscribe(event -> {System.out.println("Event registered: " + listener.getEventType());});
            } 
            
            gatewayDiscordClient.onDisconnect().block();
            
        }
        catch ( Exception exception ) {
            log.error( "invalid discord token", exception );
        }

        return gatewayDiscordClient;
    }
    
    
    @Bean
    public TimeResponse timeResponse() {
    	return new TimeResponse();
    }
}
