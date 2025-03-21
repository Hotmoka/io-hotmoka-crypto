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

import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.EncoderException;

/**
 * Simple class for translation of byte arrays into and from Base64 strings.
 */
public final class Base64 {

	private Base64() {}

	/**
	 * Yields a string Base64 representation of the given bytes.
	 * 
	 * @param data the bytes (most significant byte first)
	 * @return the Base64 string representation
	 */
	public static String toBase64String(byte[] data) {
		return org.bouncycastle.util.encoders.Base64.toBase64String(data);
	}

	/**
	 * Yields a string Base64 representation of a portion of the given bytes.
	 * 
	 * @param data the bytes (most significant byte first)
	 * @param offset the starting point in {@code bytes}
	 * @param length the number of bytes to consider
	 * @return the Base64 string representation
	 * @throws Base64ConversionException if the conversion fails
	 */
	public static String toBase64String(byte[] data, int offset, int length) throws Base64ConversionException {
		try {
			return org.bouncycastle.util.encoders.Base64.toBase64String(data, offset, length);
		}
		catch (EncoderException e) {
			throw new Base64ConversionException(e);
		}
	}

	/**
	 * Yields a byte representation of the given Base64 string.
	 * 
	 * @param base64 the Base64 string
	 * @return the byte representation (most significant byte first)
	 * @throws Base64ConversionException if the conversion fails
	 */
	public static byte[] fromBase64String(String base64) throws Base64ConversionException {
		return fromBase64String(base64, Base64ConversionException::new);
	}

	/**
	 * Yields a byte representation of the given Base64 string.
	 * 
	 * @param <E> the type of the exception thrown if the conversion fails
	 * @param base64 the Base64 string
	 * @param onConversionFailed the generator of the exception thrown if the conversion fails
	 * @return the byte representation (most significant byte first) of {@code base64}
	 * @throws E if the conversion fails
	 */
	public static <E extends Exception> byte[] fromBase64String(String base64, Function<String, ? extends E> onConversionFailed) throws E {
		try {
			return org.bouncycastle.util.encoders.Base64.decode(base64);
		}
		catch (DecoderException e) {
			throw onConversionFailed.apply(e.getMessage());
		}
	}

	/**
	 * Checks that the given string is actually in Base64 format.
	 * 
	 * @param <E> the type of the exception thrown if {@code s} is not in Base64 format
	 * @param s the string to check
	 * @param onIllegalFormat the generator of the exception thrown if {@code s} is not in Base64 format
	 * @return the string {@code s} itself
	 * @throws E if {@code s} is not in Base64 format
	 */
	public static <E extends Exception> String requireBase64(String s, Function<String, ? extends E> onIllegalFormat) throws E {
		fromBase64String(s, onIllegalFormat);
		return s;
	}
}