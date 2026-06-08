import {test} from '../fixtures/loginFixture'
import {testUsers } from '../fixtures/testUsers'

test.describe('Login validatation checks',()=>{
   
    test('Valid Login check with valid credentaials',async ({login})=>{
        await login.ValidLogin(
            testUsers.validCustomer.email,
            testUsers.validCustomer.password
        );
    })
    
    
    test('InValid Login check with invalid credentaials',async ({login})=>{
        await login.inValidLogin(
            testUsers.invalidCustomer.email,
            testUsers.invalidCustomer.password
        );
    })

   
   
    test('Locked account check with locked credentaials',async ({login})=>{
        await login.lockedLogin(
            testUsers.lockedCustomer.email,
            testUsers.lockedCustomer.password
        );
    })
})