package state;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public final class CatalogProviderStates {

    private final WireMockExtension wireMock;

    public CatalogProviderStates(WireMockExtension wireMock) {
        this.wireMock = wireMock;
    }

    public void skuExists() {

        wireMock.stubFor(
                get(urlEqualTo("/catalog/SKU-1"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody("""
{
  "sku": "SKU-1",
  "name": "Running Shoes",
  "priceMinor": 129980,
  "availability": "IN_STOCK"
}
""")
                        )
        );
    }

    public void skuMissing() {

        wireMock.stubFor(
                get(urlEqualTo("/catalog/SKU-404"))
                        .willReturn(
                                aResponse()
                                        .withStatus(404)
                        )
        );
    }
}