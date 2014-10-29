package xdl.wxk.financing.tools;

public class PageInfo {
	private int currentPage;
	private int totalPage;
	private int totalRecord;

	public PageInfo() {

	}
	
	public PageInfo(int currentPage, int totalPage, int totalRecord) {
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalRecord = totalRecord;
	}

	public int getFirstPage() {
		return 1;
	}

	public int getPreviousPage() {
		return (currentPage - 1) == 0 ? 1 : (currentPage - 1);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNextPage() {
		return (currentPage + 1) > totalPage ? totalPage : (currentPage + 1);
	}

	public int getLastPage() {
		return totalPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
}
