package xdl.wxk.financing.tools;

import java.math.BigDecimal;

public class Calculator {
	public static BigDecimal add(String a,String b){
		BigDecimal big1 = new BigDecimal(a);
		BigDecimal big2 = new BigDecimal(b);
		return big1.add(big2);
	}
	public static BigDecimal subtract(String a,String b){
		BigDecimal big1 = new BigDecimal(a);
		BigDecimal big2 = new BigDecimal(b);
		return big1.subtract(big2);
	}
	public static BigDecimal abs(String a){
		return new BigDecimal(a).abs();
	}
}
