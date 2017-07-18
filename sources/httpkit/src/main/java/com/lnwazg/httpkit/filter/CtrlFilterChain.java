package com.lnwazg.httpkit.filter;

import java.util.ArrayList;
import java.util.List;

import com.lnwazg.httpkit.controller.BaseController;

/**
 * 过滤器链
 * @author lnwazg@126.com
 * @version 2017年3月15日
 */
public class CtrlFilterChain extends BaseController
{
    /**
     * 该过滤器链所持有的一系列过滤器列表
     */
    private List<CtrlFilter> filters = new ArrayList<>();
    
    /**
     * 最终回调controller方法的回调函数
     */
    ControllerCallback callback;
    
    /**
     * 移动到链条的下一个元素
     * @author lnwazg@126.com
     * @param testFilterA 
     */
    public void moveToNext(CtrlFilter currentCtrlFilter)
    {
        int currentIndex = filters.indexOf(currentCtrlFilter);
        if (currentIndex >= filters.size() - 1)
        {
            //如果当前已经到达末尾了，那么就直接调用最终的controller方法吧
            if (callback != null)
            {
                callback.call();
            }
        }
        else
        {
            //还没到末尾，则将请求继续向后移动
            filters.get(currentIndex + 1).doFilter(this);
        }
    }
    
    /**
     * 设置回调函数
     * @author lnwazg@126.com
     * @param callback
     */
    public void setCallback(ControllerCallback callback)
    {
        this.callback = callback;
    }
    
    /**
     * 添加到链尾
     * @author lnwazg@126.com
     * @param ctrlFilter
     */
    public void addToChain(CtrlFilter ctrlFilter)
    {
        filters.add(ctrlFilter);
    }
    
    /**
     * 获取过滤器列表
     * @author lnwazg@126.com
     * @return
     */
    public List<CtrlFilter> getFilters()
    {
        return filters;
    }
}
