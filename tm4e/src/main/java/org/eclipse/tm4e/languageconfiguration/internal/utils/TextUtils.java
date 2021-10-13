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
package org.eclipse.tm4e.languageconfiguration.internal.utils;

import java.util.Arrays;


public class TextUtils {



	public static String normalizeIndentation(String str, int tabSize, boolean insertSpaces) {
		int firstNonWhitespaceIndex = TextUtils.firstNonWhitespaceIndex(str);
		if (firstNonWhitespaceIndex == -1) {
			firstNonWhitespaceIndex = str.length();
		}
		return TextUtils.normalizeIndentationFromWhitespace(str.substring(0, firstNonWhitespaceIndex), tabSize,
				insertSpaces) + str.substring(firstNonWhitespaceIndex);
	}

	private static String normalizeIndentationFromWhitespace(String str, int tabSize, boolean insertSpaces) {
		int spacesCnt = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '\t') {
				spacesCnt += tabSize;
			} else {
				spacesCnt++;
			}
		}

		StringBuilder result = new StringBuilder();
		if (!insertSpaces) {
			long tabsCnt = Math.round(Math.floor(spacesCnt / tabSize));
			spacesCnt = spacesCnt % tabSize;
			for (int i = 0; i < tabsCnt; i++) {
				result.append('\t');
			}
		}

		for (int i = 0; i < spacesCnt; i++) {
			result.append(' ');
		}

		return result.toString();
	}

	/**
	 * Returns the start of the string at the offset in the text. If the string is
	 * not in the text at the offset, returns -1.</br>
	 * Ex: </br>
	 * text = "apple banana", offset=8, string="banana" returns=6
	 */
	public static int startIndexOfOffsetTouchingString(String text, int offset, String string) {
		int start = offset - string.length();
		start = Math.max(start, 0);
		int end = offset + string.length();
		end = Math.min(end, text.length());
		try {
			int indexInSubtext = text.substring(start, end).indexOf(string);
			return indexInSubtext == -1 ? -1 : start + indexInSubtext;
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
	}

	/**
	 * Returns first index of the string that is not whitespace. If string is empty
	 * or contains only whitespaces, returns -1
	 */
	public static int firstNonWhitespaceIndex(String str) {
		for (int i = 0, len = str.length(); i < len; i++) {
			char c = str.charAt(i);
			if (c != ' ' && c != '\t') {
				return i;
			}
		}
		return -1;
	}

	public static String getIndentationFromWhitespace(String whitespace, TabSpacesInfo tabSpaces) {
		String tab = "\t"; //$NON-NLS-1$
		String spaces = null;
		int indentOffset = 0;
		boolean startsWithTab = true;
		boolean startsWithSpaces = true;
		if (tabSpaces.isInsertSpaces()) {
			char[] chars = new char[tabSpaces.getTabSize()];
			Arrays.fill(chars, ' ');
			spaces = new String(chars);
		}
		while (startsWithTab || startsWithSpaces) {
			startsWithTab = whitespace.startsWith(tab, indentOffset);
			startsWithSpaces = tabSpaces.isInsertSpaces() && whitespace.startsWith(spaces, indentOffset);
			if (startsWithTab) {
				indentOffset += tab.length();
			}
			if (startsWithSpaces) {
				indentOffset += spaces.length();
			}
		}
		return whitespace.substring(0, indentOffset);
	}


}
