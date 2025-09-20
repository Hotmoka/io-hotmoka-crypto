/*
Copyright 2025 Fausto Spoto

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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.hotmoka.crypto.BIP39Mnemonics;
import io.hotmoka.testing.AbstractLoggedTests;

public class BIP39Mnemonic extends AbstractLoggedTests {

    @Test
    @DisplayName("BIP39 of bytes works")
    void BIP39OfBytesWorks() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    	var data = new byte[16];
    	new Random().nextBytes(data);
    	assertArrayEquals(data, BIP39Mnemonics.of(data).getBytes());
    }

    @Test
    @DisplayName("BIP39 of words works")
    void BIP39OfWordsWorks() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    	var data = new byte[16];
    	new Random().nextBytes(data);
    	var words = BIP39Mnemonics.of(data).stream().toArray(String[]::new);
    	assertArrayEquals(data, BIP39Mnemonics.of(words).getBytes());
    }
}