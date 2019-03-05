
# react-native-althea-adyen-checkout

## Getting started

`$ npm install react-native-althea-adyen-checkout --save`

### Mostly automatic installation

`$ react-native link react-native-althea-adyen-checkout`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-althea-adyen-checkout` and add `RNAltheaAdyenCheckout.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAltheaAdyenCheckout.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAltheaAdyenCheckoutPackage;` to the imports at the top of the file
  - Add `new RNAltheaAdyenCheckoutPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-althea-adyen-checkout'
  	project(':react-native-althea-adyen-checkout').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-althea-adyen-checkout/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-althea-adyen-checkout')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNAltheaAdyenCheckout.sln` in `node_modules/react-native-althea-adyen-checkout/windows/RNAltheaAdyenCheckout.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Althea.Adyen.Checkout.RNAltheaAdyenCheckout;` to the usings at the top of the file
  - Add `new RNAltheaAdyenCheckoutPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNAltheaAdyenCheckout from 'react-native-althea-adyen-checkout';

// TODO: What to do with the module?
RNAltheaAdyenCheckout;
```
  