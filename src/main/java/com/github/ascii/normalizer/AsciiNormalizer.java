package com.github.ascii.normalizer;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Class normalizing strings by substituting Unicode diacritics, ligatures, punctuations
 * and spaces with ASCII characters.
 * 
 * @author Piotr Melkowski
 */
public class AsciiNormalizer {

    // Unicode codepoint -> ASCII substitution
	static final int[][] CP_MAPPING = new int[Character.MAX_VALUE + 1][];

	static {
		IntStream.range(0, Byte.MAX_VALUE + 1)
			.forEach(ascii -> CP_MAPPING[ascii] = new int[] {ascii});

		Arrays.fill(CP_MAPPING, Byte.MAX_VALUE + 1, Character.MAX_VALUE + 1, new int[0]);
		CP_MAPPING['\u00c6'] = new int[] {'A', 'E'};
		CP_MAPPING['\u00d8'] = CP_MAPPING['O'];
		CP_MAPPING['\u00df'] = new int[] {'s', 's'};
		CP_MAPPING['\u00e6'] = new int[] {'a', 'e'};
		CP_MAPPING['\u00f8'] = CP_MAPPING['o'];
		CP_MAPPING['\u0141'] = CP_MAPPING['L'];
		CP_MAPPING['\u0142'] = CP_MAPPING['l'];
		CP_MAPPING['\u0152'] = new int[] {'O', 'E'};
		CP_MAPPING['\u0153'] = new int[] {'o', 'e'};
		CP_MAPPING['\u0195'] = new int[] {'h', 'v'};
		CP_MAPPING['\u01f6'] = new int[] {'H', 'v'};
		CP_MAPPING['\u1d6b'] = new int[] {'u', 'e'};
		CP_MAPPING['\u1e9e'] = new int[] {'f', 's'};
		CP_MAPPING['\u1efa'] = new int[] {'I', 'L'};
		CP_MAPPING['\u1efb'] = new int[] {'i', 'l'};
		CP_MAPPING['\u2114'] = new int[] {'l', 'b'};
		CP_MAPPING['\ua728'] = new int[] {'T', 'Z'};
		CP_MAPPING['\ua729'] = new int[] {'t', 'z'};
		CP_MAPPING['\ua732'] = new int[] {'A', 'A'};
		CP_MAPPING['\ua733'] = new int[] {'a', 'a'};
		CP_MAPPING['\ua734'] = new int[] {'A', 'O'};
		CP_MAPPING['\ua735'] = new int[] {'a', 'o'};
		CP_MAPPING['\ua736'] = new int[] {'A', 'U'};
		CP_MAPPING['\ua737'] = new int[] {'a', 'u'};
		CP_MAPPING['\ua738'] = CP_MAPPING['\ua73a'] = new int[] {'A', 'V'};
		CP_MAPPING['\ua739'] = CP_MAPPING['\ua73b'] = new int[] {'a', 'v'};
		CP_MAPPING['\ua73c'] = new int[] {'A', 'Y'};
		CP_MAPPING['\ua73d'] = new int[] {'a', 'y'};
		CP_MAPPING['\ua74e'] = new int[] {'O', 'O'};
		CP_MAPPING['\ua74f'] = new int[] {'o', 'o'};
		CP_MAPPING['\ua760'] = new int[] {'V', 'Y'};
		CP_MAPPING['\ua761'] = new int[] {'v', 'y'};

		IntStream.range(Byte.MAX_VALUE + 1, Character.MAX_VALUE + 1).forEach(codePoint -> {
			switch (Character.getType(codePoint)) {
			case Character.CONNECTOR_PUNCTUATION:
				CP_MAPPING[codePoint] = CP_MAPPING['_'];
				break;
			case Character.DASH_PUNCTUATION:
				CP_MAPPING[codePoint] = CP_MAPPING['-'];
				break;
			case Character.FINAL_QUOTE_PUNCTUATION:
			case Character.INITIAL_QUOTE_PUNCTUATION:
				CP_MAPPING[codePoint] = CP_MAPPING['"'];
				break;
			case Character.SPACE_SEPARATOR:
				CP_MAPPING[codePoint] = CP_MAPPING[' '];
				break;
			}
		});
	}

	/**
	 * Checks if a string contains only ASCII characters.
	 * 
	 * @param src the string to check
	 * @return true when the string contains only ASCII characters, false otherwise
	 */
	public static boolean isNormalized(CharSequence src) {
		return src.codePoints()
			.allMatch(codePoint -> codePoint >>> 7 == 0);
	}

	/**
	 * Normalizes a string by substituting Unicode diacritics, ligatures, punctuations
	 * and spaces with ASCII characters.
	 * 
	 * @param src the sequence of char values to normalize.
	 * @return the ASCII normalized string
	 */
	public static String normalize(CharSequence src) {
		return Normalizer.normalize(src, Normalizer.Form.NFKD).codePoints()
			.flatMap(codePoint -> IntStream.of(CP_MAPPING[codePoint]))
			.collect(StringBuilder::new, (sb, codePoint) -> sb.append((char) codePoint), StringBuilder::append)
			.toString();
	}

}