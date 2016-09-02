package com.yuncai.modules.comconfig;

/**针对短信 发送，验证 配置
 * @author Administrator
 *
 */
public class SmsAuthConstraints {
	 public  static final int SEND_INTERVAL = 60;// 发送间隔(秒)
	 public  static final	int ACTIVE_TIME = 60;// 验证码有效时间(秒)
	 public  static final int RETRY_LIMIT = 5;//最大允许输错次数
}
