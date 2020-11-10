package com.lth.work.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lth.work.mapper.HomewMapper;
import com.lth.work.pojo.Homew;
import com.lth.work.pojo.Twork;
import com.lth.work.service.HomewService;
import com.lth.work.util.PageRequest;
import com.lth.work.util.PageResult;
import com.lth.work.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomewServiceImpl implements HomewService {
    @Autowired
    private HomewMapper homewMapper;

    @Override
    public Homew findById(Integer id) {
        return homewMapper.findById(id);
    }

    @Override
    public void updateHomew(Homew homew) {
        homewMapper.updateHomew(homew);
    }

    @Override
    public List<Homew> findByCate(Integer category) {
        return homewMapper.findByCate(category);
    }

    @Override
    public List<Homew> findAll() {
        return homewMapper.findAll();
    }

    @Override
    public PageResult findPage(PageRequest pageRequest,String sort) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest,sort));
    }

    @Override
    public List<Homew> findByCatePost(Integer category) {
        return homewMapper.findByCatePost(category);
    }

    @Override
    public void insertHomew(Homew homew) {
        homewMapper.insertHomew(homew);
    }

    /**
     * 调用分页插件完成分页
     * @return
     */
    private PageInfo<Homew> getPageInfo(PageRequest pageRequest,String sort) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Homew> sysMenus = homewMapper.findPage(sort);
        return new PageInfo<Homew>(sysMenus);
    }
}
