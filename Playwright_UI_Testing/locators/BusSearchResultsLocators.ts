import { Locator, Page } from "@playwright/test";

export class BusSearchResultsLocators {

    constructor(private page: Page) {}

    results = (): Locator =>
        this.page.locator("#bus-results");

    busCards = (): Locator =>
        this.page.locator("article.trip-card");

    firstBusCard = (): Locator =>
        this.busCards().first();

    operatorName = (): Locator =>
        this.firstBusCard().locator(".op .row");

    busType = (): Locator =>
        this.firstBusCard().locator(".op-type");

    sleeperBadge = (): Locator =>
        this.firstBusCard().locator(".badge.badge-bus");

    fare = (): Locator =>
        this.firstBusCard().locator(".fare.price-big");

    selectSeatsButton = (): Locator =>
        this.firstBusCard().locator("a.btn.btn-bus");
}