//
//  RNAltheaAdyenCheckout.swift
//  RNAltheaAdyenCheckout
//
//  Created by Alvin on 14/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import Adyen

@objc(RNAltheaAdyenCheckout)
class RNAltheaAdyenCheckout: NSObject {
    
    @objc
    func getPaymentToken(
        _ resolve: RCTPromiseResolveBlock,
        rejecter reject: RCTPromiseRejectBlock
        ) -> Void {
        
        resolve("It works!");
    }
}
