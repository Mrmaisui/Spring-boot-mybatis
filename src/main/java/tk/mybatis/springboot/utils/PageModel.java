package tk.mybatis.springboot.utils;

import java.util.List;

public class PageModel<E> {
    /**
     * 结果集
     */
    private List<E> PageList;
    /**
     * 查询记录数
     */
    private Long totalRecords;
    /**
     * 每页有多少条数据
     */
    private Long pageSize;

    public List<E> getPageList() {
        return PageList;
    }

    public void setPageList(List<E> pageList) {
        PageList = pageList;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 当前在第几页
     */
    private Long pageNo;


    /**
     * 总页数
     * @return
     */
    public Long getTotalPages() {
       // return (totalRecords + pageSize - 1) / pageSize;
        if(totalRecords%pageSize==0){
            return totalRecords/pageSize;
        }
        return (totalRecords/pageSize)+1;
    }

    /**
     * 取得首页
     * @return
     */
    public Long getTopPageNo() {
        return 1L;
    }

    /**
     * 上一页
     * @return
     */
    public Long getPreviousPageNo() {
        if (pageNo <= 1) {
            return 1L;
        }
        return pageNo - 1;
    }

    /**
     * 下一页
     * @return
     */
    public Long getNextPageNo() {
        if (pageNo >= getBottomPageNo()) {
            return getBottomPageNo();
        }
        return pageNo + 1;
    }

    /**
     * 取得尾页
     * @return
     */
    public Long getBottomPageNo() {
        return getTotalPages();
    }
}
