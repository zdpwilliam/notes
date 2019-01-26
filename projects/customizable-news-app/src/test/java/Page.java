import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用于分页的工具类
 * 
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<T> list; 			// 对象记录结果集
	private int total = 0; 			// 总记录数
	private int limit = 5; 			// 每页显示记录数
	private int pages = 1; 			// 总页数
	private int pageNumber = 1; 	// 当前页

	private boolean isFirstPage = false; 		// 是否为第一页
	private boolean isLastPage = false; 		// 是否为最后一页
	private boolean hasPreviousPage = false; 	// 是否有前一页
	private int previousPageNumber = 0; 		// 前一页页码
	private boolean hasNextPage = false; 		// 是否有下一页
	private int nextPageNumber = 0; 			// 下一页页码

	private int navigatePages = 7; 				// 导航页码数
	private List<Integer> navigatePageNumbers; 	// 所有导航页号
	private boolean includeFirstPage;			// 导航页是否包含首页
	private boolean includeLastPage;			// 导航页是否包含尾页

	public Page(int total, int pageNumber) {
		init(total, pageNumber, limit);
	}

	public Page(int total, int pageNumber, int limit) {
		init(total, pageNumber, limit);
	}

	public Page(List<T> sourceList, int pageNumber) {
		init(sourceList.size(), pageNumber, limit);
		initList(sourceList);
	}

	public Page(List<T> sourceList, int pageNumber, int limit) {
		init(sourceList.size(), pageNumber, limit);
		initList(sourceList);
	}
	
	/**
	 * 初始化必要的参数
	 */
	private void init(int total, int pageNumber, int limit) {
		// 设置基本参数
		this.total = total;
		this.limit = limit;
		this.pages = (this.total - 1) / this.limit + 1;
		// 根据输入可能错误的当前号码进行自动纠正
		if (pageNumber < 1) {
			this.pageNumber = 1;
		}
		else if (pageNumber > this.pages) {
			this.pageNumber = this.pages;
		}
		else {
			this.pageNumber = pageNumber;
		}
		// 基本参数设定之后进行导航页面的计算
		calcNavigatePageNumbers();
		// 以及页面边界的判定
		judgePageBoudary();
	}

	/**
	 * 基于内存的分页的初始化
	 * 
	 * @param sourceList
	 */
	private void initList(List<T> sourceList) {
		int positionIncrement = pageNumber * limit;
		int endPosition = total > limit ? (positionIncrement > total ? total : positionIncrement) : total;
		this.list = sourceList.subList(positionIncrement - limit, endPosition);
	}

	/**
	 * 添加已分好的当页数据，当且仅当init调用后才能使用
	 * @param sourceList
	 */
	public void addPagedList(List<T> sourceList) {
		this.list = sourceList;
	}
	
	/**
	 * 计算导航页
	 */
	private void calcNavigatePageNumbers() {
		// 当总页数小于或等于导航页码数时
		if (pages <= navigatePages) {
			navigatePageNumbers = new ArrayList<Integer>(pages);
			for(int i = 1; i <= pages; i++) {
				navigatePageNumbers.add(i);
			}
		}
		else { // 当总页数大于导航页码数时
			navigatePageNumbers = new ArrayList<Integer>(navigatePages);
			int startNum = pageNumber - navigatePages / 2;
			int endNum = pageNumber + navigatePages / 2;
            
			if (startNum < 1) {
				startNum = 1;
				// (最前navPageCount页
				for(int i = 0; i < navigatePages; i++) {
					navigatePageNumbers.add(startNum++);
				}
			}
			else if (endNum > pages) {
				endNum = pages;
				// 最后navPageCount页
				for(int i = navigatePages - 1; i >= 0; i--) {
					navigatePageNumbers.add(endNum--);
				}
				Collections.sort(navigatePageNumbers);
			}
			else {
				// 所有中间页
				for(int i = 0; i < navigatePages; i++) {
					navigatePageNumbers.add(startNum++);
				}
			}
		}
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNumber == 1;
		isLastPage = pageNumber == pages && pageNumber != 1;
		hasPreviousPage = pageNumber != 1;
		previousPageNumber = hasPreviousPage ? (pageNumber - 1) : 1;
		hasNextPage = pageNumber != pages;
		nextPageNumber = hasNextPage ? (pageNumber + 1) : pages;
		includeFirstPage = navigatePageNumbers.contains(Integer.valueOf(1));
		includeLastPage = navigatePageNumbers.contains(Integer.valueOf(pages));
	}

	/**
	 * 截取当前页数据
	 * 
	 * @param sourceList
	 */
	public void setList(List<T> sourceList) {
		initList(sourceList);
	}

	/**
	 * 得到当前页的内容
	 * 
	 * @return {List}
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 得到记录总数
	 * 
	 * @return {int}
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 得到每页显示多少条记录
	 * 
	 * @return {int}
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 得到页面总数
	 * 
	 * @return {int}
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * 得到当前页号
	 * 
	 * @return {int}
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 得到所有导航页号
	 * 
	 * @return {List<Integer>}
	 */
	public List<Integer> getNavigatePageNumbers() {
		return navigatePageNumbers;
	}

	public boolean getFirstPage() {
		return isFirstPage;
	}

	public boolean getLastPage() {
		return isLastPage;
	}

	public boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	public int getPreviousPageNumber() {
		return previousPageNumber;
	}

	public boolean getHasNextPage() {
		return hasNextPage;
	}

	public boolean getIncludeFirstPage() {
		return includeFirstPage;
	}

	public boolean getIncludeLastPage() {
		return includeLastPage;
	}

	public int getNextPageNumber() {
		return nextPageNumber;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	@Override
	public String toString() {
		return "Page{" +
				"list=" + list +
				", total=" + total +
				", limit=" + limit +
				", pages=" + pages +
				", pageNumber=" + pageNumber +
				", isFirstPage=" + isFirstPage +
				", isLastPage=" + isLastPage +
				", hasPreviousPage=" + hasPreviousPage +
				", previousPageNumber=" + previousPageNumber +
				", hasNextPage=" + hasNextPage +
				", nextPageNumber=" + nextPageNumber +
				", navigatePages=" + navigatePages +
				", navigatePageNumbers=" + navigatePageNumbers +
				", includeFirstPage=" + includeFirstPage +
				", includeLastPage=" + includeLastPage +
				'}';
	}
}
