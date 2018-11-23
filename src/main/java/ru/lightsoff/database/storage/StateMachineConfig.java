package ru.lightsoff.database.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

@Configuration
public class StateMachineConfig {
    private StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

    @Bean
    StateMachine<States, Events> stateMachine() throws Exception {
        builder.configureStates()
                .withStates()
                    .initial(States.PASSIVE)
                    .states(EnumSet.allOf(States.class));
        builder.configureTransitions()
                .withExternal()
                    .source(States.PASSIVE)
                    .target(States.ACTIVE)
                    .event(Events.TO_ACTIVE)
                .and()
                .withExternal()
                    .source(States.ACTIVE)
                    .target(States.PASSIVE)
                    .event(Events.TO_PASSIVE);
        return builder.build();
    }
}
