package com.example.tourist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tourist.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() , PaymentResultListener {
    lateinit var binding: ActivityPaymentBinding
    private lateinit var amountEdt: EditText
    private lateinit var payBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }

    private fun initview() {
        amountEdt = findViewById(R.id.idEdtAmount)
        payBtn = findViewById(R.id.idBtnPay)
       payBtn.setOnClickListener(View.OnClickListener {
            // on below line we are getting
            // amount that is entered by user.
            val samount =amountEdt.getText().toString()

            // rounding off the amount.
            val amount = Math.round(samount.toFloat() * 100)

            // initialize Razorpay account.
            val checkout = Checkout()

            // set your id as below
            checkout.setKeyID("rzp_test_oDxcGkgbJUxaHR")

            // set image
            checkout.setImage(R.drawable.singapore)

            // initialize json object
            val `object` = JSONObject()
            try {
                // to put name
                `object`.put("name", "Payment here")

                // put description
                `object`.put("description", "Test payment")

                // to set theme color
                `object`.put("theme.color", "")

                // put the currency
                `object`.put("currency", "INR")

                // put amount
                `object`.put("amount", amount)

                // put mobile number
                `object`.put("prefill.contact", "9284064503")

                // put email
                `object`.put("prefill.email", "chaitanyamunje@gmail.com")

                // open razorpay to checkout activity
                checkout.open(this, `object`)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        })
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment is successful : $p0", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed due to error : $p1", Toast.LENGTH_SHORT).show()
    }

}