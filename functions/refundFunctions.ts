import { APIRequestContext } from "@playwright/test";

var posUrl = 'http://localhost:4000'

async function seedOrder(request:APIRequestContext, payload:object,headerparams:object) {

    var response = await request.post(posUrl+'/api/refund-lab/orders',{

        data:{
            ...payload
        },
        headers:{
            ...headerparams
        }

    })

    return response

    
}

async function refunds(request: APIRequestContext, payload:object,headers : object,idempotencyKey?:object){
     var response = await request.post("http://localhost:4000/api/refunds",{
            data:{
                ...payload
            },
            headers:{
                ...(idempotencyKey ?? {"Idempotency-Key": crypto.randomUUID()}),
                ...headers
            }
        })

        return response
}

export {seedOrder, refunds}