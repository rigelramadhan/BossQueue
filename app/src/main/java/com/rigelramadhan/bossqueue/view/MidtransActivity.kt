//package com.rigelramadhan.bossqueue.view
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import com.midtrans.sdk.corekit.callback.CardRegistrationCallback
//import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
//import com.midtrans.sdk.corekit.core.MidtransSDK
//import com.midtrans.sdk.corekit.core.PaymentMethod
//import com.midtrans.sdk.corekit.core.TransactionRequest
//import com.midtrans.sdk.corekit.core.UIKitCustomSetting
//import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
//import com.midtrans.sdk.corekit.models.CardRegistrationResponse
//import com.midtrans.sdk.corekit.models.CustomerDetails
//import com.midtrans.sdk.corekit.models.snap.Gopay
//import com.midtrans.sdk.corekit.models.snap.Shopeepay
//import com.midtrans.sdk.corekit.models.snap.TransactionResult
//import com.midtrans.sdk.uikit.SdkUIFlowBuilder
//import com.rigelramadhan.bossqueue.R
//import com.rigelramadhan.bossqueue.SdkConfig
//import com.rigelramadhan.bossqueue.databinding.ActivityMidtransBinding
//
//class MidtransActivity : AppCompatActivity(), TransactionFinishedCallback {
class MidtransActivity {}
//
//    private lateinit var binding: ActivityMidtransBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMidtransBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        initActionButtons()
//        initMidtransSdk()
//    }
//
//    private fun initTransactionRequest(): TransactionRequest {
//        // Create new Transaction Request
//        val transactionRequestNew = TransactionRequest(System.currentTimeMillis().toString() + "", 36500.0)
//        transactionRequestNew.customerDetails = initCustomerDetails()
//        transactionRequestNew.gopay = Gopay("mysamplesdk:://midtrans")
//        transactionRequestNew.shopeepay = Shopeepay("mysamplesdk:://midtrans")
//        return transactionRequestNew
//    }
//
//    private fun initCustomerDetails(): CustomerDetails {
//        //define customer detail (mandatory for coreflow)
//        val mCustomerDetails = CustomerDetails()
//        mCustomerDetails.phone = "085310102020"
//        mCustomerDetails.firstName = "user fullname"
//        mCustomerDetails.email = "mail@mail.com"
//        mCustomerDetails.customerIdentifier = "mail@mail.com"
//        return mCustomerDetails
//    }
//
//    private fun initMidtransSdk() {
//        val clientKey: String = SdkConfig.MERCHANT_CLIENT_KEY
//        val baseUrl: String = SdkConfig.MERCHANT_BASE_CHECKOUT_URL
//        val sdkUIFlowBuilder: SdkUIFlowBuilder = SdkUIFlowBuilder.init()
//            .setClientKey(clientKey) // client_key is mandatory
//            .setContext(this) // context is mandatory
//            .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
//            .setMerchantBaseUrl(baseUrl) //set merchant url
//            .setUIkitCustomSetting(uiKitCustomSetting())
//            .enableLog(true) // enable sdk log
//            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // will replace theme on snap theme on MAP
//            .setLanguage("en")
//        sdkUIFlowBuilder.buildSDK()
//    }
//
//    override fun onTransactionFinished(result: TransactionResult) {
//        if (result.response != null) {
//            when (result.status) {
//                TransactionResult.STATUS_SUCCESS -> Toast.makeText(this, "Transaction Finished. ID: " + result.response.transactionId, Toast.LENGTH_LONG).show()
//                TransactionResult.STATUS_PENDING -> Toast.makeText(this, "Transaction Pending. ID: " + result.response.transactionId, Toast.LENGTH_LONG).show()
//                TransactionResult.STATUS_FAILED -> Toast.makeText(this, "Transaction Failed. ID: " + result.response.transactionId.toString() + ". Message: " + result.response.statusMessage, Toast.LENGTH_LONG).show()
//            }
//            result.response.validationMessages
//        } else if (result.isTransactionCanceled) {
//            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show()
//        } else {
//            if (result.status.equals(TransactionResult.STATUS_INVALID, true)) {
//                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    private fun initActionButtons() {
//        binding.buttonUikit.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity)
//        }
//        binding.buttonDirectCreditCard.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().UiCardRegistration(this@MidtransActivity, object :
//                CardRegistrationCallback {
//                override fun onSuccess(cardRegistrationResponse: CardRegistrationResponse?) {
//                    Toast.makeText(this@MidtransActivity, "register card token success", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onFailure(cardRegistrationResponse: CardRegistrationResponse?, s: String?) {
//                    Toast.makeText(this@MidtransActivity, "register card token Failed", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onError(throwable: Throwable?) {}
//            })
//        }
//        binding.buttonDirectBcaVa.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity, PaymentMethod.BANK_TRANSFER_BCA)
//        }
//        binding.buttonDirectBniVa.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity, PaymentMethod.BANK_TRANSFER_BNI)
//        }
//        binding.buttonDirectMandiriVa.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity, PaymentMethod.BANK_TRANSFER_MANDIRI)
//        }
//        binding.buttonDirectPermataVa.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity, PaymentMethod.BANK_TRANSFER_PERMATA)
//        }
//        binding.buttonDirectAtmBersamaVa.setOnClickListener {
//            MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity, PaymentMethod.BCA_KLIKPAY)
//        }
//        binding.buttonSnapPay.setOnClickListener {
//            val snaptokenValue: String = binding.editSnaptoken.editableText.toString()
//            MidtransSDK.getInstance().startPaymentUiFlow(this@MidtransActivity, snaptokenValue)
//        }
//    }
//
//    private fun uiKitCustomSetting(): UIKitCustomSetting {
//        val uIKitCustomSetting = UIKitCustomSetting()
//        uIKitCustomSetting.isSkipCustomerDetailsPages = true
//        uIKitCustomSetting.isShowPaymentStatus = true
//        return uIKitCustomSetting
//    }
//}