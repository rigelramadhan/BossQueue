package com.rigelramadhan.bossqueue.view.ui.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.midtrans.sdk.corekit.callback.CardRegistrationCallback
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.PaymentMethod
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.UIKitCustomSetting
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.CardRegistrationResponse
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.snap.Gopay
import com.midtrans.sdk.corekit.models.snap.Shopeepay
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.rigelramadhan.bossqueue.SdkConfig
import com.rigelramadhan.bossqueue.adapter.RemoveFoodAdapter
import com.rigelramadhan.bossqueue.databinding.ActivityBasketBinding

class BasketActivity : AppCompatActivity(), TransactionFinishedCallback {

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }

    private lateinit var basketViewModel: BasketViewModel
    private lateinit var binding: ActivityBasketBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionButtons()
        initMidtransSdk()
        loadData()
    }

    private fun loadData() {
        userId = intent.getIntExtra(EXTRA_USER_ID, 1)
        basketViewModel = BasketViewModel(userId)
        binding.rvFoods.apply {
            adapter = RemoveFoodAdapter(basketViewModel.getFoods())
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initTransactionRequest(): TransactionRequest {
        // Create new Transaction Request
        val transactionRequestNew = TransactionRequest(System.currentTimeMillis().toString() + "", 36500.0)
        transactionRequestNew.customerDetails = initCustomerDetails()
        transactionRequestNew.gopay = Gopay("mysamplesdk:://midtrans")
        transactionRequestNew.shopeepay = Shopeepay("mysamplesdk:://midtrans")
        return transactionRequestNew
    }

    private fun initCustomerDetails(): CustomerDetails {
        //define customer detail (mandatory for coreflow)
        val mCustomerDetails = CustomerDetails()
        mCustomerDetails.phone = "085310102020"
        mCustomerDetails.firstName = "user fullname"
        mCustomerDetails.email = "mail@mail.com"
        mCustomerDetails.customerIdentifier = "mail@mail.com"
        return mCustomerDetails
    }

    private fun initMidtransSdk() {
        val clientKey: String = SdkConfig.MERCHANT_CLIENT_KEY
        val baseUrl: String = SdkConfig.MERCHANT_BASE_CHECKOUT_URL
        val sdkUIFlowBuilder: SdkUIFlowBuilder = SdkUIFlowBuilder.init()
            .setClientKey(clientKey)
            .setContext(this)
            .setTransactionFinishedCallback(this)
            .setMerchantBaseUrl(baseUrl)
            .setUIkitCustomSetting(uiKitCustomSetting())
            .enableLog(true) // enable sdk log
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("en")
        sdkUIFlowBuilder.buildSDK()
    }

    override fun onTransactionFinished(result: TransactionResult) {
        if (result.response != null) {
            when (result.status) {
                TransactionResult.STATUS_SUCCESS -> Toast.makeText(this, "Transaction Finished. ID: " + result.response.transactionId, Toast.LENGTH_LONG).show()
                TransactionResult.STATUS_PENDING -> Toast.makeText(this, "Transaction Pending. ID: " + result.response.transactionId, Toast.LENGTH_LONG).show()
                TransactionResult.STATUS_FAILED -> Toast.makeText(this, "Transaction Failed. ID: " + result.response.transactionId.toString() + ". Message: " + result.response.statusMessage, Toast.LENGTH_LONG).show()
            }
            result.response.validationMessages
        } else if (result.isTransactionCanceled) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show()
        } else {
            if (result.status.equals(TransactionResult.STATUS_INVALID, true)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initActionButtons() {
        binding.cvGopay.setOnClickListener {
            startPayment(PaymentMethod.GO_PAY)
        }
        binding.cvMandiri.setOnClickListener {
            startPayment(PaymentMethod.BANK_TRANSFER_MANDIRI)
        }
        binding.cvBca.setOnClickListener {
            startPayment(PaymentMethod.BANK_TRANSFER_BCA)
        }
    }

    private fun startPayment(paymentMethod: PaymentMethod) {
        MidtransSDK.getInstance().transactionRequest = initTransactionRequest()
        MidtransSDK.getInstance().startPaymentUiFlow(this@BasketActivity, paymentMethod)
    }

    private fun uiKitCustomSetting(): UIKitCustomSetting {
        val uIKitCustomSetting = UIKitCustomSetting()
        uIKitCustomSetting.isSkipCustomerDetailsPages = true
        uIKitCustomSetting.isShowPaymentStatus = true
        return uIKitCustomSetting
    }
}