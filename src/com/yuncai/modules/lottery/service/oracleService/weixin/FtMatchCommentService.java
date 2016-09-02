package com.yuncai.modules.lottery.service.oracleService.weixin;

import java.util.List;
import com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch;
import com.yuncai.modules.lottery.model.Oracle.weixin.FtMatchComment;
import com.yuncai.modules.lottery.model.Oracle.weixin.WeiXinNews;
import com.yuncai.modules.lottery.service.BaseService;


public interface FtMatchCommentService extends BaseService<FtMatchComment, Integer> {
	
	public int deleteByNewsId(int newsId);
	
	public List<WeiXinNews> findComment(int mid);
	
	public List<Object> checkHaveComment(String mids);
	
	public List<CommentBySearch> findComment(String mids);
	
}
