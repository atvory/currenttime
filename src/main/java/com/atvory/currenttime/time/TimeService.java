package com.atvory.currenttime.time;

import java.util.EventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

@Service
public class TimeService implements iEventListener<MessageCreateEvent>{
    
    @Autowired
    private TimeResponse timeResponse;

    @Override
    public Class<MessageCreateEvent>getEventType(){
        return MessageCreateEvent.class;
    }

    public Mono<Void> execute(MessageCreateEvent mce){
        return processCommand(mce.getMessage());
    }

    private Mono<Void> processCommand(Message msg){
        return Mono.just(msg)
        .filter(message -> (message.getContent().startsWith("!time")))
        .flatMap(Message::getChannel)
        .flatMap(channel -> channel.createMessage(timeResponse.getUtcDateTime()))
        .then();
    }
}
