package com.github.ascii.normalizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AsciiNormalizerTest {

	@ParameterizedTest
	@CsvSource({
		"'\0',		true",
		"-,			true",
		"+,			true",
		"_,			true",
		"0,			true",
		"\",		true",
		"' ',		true",
		"'\n',		true",
		"\u007f,	true",
		"\u0080,	false",
		"\u0141,	false",
		"\u0142,	false",
		"\u2012,	false",
		"\uffff,	false",
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ,			true",
		"ABCDEFGHIJK\u0141MNOPQRSTUVWXYZ,		false",
		"abcdefghijklmnopqrstuvwxyz,			true",
		"abcdefghijk\u0142mnopqrstuvwxyz,		false",
		"0123456789,							true",
		"!@#$%^&*()_+=[]{}; \'\\:\";\'|./<>?,	true",
		"\u021a\u0125\u00ef\u015d\u2000\u0129\u0161\u2000\u00e2\u2000f\u016f\u0148\u0137\u0177\u2000\u0160\u0165\u0155\u012d\u0144\u0121,	false"
	})
	public void testIsNormalized(String string, boolean expected) {
		assertEquals(expected, AsciiNormalizer.isNormalized(string));
	}

	@ParameterizedTest
	@CsvSource({
		"'\0',		'\0'",
		"-,			-",
		"_,			_",
		"\",		\"",
		"' ',		' '",
		"'\t',		'\t'",
		"'\n',		'\n'",
		"\u007f,	\u007f",
		"\u00a0,	' '",
		"\u00c6,	AE",
		"\u00d8,	O",
		"\u00df,	ss",
		"\u00e6,	ae",
		"\u00f8,	o",
		"\u0128,	I",
		"\u0133,	ij",
		"\u0141,	L",
		"\u0142,	l",
		"\u2012,	-",
		"\u201c,	\"",
		"\u201d,	\"",
		"\ua734,	AO",
		"\ua735,	ao",
		"\ufb00,	ff",
		"\ufb05,	st",
		"\uff3f,	_",
		"\uffff,	''",
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ,	ABCDEFGHIJKLMNOPQRSTUVWXYZ",
		"abcdefghijklmnopqrstuvwxyz,	abcdefghijklmnopqrstuvwxyz",
		"0123456789,	0123456789",
		"!@#$%^&*()_+=[]{};\'\\:\";\'|./<>?,	!@#$%^&*()_+=[]{};\'\\:\";\'|./<>?",
		"\u021a\u0125\u00ef\u015d\u2000\u0129\u0161\u2000\u00e2\u2000f\u016f\u0148\u0137\u0177\u2000\u0160\u0165\u0155\u012d\u0144\u0121,"
				+ "This is a funky String",
		"a\u0327\u00e7\u1e09\u1e11\u0229\u0137\u013c\u0146\u0157\u015f\u0163u\u0327A\u0327\u00c7\u1e08\u1e10\u0228\u0122\u0136\u013b\u0145\u0156\u015e\u0162U\u0327,"
				+ "accdeklnrstuACCDEGKLNRSTU",
		"\u00e0\u00e8\u00ec\u00f2\u00f9\u00c0\u00c8\u00cc\u00d2\u00d9,"
				+ "aeiouAEIOU",
		"\u00e1\u0105\u0301\u0107\u00e9\u01f5\u00ed\u013a\u1e3f\u0144\u00f3\u1e55\u0155\u015bt\u0301\u00fa\u1e83\u00fd\u017a\u00c1\u0104\u0301\u0106\u00c9\u01f4\u00cd\u0139\u1e3e\u0143\u00d3\u1e54\u0154\u015aT\u0301\u00da\u1e82\u00dd\u0179,"
				+ "aacegilmnoprstuwyzAACEGILMNOPRSTUWYZ",
		"\u00e2\u0109d\u0302\u00ea\u011d\u0125\u00ee\u00f4\u00fb\u0177\u00c2\u00ca\u00ce\u00d4\u00db,"
				+ "acdeghiouyAEIOU",
		"\u00e3\u0129\u00f5\u00f1\u00c3\u0128\u00d5\u00d1,"
				+ "aionAION",
		"\u00e4\u00eb\u00ef\u00f6\u00fc\u00ff\u00c4\u00cb\u00cf\u00d6\u00dc,"
				+ "aeiouyAEIOU",
		"\u0105\u0104\u0107\u0106\u0119\u0118\u0142\u0141\u0144\u0143\u00f3\u00d3\u015b\u015a\u017c\u017b\u017a\u0179,"
				+ "aAcCeElLnNoOsSzZzZ",
		"\ua732\ua733\u00c6\u00e6\ua734\ua735\ua736\ua737\ua738\ua739\ua73a\ua73b\ua73c\ua73d,"
				+ "AAaaAEaeAOaoAUauAVavAVavAYay",
		"\ufb00\ufb03\ufb04\ufb01\ufb02\u01f6\u0195\u2114\u1efa\u1efb,"
				+ "ffffifflfiflHvhvlbILil",
		"\u0152\u0153\ua74e\ua74f\u1e9e\u00df\ufb06\ufb05\ua728\ua729\u1d6b\ua760\ua761,"
				+ "OEoeOOoofsssststTZtzueVYvy",
		"\u203f\u2040\u2054\ufe33\ufe34\ufe4d\ufe4e\ufe4f\uff3f,"
				+ "_________",
		"\u058a\u1400\u2010\u2011\u2012\u2013\u2014\u2015\u2e17\u2e1a\u2e3a\u2e3b\u2e40\u301c\u3030\u30a0\ufe31\ufe32\ufe58\ufe63\uff0d,"
				+ "---------------------",
		"\u2018\u2019\u201b\u201c\u201d\u201f\u2039\u203a\u2e02\u2e03\u2e04\u2e05\u2e09\u2e0a\u2e0c\u2e0d\u2e1c\u2e1d\u2e20\u2e21,"
				+ "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"",
		"\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000,"
				+ "'               '"
	})
	public void testNormalize(String unicodeString, String expected) {
		assertEquals(expected, AsciiNormalizer.normalize(unicodeString));
	}

}