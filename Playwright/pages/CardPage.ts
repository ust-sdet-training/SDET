import { Page } from "@playwright/test";
import { CardLocators } from "../locators/CardLocators";
import { logger } from "../logger/logger";

export class CardPage {

    private readonly paymentLocators: CardLocators;

    constructor(page: Page) {

        this.paymentLocators = new CardLocators(page);

    }

    async enterName(name: string){
        logger.info("Entering cardholder name");
        await this.paymentLocators.cardHolderName.fill(name);
    }

    async enterCardNumber(cardNumber: string){
        logger.info("Entering card number");
        await this.paymentLocators.cardNumber.fill(cardNumber);
    }

    async enterExpiry(expiry: string){
        logger.info("Entering card expiry");
        await this.paymentLocators.expiry.fill(expiry);
    }

    async enterCVV(cvv: string){
        logger.info("Entering card CVV");
        await this.paymentLocators.cvv.fill(cvv);
    }

    async clickPay(){
        logger.info("Submitting payment");
        await this.paymentLocators.payButton.click();
    }

    async makePayment(
        name: string,cardNumber: string,expiry: string, cvv: string){
        logger.info("Starting payment flow");
        await this.enterName(name);
        await this.enterCardNumber(cardNumber);
        await this.enterExpiry(expiry);
        await this.enterCVV(cvv);
        await this.clickPay();
        logger.info("Payment flow completed");
    }

}