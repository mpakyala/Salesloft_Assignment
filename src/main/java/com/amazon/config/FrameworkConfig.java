package com.amazon.config;

import org.aeonbits.owner.Config;

@Config.Sources("file:FrameworkConfig.properties")
public interface FrameworkConfig extends Config {

    @Key("hostname")
    String hostname();


    @Key("environment")
    String environment();

}
