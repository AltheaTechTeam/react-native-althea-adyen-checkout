using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Althea.Adyen.Checkout.RNAltheaAdyenCheckout
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNAltheaAdyenCheckoutModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNAltheaAdyenCheckoutModule"/>.
        /// </summary>
        internal RNAltheaAdyenCheckoutModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNAltheaAdyenCheckout";
            }
        }
    }
}
