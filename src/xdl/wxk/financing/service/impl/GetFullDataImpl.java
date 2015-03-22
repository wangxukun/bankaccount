package xdl.wxk.financing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xdl.wxk.financing.service.IGetFullData;
import xdl.wxk.financing.tools.Calculator;
import xdl.wxk.financing.tools.WebUtils;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;

public class GetFullDataImpl implements IGetFullData {

	@Override
	public List<DataInfo> GetFullData(InitAccount init, List<DataInfo> data) {
		List<DataInfo> list = new ArrayList<DataInfo>();
		DataInfo top = new DataInfo();
		top.setAccountdetailid(-2);	//设置此条记录ID
		top.setGroupid(init.getAccountid()); //所属单位ID
		top.setAccountname(init.getAccountname()); //所属单位名称
		top.setSummary(init.getSummary());	//摘要
		top.setAmount(init.getAmount()); //初始化金额
		top.setBalance(init.getAmount()); //初始化余额
		top.setNumber(init.getDirection());//初始化余额方向
		top.setDirection(init.getDirection()); //初始化方向
		top.setFreeze(1);
		
		String tempData = top.getBalance();
		
		list.add(top);
		Iterator<DataInfo> iter = data.iterator();
		while(iter.hasNext()){
			DataInfo temp = iter.next();
			if(temp.getDirection()==0){	//本期发生额在借方
				temp.setBalance(Calculator.add(tempData, temp.getAmount()).toString());
				tempData = temp.getBalance();
			}else{
				temp.setBalance(Calculator.subtract(tempData, temp.getAmount()).toString());
				tempData = temp.getBalance();
			}
			//设置余额方向，并把余额由负数改为正数
			{
				BigDecimal bigdata = new BigDecimal(tempData);
				int f = bigdata.signum();//取得数值的正负号
				if(f == -1){
					temp.setNumber(1);//设置余额为贷方
					temp.setBalance(bigdata.negate().toString());//将负数改为正数
				}else{
					temp.setNumber(0);//设置余额为借方
				}
			}
			list.add(temp);
		}
		return list;
	}

}
