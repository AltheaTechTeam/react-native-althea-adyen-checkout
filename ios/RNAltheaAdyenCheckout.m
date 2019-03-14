
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif
  
@interface RCT_EXTERN_MODULE(RNAltheaAdyenCheckout, NSObject)

RCT_EXTERN_METHOD(getPaymentToken)

@end
