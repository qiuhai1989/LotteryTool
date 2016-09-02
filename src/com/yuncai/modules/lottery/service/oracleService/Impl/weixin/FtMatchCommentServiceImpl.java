package com.yuncai.modules.lottery.service.oracleService.Impl.weixin;

import java.util.List;
import org.apache.log4j.Logger;

import com.yuncai.modules.lottery.dao.oracleDao.weixin.FtMatchCommentDao;
import com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch;
import com.yuncai.modules.lottery.model.Oracle.weixin.FtMatchComment;
import com.yuncai.modules.lottery.model.Oracle.weixin.WeiXinNews;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.weixin.FtMatchCommentService;

public class FtMatchCommentServiceImpl extends BaseServiceImpl<FtMatchComment, Integer> implements FtMatchCommentService {

	private FtMatchCommentDao ftMatchCommentDao;
	
	Logger log=Logger.getLogger(this.getClass());

	public void setFtMatchCommentDao(FtMatchCommentDao ftMatchCommentDao) {
		this.ftMatchCommentDao = ftMatchCommentDao;
	}

	@Override
	public int deleteByNewsId(int newsId) {
		return this.ftMatchCommentDao.deleteByNewsId(newsId);
	}

	@Override
	public List<WeiXinNews> findComment(int mid) {
		return this.ftMatchCommentDao.findComment(mid);
	}

	@Override
	public List<Object> checkHaveComment(String mids) {
		return this.ftMatchCommentDao.checkHaveComment(mids);
	}

	@Override
	public List<CommentBySearch> findComment(String mids) {
		return this.ftMatchCommentDao.findComment(mids);
	}

}
