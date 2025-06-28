package com.hxl.service;

import com.hxl.vo.BusinessDataVO;

public interface WorkspaceService {
    /**
     * 查询今日运营数据
     * @return 返回今日的一些运营数据信息
     */
    BusinessDataVO businessData();
}
