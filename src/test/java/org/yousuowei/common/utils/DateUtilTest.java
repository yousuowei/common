package org.yousuowei.common.utils;

import org.junit.Test;
import org.yousuowei.common.utils.DateUtil;
import org.yousuowei.common.utils.DateUtil.DateFormatStyle;

public class DateUtilTest {

	@Test
	public void testLongToDate() {
		Long time = System.currentTimeMillis();
		String a = DateUtil.longToDate(time, DateFormatStyle.DATE);
		String b = DateUtil.longToDate(time, DateFormatStyle.TIME_DETAIL);
		System.out.println(a);
		System.out.println(b);
	}
}
