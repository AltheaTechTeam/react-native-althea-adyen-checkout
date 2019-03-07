
package com.althea.adyencheckout;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.adyen.checkout.core.CheckoutException;
import com.adyen.checkout.core.PaymentMethodHandler;
import com.adyen.checkout.core.PaymentResult;
import com.adyen.checkout.core.StartPaymentParameters;
import com.adyen.checkout.core.handler.StartPaymentParametersHandler;
import com.adyen.checkout.ui.CheckoutController;
import com.adyen.checkout.ui.CheckoutSetupParameters;
import com.adyen.checkout.ui.CheckoutSetupParametersHandler;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;
import org.json.JSONObject;

public class RNAltheaAdyenCheckoutModule extends ReactContextBaseJavaModule implements ActivityEventListener {

	private static final int    REQUEST_CODE_CHECKOUT = 1;
	private static final String E_PAYMENT_FAILED      = "E_PAYMENT_FAILED";
	private static final String E_PAYMENT_CANCELED    = "E_PAYMENT_CANCELED";

	private final ReactApplicationContext reactContext;

	private JSONObject paymentToken;
	private String     errorMessage;
	private Promise    promise;

	public RNAltheaAdyenCheckoutModule(ReactApplicationContext reactContext) {

		super(reactContext);
		this.reactContext = reactContext;
		this.paymentToken = new JSONObject();
		this.errorMessage = "";
	}

	@ReactMethod
	public void generatePaymentToken(Callback successCallback, Callback errorCallback) {

		CheckoutController.startPayment(getCurrentActivity(), new CheckoutSetupParametersHandler() {

			@Override
			public void onRequestPaymentSession(@NonNull CheckoutSetupParameters checkoutSetupParameters) {

				String token     = checkoutSetupParameters.getSdkToken();
				String returnUrl = checkoutSetupParameters.getReturnUrl();

				try {

					RNAltheaAdyenCheckoutModule.this.paymentToken.put("sdkToken", token);
					RNAltheaAdyenCheckoutModule.this.paymentToken.put("returnUrl", returnUrl);
				} catch (JSONException exception) {

					RNAltheaAdyenCheckoutModule.this.errorMessage = exception.getMessage();
				}
			}

			@Override
			public void onError(@NonNull CheckoutException error) {

				RNAltheaAdyenCheckoutModule.this.errorMessage = error.getMessage();
			}
		});

		if (!this.errorMessage.isEmpty()) {

			errorCallback.invoke(this.errorMessage);
		}

		successCallback.invoke(this.paymentToken.toString());
	}

	@ReactMethod
	public void initCheckout(final String session, final Promise promise) {

		Handler handler = new Handler(reactContext.getMainLooper());

		handler.post(new Runnable() {

			@Override
			public void run() {

				CheckoutController.handlePaymentSessionResponse(getCurrentActivity(), session, new StartPaymentParametersHandler() {

					@Override
					public void onPaymentInitialized(@NonNull StartPaymentParameters startPaymentParameters) {

						RNAltheaAdyenCheckoutModule.this.promise = promise;

						PaymentMethodHandler checkoutHandler = CheckoutController.getCheckoutHandler(startPaymentParameters);

						checkoutHandler.handlePaymentMethodDetails(getCurrentActivity(), REQUEST_CODE_CHECKOUT);
					}

					@Override
					public void onError(@NonNull CheckoutException error) {

						RNAltheaAdyenCheckoutModule.this.promise.reject(E_PAYMENT_FAILED, error.getMessage());
					}
				});
			}
		});
	}

	@Override
	public String getName() {

		return "RNAltheaAdyenCheckout";
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE_CHECKOUT) {

			if (resultCode == PaymentMethodHandler.RESULT_CODE_OK) {

				PaymentResult paymentResult = PaymentMethodHandler.Util.getPaymentResult(data);

				this.promise.resolve(paymentResult.getPayload());

			} else {

				CheckoutException checkoutException = PaymentMethodHandler.Util.getCheckoutException(data);

				if (resultCode == PaymentMethodHandler.RESULT_CODE_CANCELED) {

					this.promise.reject(E_PAYMENT_CANCELED, checkoutException.getMessage());
				} else {

					this.promise.reject(E_PAYMENT_FAILED, checkoutException.getMessage());
				}
			}
		}
	}
}