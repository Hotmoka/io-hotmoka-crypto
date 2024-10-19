/*
Copyright 2024 Fausto Spoto

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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.hotmoka.crypto.SignatureAlgorithms;
import io.hotmoka.testing.AbstractLoggedTests;

public class SignaturesKeyLength extends AbstractLoggedTests {

    @Test
    @DisplayName("verifies the length of the keys of the ED25519 signature algorithm")
    void testED25519KeysLength() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
    	// create the signature algorithm
    	var ed25519 = SignatureAlgorithms.ed25519();
    	// create a key pair (public, private) with that signature algorithm
    	var keyPair = ed25519.getKeyPair();

    	// checks that the keys have the expected length
    	assertEquals(ed25519.publicKeyLength().getAsInt(), ed25519.encodingOf(keyPair.getPublic()).length);
    	assertEquals(ed25519.privateKeyLength().getAsInt(), ed25519.encodingOf(keyPair.getPrivate()).length);
    }

    @Test
    @DisplayName("verifies the length of the keys of the QTESLA1 signature algorithm")
    void testQTESLA1KeysLength() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
    	// create the signature algorithm
    	var qtesla1 = SignatureAlgorithms.qtesla1();
    	// create a key pair (public, private) with that signature algorithm
    	var keyPair = qtesla1.getKeyPair();

    	// checks that the keys have the expected length
    	assertEquals(qtesla1.publicKeyLength().getAsInt(), qtesla1.encodingOf(keyPair.getPublic()).length);
    	assertEquals(qtesla1.privateKeyLength().getAsInt(), qtesla1.encodingOf(keyPair.getPrivate()).length);
    }

    @Test
    @DisplayName("verifies the length of the keys of the QTESLA3 signature algorithm")
    void testQTESLA3KeysLength() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
    	// create the signature algorithm
    	var qtesla3 = SignatureAlgorithms.qtesla3();
    	// create a key pair (public, private) with that signature algorithm
    	var keyPair = qtesla3.getKeyPair();

    	// checks that the keys have the expected length
    	assertEquals(qtesla3.publicKeyLength().getAsInt(), qtesla3.encodingOf(keyPair.getPublic()).length);
    	assertEquals(qtesla3.privateKeyLength().getAsInt(), qtesla3.encodingOf(keyPair.getPrivate()).length);
    }
}