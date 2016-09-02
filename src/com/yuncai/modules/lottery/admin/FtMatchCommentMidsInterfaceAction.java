package com.yuncai.modules.lottery.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsJcService;
import com.yuncai.modules.lottery.service.oracleService.weixin.FtMatchCommentService;

/**
 * 检查指定mid列表哪些有专家点评-接口action
 * @author blackworm
 *
 */
@Controller("ftMatchCommentMidsInterfaceAction")
@Scope("prototype")
public class FtMatchCommentMidsInterfaceAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	@Resource
	private FtMatchCommentService ftMatchCommentService;
	@Resource
	private FtImsJcService ftImsJcService;
	
	private String mids;
	private List<Object> midList;
	private List<Map<String, Object>> ranks;
	private int vt; // 0/1 用来区分不同使用场合
	private List<CommentBySearch> comments;
	
	
	public List<Object> getMidList() {
		return midList;
	}

	public void setMids(String mids) {
		this.mids = mids;
	}
	
	public List<Map<String, Object>> getRanks() {
		return ranks;
	}
	
	public void setVt(int vt) {
		this.vt = vt;
	}
	
	public List<CommentBySearch> getComments() {
		return comments;
	}

	public FtMatchCommentMidsInterfaceAction() {
		super();
	}
	
	public String find(){
		if(DBHelper.isNoNull(mids)){//不为空，查询参数mids=3286,3287,3288,5000的结果
			
			//过滤参数，检查格式的是否合法
			String [] idArr=mids.split(",");
			mids="";
			for (int i=0;i<idArr.length;i++) {
				String s=idArr[i];
				mids+=subString(s);
				if(i!=idArr.length-1){
					mids+=",";
				}
			}
			if(DBHelper.isNoNull(mids)){
				mids=mids.trim();
				
				midList=new ArrayList<Object>();
				comments=new ArrayList<CommentBySearch>();
				if(vt==0){
					midList=ftMatchCommentService.checkHaveComment(mids);
				}else if(vt==1){
					comments=ftMatchCommentService.findComment(mids);
				}
				
				ranks=ftImsJcService.findRank(mids);
				for (Map<String, Object> map : ranks) {
					map.put("mb_rank", subString(map.get("mb_rank")+""));
					map.put("tg_rank", subString(map.get("tg_rank")+""));
				}
			}
		}
		return SUCCESS;
	}

	private String subString(String string){
		String newString="";
		if(string==null || "".equals(string)) return newString;
		
		char []nums={'0','1','2','3','4','5','6','7','8','9'};
		for(int i=0;i<string.length();i++){
			char c=string.charAt(i);
			for(int j=0;j<nums.length;j++){
				if(c==nums[j]){
					newString+=c;
					break;
				}
			}
		}
		
		return newString;
	}
	
}
