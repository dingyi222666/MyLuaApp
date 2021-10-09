/**
 *  Copyright (c) 2015-2017 Angelo ZERR.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.tm4e.core;

import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.grammar.ITokenizeLineResult;
import org.eclipse.tm4e.core.grammar.ITokenizeLineResult2;
import org.eclipse.tm4e.core.internal.theme.reader.ThemeReader;
import org.eclipse.tm4e.core.registry.Registry;
import org.eclipse.tm4e.core.theme.IRawTheme;
import org.eclipse.tm4e.core.theme.ParsedThemeRule;
import org.eclipse.tm4e.core.theme.Theme;

import java.util.List;
import java.util.Map;

public class TestGrammar {

	public static void main(String[] args) throws Exception {

		Registry registry = new Registry();
		IGrammar grammar = registry.loadGrammarFromPathSync("JavaScript.tmLanguage.json",
				TestGrammar.class.getResourceAsStream("JavaScript.tmLanguage.json"));


		IRawTheme rawTheme = ThemeReader.readThemeSync("themes/vscode_light.tmTheme",
				TestGrammar.class.getResourceAsStream("themes/vscode_light.tmTheme"));

		List<ParsedThemeRule> parsedThemeRule = Theme.parseTheme(rawTheme);

		Theme theme = Theme.resolveParsedThemeRules(parsedThemeRule);


		ITokenizeLineResult result = grammar.tokenizeLine("let a = 15;");

		for (int i = 0; i < result.getTokens().length; i++) {


			System.err.println(result.getTokens()[i]);
		}
	}
}
