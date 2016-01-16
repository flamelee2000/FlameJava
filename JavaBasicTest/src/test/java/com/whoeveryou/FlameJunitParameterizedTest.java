package com.whoeveryou;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FlameJunitParameterizedTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 0, 0 }, { 1, 1, 2 },
				{ 2, 1, 3 }, { 3, 2, 5 }, { 4, 3, 7 }, { 5, 5, 10 },
				{ 6, 8, 14 } });
	}

	private int fInput1;
	private int fInput2;
	private int fExpected;

	public FlameJunitParameterizedTest(int input1, int input2, int expected) {
		fInput1 = input1;
		fInput2 = input2;
		fExpected = expected;
	}

	@Test
	public void test() {
		assertEquals(fExpected, fInput1 + fInput2);
	}

}
