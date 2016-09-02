package com.yuncai.modules.timer.termTask;

public class CommonHandle extends AbstractHandle{

	/**
	 * 初始化彩期事件的处理
	 */
	@Override
	void initWorkFlow() {
		hmStopEvent.setNextEvent(termStopEvent);
		termStopEvent.setNextEvent(terminalStopEvent);
		terminalStopEvent.setNextEvent(hmStopEvent);
	}
	

}
