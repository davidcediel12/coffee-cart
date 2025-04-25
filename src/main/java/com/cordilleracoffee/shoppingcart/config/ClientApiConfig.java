package com.cordilleracoffee.shoppingcart.config;


import com.cordilleracoffee.infrastructure.api.client.pricing.ApiClient;
import com.cordilleracoffee.infrastructure.api.client.pricing.PricingApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientApiConfig {


    @Bean
    public ApiClient pricingApiClient(RestClient.Builder builder, ApiClientProperties properties) {
        ApiClient apiClient = new ApiClient(builder.build());
        apiClient.setBasePath(properties.getPricingUrl());
        apiClient.addDefaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        apiClient.addDefaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return apiClient;
    }

    @Bean
    public PricingApi pricingApi(ApiClient pricingApiClient) {
        return new PricingApi(pricingApiClient);
    }
}
