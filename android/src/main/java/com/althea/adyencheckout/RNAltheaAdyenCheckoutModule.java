
package com.althea.adyencheckout;

import android.support.annotation.NonNull;

import com.adyen.checkout.core.CheckoutException;
import com.adyen.checkout.core.PaymentMethodHandler;
import com.adyen.checkout.core.StartPaymentParameters;
import com.adyen.checkout.core.handler.StartPaymentParametersHandler;
import com.adyen.checkout.ui.CheckoutController;
import com.adyen.checkout.ui.CheckoutSetupParameters;
import com.adyen.checkout.ui.CheckoutSetupParametersHandler;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNAltheaAdyenCheckoutModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

	private static final int REQUEST_CODE_CHECKOUT = 1;

	private final ReactApplicationContext reactContext;

	private String paymentToken;

	public RNAltheaAdyenCheckoutModule(ReactApplicationContext reactContext) {

		super(reactContext);
		this.reactContext = reactContext;
		this.paymentToken = "";
	}

	@ReactMethod
	public String generatePaymentToken() {

		CheckoutController.startPayment(getCurrentActivity(), new CheckoutSetupParametersHandler() {

			@Override
			public void onRequestPaymentSession(@NonNull CheckoutSetupParameters checkoutSetupParameters) {

				String token     = checkoutSetupParameters.getSdkToken();
				String returnUrl = checkoutSetupParameters.getReturnUrl();

				RNAltheaAdyenCheckoutModule.this.paymentToken = token + "_" + returnUrl;
			}

			@Override
			public void onError(@NonNull CheckoutException error) {

			}
		});

		return this.paymentToken;
	}

	@ReactMethod
	public void initCheckout(String session) {

		CheckoutController.handlePaymentSessionResponse(getCurrentActivity(), session, new StartPaymentParametersHandler() {

			@Override
			public void onPaymentInitialized(@NonNull StartPaymentParameters startPaymentParameters) {

				PaymentMethodHandler checkoutHandler = CheckoutController.getCheckoutHandler(startPaymentParameters);

				checkoutHandler.handlePaymentMethodDetails(getCurrentActivity(), REQUEST_CODE_CHECKOUT);
			}

			@Override
			public void onError(@NonNull CheckoutException error) {

			}
		});
	}

	@Override
	public String getName() {

		return "RNAltheaAdyenCheckout";
	}

	@Override
	public void onHostResume() {

		this.paymentToken = "";
	}

	@Override
	public void onHostPause() {

	}

	@Override
	public void onHostDestroy() {

	}

}