package com.demo.country_flag_api.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ConfigurationProperties(value = "api.country-flag")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {
    private String countryDataProviderUrl;
    private String countryDataProviderUrl2;
    private String apiNinjaKey;

    @Bean
    public RestClient countryClient(RestClient.Builder builder, AppConfig appConfig){
        builder = builder.defaultHeader("X-Api-Key", appConfig.getApiNinjaKey()).
                baseUrl(appConfig.getCountryDataProviderUrl());
        return builder.build();
    }

    @Bean
    public RestClient countryClient2(RestClient.Builder builder, AppConfig appConfig){
        builder = builder.baseUrl(appConfig.getCountryDataProviderUrl2());
        return builder.build();
    }
}
