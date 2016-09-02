package com.yuncai.modules.lottery.dao.oracleDao.weixin;


import java.util.List;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch;
import com.yuncai.modules.lottery.model.Oracle.weixin.FtMatchComment;
import com.yuncai.modules.lottery.model.Oracle.weixin.WeiXinNews;

public interface FtMatchCommentDao extends GenericDao<FtMatchComment, Integer>{
	
	/**
	 * 根据资讯id删除关系
	 * @param newsId
	 */
	public int deleteByNewsId(int newsId);
	
	/**
	 * 根据mid查询专家点评的内容
	 * @param mid
	 * @return
	 */
	public List<WeiXinNews> findComment(int mid);
	
	/**
	 * 检查指定mid列表哪些有专家点评
	 * @param mids 12608,12608,12606
	 * @return
	 */
	public List<Object> checkHaveComment(String mids);
	
	/**
	 * 根据指定mid列表查询专家点评
	 * @param mids 12608,12608,12606
	 * @return
	 */
	public List<CommentBySearch> findComment(String mids);
	
}
