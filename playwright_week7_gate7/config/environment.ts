import dotenv from 'dotenv'
dotenv.config();

export class environment{

    static readonly email =  process.env.EMAIL!
    static readonly password = process.env.PASS_WORD!

    static readonly invemail = process.env.INVALIDEMAIL!
    static readonly invpass = process.env.INVALIDPASS_WORD!

}