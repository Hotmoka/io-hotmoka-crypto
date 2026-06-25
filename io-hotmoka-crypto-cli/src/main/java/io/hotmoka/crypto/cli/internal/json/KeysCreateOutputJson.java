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

package io.hotmoka.crypto.cli.internal.json;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import io.hotmoka.crypto.cli.api.keys.KeysCreateOutput;
import io.hotmoka.crypto.cli.internal.keys.CreateImpl;
import io.hotmoka.websockets.beans.api.InconsistentJsonException;
import io.hotmoka.websockets.beans.api.JsonRepresentation;

/**
 * The JSON representation of the output of the {@code crypto keys create} command.
 */
public abstract class KeysCreateOutputJson implements JsonRepresentation<KeysCreateOutput> {
	private final String file;
	private final String signature;
	private final String publicKeyBase58;
	private final String publicKeyBase64;
	private final String tendermintAddress;
	private final String privateKeyBase58;
	private final String privateKeyBase64;
	private final String concatenatedBase64;

	protected KeysCreateOutputJson(KeysCreateOutput output) {
		this.file = output.getFile().toString();
		this.signature = output.getSignature().getName();
		this.publicKeyBase58 = output.getPublicKeyBase58();
		this.publicKeyBase64 = output.getPublicKeyBase64();
		this.tendermintAddress = output.getTendermintAddress();
		this.privateKeyBase58 = output.getPrivateKeyBase58().orElse(null);
		this.privateKeyBase64 = output.getPrivateKeyBase64().orElse(null);
		this.concatenatedBase64 = output.getConcatenatedBase64().orElse(null);
	}

	/**
	 * Yields the path where the file of the keys has been saved.
	 * 
	 * @return the path where the file of the keys has been saved
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Yields the name of the signature algorithm of the key pair.
	 * 
	 * @return the name of the signature algorithm of the key pair
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Yields the base58-encoded public key.
	 * 
	 * @return the base58-encoded public key
	 */
	public String getPublicKeyBase58() {
		return publicKeyBase58;
	}

	/**
	 * Yields the base64-encoded public key.
	 * 
	 * @return the base64-encoded public key
	 */	
	public String getPublicKeyBase64() {
		return publicKeyBase64;
	}

	/**
	 * Yields the public key represented as a Tendermint address.
	 * 
	 * @return the public key represented as a Tendermint address
	 */
	public String getTendermintAddress() {
		return tendermintAddress;
	}

	/**
	 * Yields the base58-encoded private key.
	 * 
	 * @return the base58-encoded private key, if this output reports it
	 */
	public Optional<String> getPrivateKeyBase58() {
		return Optional.ofNullable(privateKeyBase58);
	}

	/**
	 * Yields the base64-encoded private key.
	 * 
	 * @return the base64-encoded private key, if this output reports it
	 */
	public Optional<String> getPrivateKeyBase64() {
		return Optional.ofNullable(privateKeyBase64);
	}

	/**
	 * Yields the base64-encoded concatenated private and public key.
	 * 
	 * @return the base64-encoded concatenated private and public key, if this output reports it
	 */
	public Optional<String> getConcatenatedBase64() {
		return Optional.ofNullable(concatenatedBase64);
	}

	@Override
	public KeysCreateOutput unmap() throws InconsistentJsonException, NoSuchAlgorithmException {
		return new CreateImpl.Output(this);
	}
}