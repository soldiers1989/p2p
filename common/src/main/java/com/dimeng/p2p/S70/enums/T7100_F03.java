package com.dimeng.p2p.S70.enums;

import com.dimeng.util.StringHelper;

/**
 * 认证状态
 */
public enum T7100_F03 {

	/**
	 * 失败
	 */
	SB("失败"),

	/**
	 * 通过
	 */
	TG("通过");

	protected final String chineseName;

	private T7100_F03(String chineseName) {
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
	 * @return {@link T7100_F03}
	 */
	public static final T7100_F03 parse(String value) {
		if (StringHelper.isEmpty(value)) {
			return null;
		}
		try {
			return T7100_F03.valueOf(value);
		} catch (Throwable t) {
			return null;
		}
	}
}
