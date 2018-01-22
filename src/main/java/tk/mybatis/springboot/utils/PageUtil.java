package tk.mybatis.springboot.utils;

import java.util.List;

public class PageUtil<E> {
    private Long currentPage;//当前页
    private Long totalPage;//总页数
    private Long count;//一页多少条数据
    private List<E> PageList;//当前页的数据
    private Long totalCount;//数据总条数

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<E> getPageList() {
        return PageList;
    }

    public void setPageList(List<E> pageList) {
        PageList = pageList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
