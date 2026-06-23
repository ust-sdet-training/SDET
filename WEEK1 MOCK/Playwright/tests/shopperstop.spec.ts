
import {test} from "../fixture"

test("positive test ",async ({shop})=>{
    await shop.possearch("shoes");
})

test("negative test ",async ({shop})=>{
    await shop.negsearch("abdfbdfs");
})