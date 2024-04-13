# /bin/bash

keytool \
  -genkey \
  -alias dev-keystore \
  -storetype PKCS12 \
  -keyalg RSA \
  -keysize 2048 \
  -keystore dev-keystore.p12 \
  -validity 365000

