package com.example.lnm.payments.presentation.features.make_payment_request.state

data class MakeMpesaPaymentForm(
    val amount: String = "",
    val phone: String = "",
    val account: String = "",
    val callbackUrl: String = "",
    val description: String = "",
    val shortCode: String = "",
    val passKey: String = "",
    val consumerKey: String = "",
    val consumerSecret: String = "",
) : MakePaymentForm {
    
   companion object {
       fun dummyPrefill() : MakeMpesaPaymentForm {
           return MakeMpesaPaymentForm(
               amount = "1",
               phone = "254",
               account = "test",
               callbackUrl = "https://webhook.site/b2286963-22a0-458a-a05c-eb3910f392b4",
               description = "test",
               shortCode = "174379",
               passKey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
               consumerKey = "NuZJqLctl7ogTqgjA5g1qGQtiEdMcvDv",
               consumerSecret = "pNYp36UnVGJqGD0p"
           )
       }
   }
}
