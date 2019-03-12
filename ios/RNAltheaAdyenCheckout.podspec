
Pod::Spec.new do |s|
  s.name         = "RNAltheaAdyenCheckout"
  s.version      = "1.0.0"
  s.summary      = "RNAltheaAdyenCheckout"
  s.description  = <<-DESC
                  RNAltheaAdyenCheckout
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNAltheaAdyenCheckout.git", :tag => "master" }
  s.source_files  = "RNAltheaAdyenCheckout/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "Adyen"
  #s.dependency "others"

end

  
