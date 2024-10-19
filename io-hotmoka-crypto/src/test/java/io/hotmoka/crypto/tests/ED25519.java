/*
Copyright 2021 Fausto Spoto

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package io.hotmoka.crypto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.hotmoka.crypto.SignatureAlgorithms;
import io.hotmoka.crypto.api.Signer;
import io.hotmoka.crypto.api.Verifier;
import io.hotmoka.testing.AbstractLoggedTests;

public class ED25519 extends AbstractLoggedTests {
    private final String data = "HELLO ED25519";

    @Test
    @DisplayName("sign data with the ed25519 signature")
    void sign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    	var ed25519 = SignatureAlgorithms.ed25519();
    	String data = this.data;
        KeyPair keyPair = ed25519.getKeyPair();
        Signer<String> signer = ed25519.getSigner(keyPair.getPrivate(), String::getBytes);
        Verifier<String> verifier = ed25519.getVerifier(keyPair.getPublic(), String::getBytes);
        byte[] signed = signer.sign(data);

        assertEquals(ed25519.length().getAsInt(), signed.length);
        assertTrue(verifier.verify(data, signed), "data is not verified correctly");
        assertFalse(verifier.verify(data + "corrupted", signed), "corrupted data is verified");
    }

    @Test
    @DisplayName("sign, verify and create the public key from the encoded public key")
    void testED25519KeyEncoding() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
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
}