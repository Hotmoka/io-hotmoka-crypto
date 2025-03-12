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

package io.hotmoka.crypto;

import java.util.function.Function;

import io.hotmoka.crypto.internal.Base58Impl;

/**
 * Base58 is a way to encode Bitcoin addresses (or arbitrary data) as alphanumeric strings.
 * <p>
 * Note that this is not the same base58 as used by Flickr, which you may find referenced around the Internet.
 * <p>
 * Satoshi explains: why base-58 instead of standard base-64 encoding?
 * <ul>
 * <li>Don't want 0OIl characters that look the same in some fonts and
 *     could be used to create visually identical looking account numbers.</li>
 * <li>A string with non-alphanumeric characters is not as easily accepted as an account number.</li>
 * <li>E-mail usually won't line-break if there's no punctuation to break at.</li>
 * <li>Double clicking selects the whole number as one word if it's all alphanumeric.</li>
 * </ul>
 * <p>
 * However, note that the encoding/decoding runs in O(n&sup2;) time, so it is not useful for large data.
 * <p>
 * The basic idea of the encoding is to treat the data bytes as a large number represented using
 * base-256 digits, convert the number to be represented using base-58 digits, preserve the exact
 * number of leading zeros (which are otherwise lost during the mathematical operations on the
 * numbers), and finally represent the resulting base-58 digits as alphanumeric ASCII characters.
 */
public final class Base58 {

	private Base58() {}

	/**
     * Encodes the given bytes as a Base58 string (no checksum is appended).
     *
     * @param data the bytes to encode
     * @return the Base58-encoded string
     */
    public static String toBase58String(byte[] data) {
    	return Base58Impl.encode(data);
    }

    /**
	 * Yields a byte representation of the given Base58 string.
	 * 
	 * @param base58 the Base58-encoded string
	 * @return the byte representation (most significant byte first) of {@code base58}
	 * @throws Base58ConversionException if the conversion fails
	 */
    public static byte[] fromBase58String(String base58) throws Base58ConversionException {
    	return fromBase58String(base58, Base58ConversionException::new);
    }

    /**
	 * Yields a byte representation of the given Base58 string.
	 * 
	 * @param <E> the type of the exception thrown if the conversion fails
	 * @param base58 the Base58-encoded string
	 * @param onConversionFailed the generator of the exception thrown if the conversion fails
	 * @return the byte representation (most significant byte first) of {@code base58}
	 * @throws E if the conversion fails
	 */
	public static <E extends Exception> byte[] fromBase58String(String base58, Function<String, ? extends E> onConversionFailed) throws E {
		try {
			return Base58Impl.decode(base58);
		}
		catch (RuntimeException e) {
			throw onConversionFailed.apply(e.getMessage());
		}
	}

	/**
	 * Checks that the given string is actually in Base58 format.
	 * 
	 * @param <E> the type of the exception thrown if {@code s} is not in Base58 format
	 * @param s the string to check
	 * @param onIllegalFormat the generator of the exception thrown if {@code s} is not in Base58 format
	 * @return the string {@code s} itself
	 * @throws E if {@code s} is not in Base58 format
	 */
	public static <E extends Exception> String requireBase58(String s, Function<String, ? extends E> onIllegalFormat) throws E {
		fromBase58String(s, onIllegalFormat);
		return s;
	}
}