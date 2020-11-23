package com.lth.work.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lth.work.mapper.TworkMapper;
import com.lth.work.pojo.Twork;
import com.lth.work.service.TworkService;
import com.lth.work.util.PageRequest;
import com.lth.work.util.PageResult;
import com.lth.work.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TworkServiceImpl implements TworkService {
    @Autowired
    private TworkMapper tworkMapper;

    @Override
    public void insert(Twork twork) {
        tworkMapper.insert(twork);
    }

    @Override
    public Twork findByStuIDAndHomeId(Integer StuID,Integer homeid) {
        return tworkMapper.findByStuIDAndHomeId(StuID,homeid);
    }

    @Override
    public List<Twork> findByName(String name) {
        return null;
    }

    @Override
    public List<Twork> findByCate(Integer category) {
        return null;
    }

    @Override
    public List<Twork> findByidCateHome(Integer stuID, Integer category, Integer workL) {
        return tworkMapper.findByidCateHome(stuID,category,workL);
    }

    @Override
    public List<Twork> findAll() {
        return tworkMapper.findAll();
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @return
     */
    private PageInfo<Twork> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Twork> sysMenus = tworkMapper.findPage();
        return new PageInfo<Twork>(sysMenus);
    }
}
