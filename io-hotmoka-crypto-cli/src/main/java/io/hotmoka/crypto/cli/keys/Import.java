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

package io.hotmoka.crypto.cli.keys;

import picocli.CommandLine.Command;

/**
 * A command that imports a key pair from its BIP39 words.
 */
@Command(name = "import",
	header = "Import a key pair file from BIP39 words.",
	showDefaultValues = true)
public class Import extends io.hotmoka.crypto.cli.internal.keys.ImportImpl {

	/**
	 * Builds the command.
	 */
	public Import() {}
}