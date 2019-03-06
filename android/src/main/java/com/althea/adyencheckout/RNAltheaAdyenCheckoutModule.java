
package com.althea.adyencheckout;

import android.support.annotation.NonNull;

import com.adyen.checkout.core.CheckoutException;
import com.adyen.checkout.core.PaymentMethodHandler;
import com.adyen.checkout.core.StartPaymentParameters;
import com.adyen.checkout.core.handler.StartPaymentParametersHandler;
import com.adyen.checkout.ui.CheckoutController;
import com.adyen.checkout.ui.CheckoutSetupParameters;
import com.adyen.checkout.ui.CheckoutSetupParametersHandler;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;
import org.json.JSONObject;

public class RNAltheaAdyenCheckoutModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

	private static final int REQUEST_CODE_CHECKOUT = 1;

	private final ReactApplicationContext reactContext;

	private JSONObject paymentToken;
	private String     errorMessage;

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

		successCallback.invoke(this.paymentToken);
	}

	@ReactMethod
	public void initCheckout(String session, Callback errorCallback) {

		CheckoutController.handlePaymentSessionResponse(getCurrentActivity(), session, new StartPaymentParametersHandler() {

			@Override
			public void onPaymentInitialized(@NonNull StartPaymentParameters startPaymentParameters) {

				PaymentMethodHandler checkoutHandler = CheckoutController.getCheckoutHandler(startPaymentParameters);

				checkoutHandler.handlePaymentMethodDetails(getCurrentActivity(), REQUEST_CODE_CHECKOUT);
			}

			@Override
			public void onError(@NonNull CheckoutException error) {

				RNAltheaAdyenCheckoutModule.this.errorMessage = error.getMessage();
			}
		});

		if (!this.errorMessage.isEmpty()) {

			errorCallback.invoke(this.errorMessage);
		}
	}

	@Override
	public String getName() {

		return "RNAltheaAdyenCheckout";
	}

	@Override
	public void onHostResume() {

		this.paymentToken = new JSONObject();
		this.errorMessage = "";
	}

	@Override
	public void onHostPause() {

	}

	@Override
	public void onHostDestroy() {

	}

}