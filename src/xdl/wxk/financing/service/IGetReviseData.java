package xdl.wxk.financing.service;

import java.util.List;

import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.web.formbean.DataSearchForm;

public interface IGetReviseData {
	public List<DataInfo> GetReviseData(DataSearchForm form);
}
