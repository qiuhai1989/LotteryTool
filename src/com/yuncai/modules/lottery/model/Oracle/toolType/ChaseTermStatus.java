package com.yuncai.modules.lottery.model.Oracle.toolType;

	public class ChaseTermStatus extends com.yuncai.core.hibernate.IntegerBeanLabelItem {

	protected ChaseTermStatus(String name, int value) {
		super(ChaseTermStatus.class.getName(), name, value);
	}

	public static final ChaseTermStatus NOT_CANCEL = new ChaseTermStatus("未撤销", 0);

	public static final ChaseTermStatus USER_CANCEL = new ChaseTermStatus("用户撤销", 1);

	public static final ChaseTermStatus SYS_CANCEL = new ChaseTermStatus("系统撤消", 2);


}
