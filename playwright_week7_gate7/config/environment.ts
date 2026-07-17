import dotenv from 'dotenv'
dotenv.config();

export class environment{

    static readonly email =  process.env.EMAIL!
    static readonly password = process.env.PASS_WORD!

    static readonly invemail = process.env.INVALIDEMAIL!
    static readonly invpass = process.env.INVALIDPASS_WORD!

    static readonly number = process.env.NUMBER!
    static readonly firstname = process.env.FIRST_NAME!
    static readonly lastname = process.env.LAST_NAME!

    static readonly cardname = process.env.CARD_NAME!
    static readonly cardnumber = process.env.CARD_NUMBER!
    static readonly expiry = process.env.EXPIRY!
    static readonly cvv = process.env.CVV!


}