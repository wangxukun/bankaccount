package xdl.wxk.financing.web.UI;
/**
 * 提交结果反馈信息类
 * @author 王旭昆
 *
 */
public class FeedbackInformation {
	private String title;
	private String message;
	private String forward;
	public FeedbackInformation() {
	}
	public FeedbackInformation(String title, String message, String forward) {
		super();
		this.title = title;
		this.message = message;
		this.forward = forward;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	
}
