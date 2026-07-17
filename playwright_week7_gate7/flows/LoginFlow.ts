import {expect,Page} from "@playwright/test"
import {FlightLoginpage} from '../pages/FlightLogin'

export class Loginflows{

    readonly ff : FlightLoginpage

    constructor(public readonly page:Page){

        this.ff = new FlightLoginpage(page)

    }

    async login(email:string, password:string)
    {
        await this.ff.home()
        
        await expect(this.page).toHaveURL("/login")
        await expect(this.page.getByRole('heading', {name:/Sign in/, level:1})).toBeVisible()
    

        await this.ff.flightLogin(email,password)
    }

    async invEmail(invalidemail:string, password:string)
    {
        await this.ff.home()
        
        await expect(this.page).toHaveURL("/login")
        await expect(this.page.getByRole('heading', {name:/Sign in/, level:1})).toBeVisible()
    

        await this.ff.flightLogin(invalidemail,password)
        await expect(this.page.getByRole('alert')).toContainText(/Invalid email/)
    }

      async invPass(email:string, invalidpassword:string)
    {
        await this.ff.home()
        
        await expect(this.page).toHaveURL("/login")
        await expect(this.page.getByRole('heading', {name:/Sign in/, level:1})).toBeVisible()
    

        await this.ff.flightLogin(email,invalidpassword)
        await expect(this.page.getByRole('alert')).toContainText(/password/)
    }


}