package com.yuncai.modules.timer.termTask.event.impl;

import java.util.Map;

import com.yuncai.modules.lottery.model.Sql.Isuses;

public class CommonTerminalStopEventImpl_fb extends AbstractTerminalStopEvent{
	
	public Map execute(Map request) {
		super.execute(request);
		Isuses nextTerm = (Isuses) request.get(NEXT_TERM);
		
		//重新设置事件运行环境
		request.put(NOW_TERM, nextTerm);
		request.put(NEXT_TERM, null);
		return request;	
		
	}

}
