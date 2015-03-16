package xdl.wxk.financing.tools;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

public class WebUtils {
	//利用反射机制
	public static <T> T requestToBean(HttpServletRequest request,Class<T> beanClass){
		try {
			//1.创建要封装数据的bean
			T bean = beanClass.newInstance();
			//2.把request中的数据整合到bean中
			Enumeration<String> e = request.getParameterNames();
			while(e.hasMoreElements()){
				String paramName = (String)e.nextElement();
				Field field = beanClass.getDeclaredField(paramName);
				//如果数据类型是java.util.Date，日期类型不会自动转换
				if(field.getType().getName().equals("java.util.Date")){
					String value = request.getParameter(paramName);
					if(value != null && !"".equals(value.trim())){
						SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
						try{
							Date paramValue = sDate.parse(value);
							BeanUtils.setProperty(bean, paramName, paramValue);
						}catch(Exception e1){
							//特意设置一个格式正确，但不存在的日期，防止在这里就抛出异常
							Date paramValue = sDate.parse("2015-2-31");
							BeanUtils.setProperty(bean, paramName, paramValue);
						}
					}
				}else{
					String paramValue = request.getParameter(paramName);
					BeanUtils.setProperty(bean, paramName, paramValue);
				}
				
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
