[![Java-Build Action Status](https://github.com/Hotmoka/io-hotmoka-crypto/actions/workflows/java_build.yml/badge.svg)](https://github.com/Hotmoka/io-hotmoka-crypto/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.hotmoka.crypto/io-hotmoka-crypto-api.svg?label=Maven%20Central)](https://central.sonatype.com/search?smo=true&q=g:io.hotmoka.crypto)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# A general interface for crypto algorithms in Java

This project defines a general interface for crypto algorithms and implements some of
such algorithms by using the underlying [Bouncy Castle library](https://www.bouncycastle.org/).
The interface is simpler and more object-oriented than other proposals.
This project provides code for hashing algorithms, signature algorithms, BIP39 words encoding,
Base58 and Base64 encodings.

## Hashing

The following test case shows how data can be repeated hashed into an array of bytes.

```java
@Test
@DisplayName("10,000,000 iterated shabal256")
void iteratedSHABAL256() throws Exception {
  long start = System.currentTimeMillis();
  // create the data that will be hashed (an array of bytes)
  byte[] data = "HELLO HASHING".getBytes();
  // create the hasher: array of bytes are mapped into themselves and then hashed into
  // another array of bytes
  Hasher<byte[]> hasher = HashingAlgorithms.shabal256().getHasher(Function.identity());
  // repeat hashing many times
  for (int i = 0; i < 10_000_000; i++)
    data = hasher.hash(data);

  long elapsed = System.currentTimeMillis() - start;
  System.out.println("shabal256 took " + elapsed + "ms");

  // check the expected final hash
  assertArrayEquals(new byte[] { -110, -11, 92, -50, 122, 2, 38, -72, 74, 124, 82, -29, -9, 122, 63, 3, -27, -127, 35, -30, 8, -11, -39, 87, -90, -97, -118, 14, 29, 45, 62, 91 }, data);
}
```

After the object gets closed, any call to `getSomething()` will throw a `MyException`.
The call to `stopNewCalls()` allows outstanding calls to `getSomething` to terminate
before closing all resources. Moreover, `stopNewCalls()` return true only at its first call,
therefore the resources are closed only once.

## Signing

The following test case shows how to sign and verify data and how to translate keys into
their byte representation and back.

```java
@Test
@DisplayName("sign, verify and create the public key from the encoded public key")
void testEncodedPublicKey() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
  // create a signature algorithm
  var ed25519 = SignatureAlgorithms.ed25519();
  // create a key pair (public, private) with that signature algorithm
  KeyPair keyPair = ed25519.getKeyPair();
  // create a signer and verifier of strings: strings are first transformed into their
  // sequence of bytes and then signed with the private key and verified with the public key
  Signer<String> signer = ed25519.getSigner(keyPair.getPrivate(), String::getBytes);
  Verifier<String> verifier = ed25519.getVerifier(keyPair.getPublic(), String::getBytes);
  // sign the data
  byte[] signed = signer.sign(data);

  // check that the signed data is correctly verified and that corrupted data is not verified
  assertTrue(verifier.verify(data, signed), "data is not verified correctly");
  assertFalse(verifier.verify(data + "corrupted", signed), "corrupted data is verified");

  // transform the public key into an array of bytes and back into a public key: they should be equal
  PublicKey publicKey = ed25519.publicKeyFromEncoding(ed25519.encodingOf(keyPair.getPublic()));
  // create a verifier with the new public key
  verifier = ed25519.getVerifier(publicKey, String::getBytes);
  // if they are equal, the new public key should behave as the original public key
  assertTrue(verifier.verify(data, signed), "data is not verified correctly with the encoded key");
  assertFalse(verifier.verify(data + "corrupted", signed), "corrupted data is verified with the encoded key");

  // explicitly check that the two public keys are equal
  assertTrue(keyPair.getPublic().equals(publicKey), "the public keys do not match");
}
```

<p align="center"><img width="100" src="https://mirrors.creativecommons.org/presskit/buttons/88x31/png/by.png" alt="This documentation is licensed under a Creative Commons Attribution 4.0 International License"></p><p align="center">This document is licensed under a Creative Commons Attribution 4.0 International License.</p>

<p align="center">Copyright 2024 by Fausto Spoto (fausto.spoto@hotmoka.io)</p>