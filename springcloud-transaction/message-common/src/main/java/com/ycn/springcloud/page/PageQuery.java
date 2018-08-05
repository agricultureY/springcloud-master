package com.ycn.springcloud.page;

/**
 * 分页查询的请求参数
 *
 * @author ycn
 * @package com.ycn.springcloud.page
 * @ClassName PageQuery
 * @Date 2018/7/30 10:09
 */
public class PageQuery {

    /**
     * 每页的条数
     */
    private Integer pageSize;

    /**
     * 页编码(第几页)
     */
    private Integer pageNo;

    /**
     * 排序方式(asc 或者 desc)
     */
    private String order;

    /**
     * 排序的字段名称
     */
    private String sort;

    public PageQuery() {
    }

    public PageQuery(Integer pageSize, Integer pageNo, String order, String sort) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.order = order;
        this.sort = sort;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
