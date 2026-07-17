import dotenv from "dotenv";
dotenv.config();
export class Env
    {
       static  readonly baseURL= process.env.BASEURL!;
        static readonly username= process.env.TRIPSTACK_USERNAME!;
        static readonly password=process.env.SHOPKART_PASSWORD!;
        static readonly from=process.env.FROM_ADDRESS!;
        static readonly to=process.env.TO_ADDRESS!;
        static readonly date=process.env.DATE!;
        static readonly firstname=process.env.FIRSTNAME!;
        static readonly lastname=process.env.LASTNAME!;
        static readonly email=process.env.EMAIL!;
        static readonly age=process.env.AGE!;
        static readonly ph_no=process.env.PH_NO!;
        static readonly cvv=process.env.CVV!;
        static readonly exp=process.env.EXP!;
        static readonly cardnumber=process.env.CARDNUMBER!;


    }