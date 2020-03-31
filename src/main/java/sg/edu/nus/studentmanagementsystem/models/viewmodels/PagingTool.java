package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class PagingTool {
	private int currentPage = 0;
	private long totalUnits;
	private int pageSize = 2;

	private long totalPage;
	private int prefPage = currentPage > 1 ? currentPage - 1 : currentPage;
	private int nextPage = currentPage + 1 <= totalPage ? currentPage + 1 : currentPage;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage() {
		this.totalPage = totalUnits % pageSize == 0 ? totalUnits / pageSize : totalUnits / pageSize + 1;
	}

	public long getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(long totalUnits) {
		this.totalUnits = totalUnits;
		setTotalPage();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		setTotalPage();
	}

	public int getNextPage() {

		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrefPage() {

		return prefPage;
	}

	public void setPrefPage(int prefPage) {
		this.prefPage = prefPage;
	}
}
