package com.ycn.springcloud.page;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ycn
 * @package com.ycn.springcloud.page
 * @ClassName PageFactory
 * @Date 2018/7/30 12:36
 */
public class PageFactory<T> {

    private static final String ASC = "asc";

    private static final String DESC = "desc";

    private static final String PAGE_SIZE = "pageSize";

    private static final String PAGE_NO = "pageNo";

    private static final String SORT_FIELD = "sort";

    private static final String ORDER = "order";

    public Page<T> defaultPage() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int pageSize = 20;
        int pageNo = 1;

        //每页条数
        String pageSizeString = request.getParameter(PAGE_SIZE);
        if (!StringUtils.isEmpty(pageSizeString))
            pageSize = Integer.valueOf(pageSizeString);

        //第几页
        String pageNoString = request.getParameter(PAGE_NO);
        if (!StringUtils.isEmpty(pageNoString))
            pageNo = Integer.valueOf(pageNoString);

        //获取排序字段和排序类型(asc/desc)
        String sort = request.getParameter(SORT_FIELD);
        String order = request.getParameter(ORDER);

        if (StringUtils.isEmpty(sort)) {
            Page<T> page = new Page<>(pageNo, pageSize);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>(pageNo, pageSize, sort);
            if (ASC.equalsIgnoreCase(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }

    public Page<T> createPage(PageQuery pageQuery) {

        int pageSize = 20;
        int pageNo = 1;

        if (pageQuery != null && null != pageQuery.getPageSize())
            pageSize = pageQuery.getPageSize();

        if (pageQuery != null && null != pageQuery.getPageNo())
            pageNo = pageQuery.getPageNo();

        if (pageQuery == null) {
            Page<T> page = new Page<>(pageNo, pageSize);
            page.setOpenSort(false);
            return page;
        } else {
            if (StringUtils.isEmpty(pageQuery.getSort())) {
                Page<T> page = new Page<>(pageNo, pageSize);
                page.setOpenSort(false);
                return page;
            } else {
                Page<T> page = new Page<>(pageNo, pageSize, pageQuery.getSort());
                if (ASC.equalsIgnoreCase(pageQuery.getOrder())) {
                    page.setAsc(true);
                } else {
                    page.setAsc(false);
                }
                return page;
            }
        }
    }
}
