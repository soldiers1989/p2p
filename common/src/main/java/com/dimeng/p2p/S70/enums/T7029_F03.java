package com.dimeng.p2p.S70.enums;

import com.dimeng.util.StringHelper;

/**
 * 类型
 */
public enum T7029_F03 {

	/**
	 * 实地认证
	 */
	SDRZ("实地认证"),

	/**
	 * 机构担保
	 */
	DYDB("机构担保"),

	/**
	 * 实地认证+机构担保
	 */
	SDRZJDYDB("实地认证+机构担保");

	protected final String chineseName;

	private T7029_F03(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * 获取中文名称.
	 * 
	 * @return {@link String}
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * 解析字符串.
	 * 
	 * @return {@link T7029_F03}
	 */
	public static final T7029_F03 parse(String value) {
		if (StringHelper.isEmpty(value)) {
			return null;
		}
		try {
			return T7029_F03.valueOf(value);
		} catch (Throwable t) {
			return null;
		}
	}
}
