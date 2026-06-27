package config;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public final class PactProviderConfig {

    private PactProviderConfig() {
    }

    public static final WireMockExtension WIRE_MOCK =
            WireMockExtension.newInstance()
                    .options(wireMockConfig().port(8081))
                    .build();
}