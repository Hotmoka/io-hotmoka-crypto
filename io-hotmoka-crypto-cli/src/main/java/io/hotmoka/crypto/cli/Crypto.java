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

package io.hotmoka.crypto.cli;

import java.io.IOException;

import io.hotmoka.cli.AbstractCLI;
import io.hotmoka.cli.AbstractPropertyFileVersionProvider;
import io.hotmoka.crypto.cli.Crypto.POMVersionProvider;
import io.hotmoka.crypto.cli.internal.Keys;
import picocli.CommandLine.Command;

/**
 * A command-line interface for cryptographic operations.
 */
@Command(
	name = "crypto",
	header = "This is the command-line tool for cryptographic operations.",
	footer = "Copyright (c) 2025 Fausto Spoto (fausto.spoto@hotmoka.io)",
	versionProvider = POMVersionProvider.class,
	subcommands = {
		Keys.class,
	}
)
public class Crypto extends AbstractCLI {

	private Crypto() {}

	/**
	 * Entry point from the shell.
	 * 
	 * @param args the command-line arguments provided to this tool
	 */
	public static void main(String[] args) {
		main(Crypto::new, args);
	}

	static {
		loadLoggingConfig(() -> Crypto.class.getModule().getResourceAsStream("logging.properties"));
	}

	/**
	 * A provider of the version of this tool, taken from the property
	 * declaration into the POM file.
	 */
	public static class POMVersionProvider extends AbstractPropertyFileVersionProvider {

		/**
		 * Creates the provider.
		 */
		public POMVersionProvider() {}

		@Override
		public String[] getVersion() throws IOException {
			return getVersion(() -> Crypto.class.getModule().getResourceAsStream("maven.properties"), "io.hotmoka.crypto.version");
		}
	}
}