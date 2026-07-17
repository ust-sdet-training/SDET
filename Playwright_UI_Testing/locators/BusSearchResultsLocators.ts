import { Page } from "@playwright/test";

export class BusSearchResultsLocators {

    constructor(private page: Page) {}

    results = () =>
        this.page.locator("#bus-results");

    busCards = () =>
        this.page.locator("article.trip-card.bus-card");

    firstBusCard = () =>
        this.busCards().first();

    operatorName = () =>
        this.firstBusCard().locator(".op .row");

    busType = () =>
        this.firstBusCard().locator(".op-type");

    sleeperBadge = () =>
        this.firstBusCard().locator(".badge.badge-bus");

    fare = () =>
        this.firstBusCard().locator(".fare.price-big");

    selectSeatsButton = () =>
        this.firstBusCard().getByRole("button", {
            name: "Select Seats"
        });
}