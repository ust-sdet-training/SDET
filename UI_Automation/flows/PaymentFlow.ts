import { Page, expect} from "@playwright/test";
import { PaymentPage } from "../pages/PaymentPage";
import { EnvCheck } from "../support/EnvCheck";

export class PaymentFlow{
    private readonly paymentPage: PaymentPage;


    constructor(private readonly page: Page){
        this.paymentPage = new PaymentPage(page);

    }
    async enterPaymentDetails(name: string){
        await this.paymentPage.enterNameOnCard(name);
        await this.paymentPage.enterCardNumber(EnvCheck.TESTUSER_CARD_NUMBER);
        await this.paymentPage.enterExpiry(EnvCheck.TESTUSER_CARD_EXPIRY);
        await this.paymentPage.enterCVV(EnvCheck.TESTUSER_CARD_CVV);
    }

    async completePayment(){
        await this.paymentPage.clickPayment();
    }
}