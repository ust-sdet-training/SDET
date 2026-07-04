export const cases = {
  full: {
        name: 'Full return',
        qty: 4,
        expectedVerdict: 'APPROVED',
        expectedstatus:'REFUNDED',
        sku: 'TEE',
        unitprice: 33300,
        pname: "Training Tee",
        taxpaisa:4995,
        refundqty:4,      
  },
  partial:{
        name: 'Partial return',
        qty: 4,
        expectedVerdict: 'APPROVED',
        expectedstatus:'PARTIALLY_REFUNDED',
        sku: 'TEE',
        unitprice: 33300,
        pname: "Training Tee",
        taxpaisa:4995,
        refundqty:2,
  },

   p_approved :{
        name: 'Partial return',
        qty: 1,
        expectedVerdict: 'APPROVED',
        expectedstatus:'REFUNDED_WITHIN_WINDOW',
        sku: 'TEE',
        unitprice: 33300,
        pname: "Training Tee",
        taxpaisa:4995,
        refundqty:2,
  }
};
 