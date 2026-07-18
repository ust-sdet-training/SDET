import { Locator, Page } from "@playwright/test";

export class FlightResultLocators {

    readonly resultCards: Locator;
    readonly firstFlight: Locator;
    readonly firstBookButton: Locator;

    readonly airlineFilter: Locator;
    readonly departureFilter: Locator;

    readonly priceSlider: Locator;

    readonly sortDeparture: Locator;
    readonly sortPrice: Locator;
    readonly sortRating: Locator;

    readonly resultCount: Locator;

    constructor(private page: Page) {

        this.resultCards = page.locator(".trip-card");

        this.firstFlight = page.locator(".trip-card").first();

        this.firstBookButton =
            page.locator(".trip-card .btn.btn-cta").first();

        this.airlineFilter =
            page.locator(".f-airline");

        this.departureFilter =
            page.locator(".f-dep");

        this.priceSlider =
            page.locator("#price-range");

        this.sortDeparture =
            page.locator("[data-sort='dep']");

        this.sortPrice =
            page.locator("[data-sort='price']");

        this.sortRating =
            page.locator("[data-sort='rating']");

        this.resultCount =
            page.locator("#result-live-count");
    }

}