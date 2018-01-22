/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;
import tk.mybatis.springboot.mapper.CountryMapper;
import tk.mybatis.springboot.model.Country;
import tk.mybatis.springboot.utils.PageModel;
import tk.mybatis.springboot.utils.PageUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class CountryService {

    @Resource
    private CountryMapper countryMapper;

    public List<Country> getAll(Country country) {
        if (country.getPage() != null && country.getRows() != null) {
            PageHelper.startPage(country.getPage(), country.getRows());
        }
        Example example = new Example(Country.class);
        Example.Criteria criteria = example.createCriteria();
        if (country.getCountryname() != null && country.getCountryname().length() > 0) {
            criteria.andLike("countryname", "%" + country.getCountryname() + "%");
        }
        if (country.getCountrycode() != null && country.getCountrycode().length() > 0) {
            criteria.andLike("countrycode", "%" + country.getCountrycode() + "%");
        }
        return countryMapper.selectByExample(example);
    }

    public List<Country> getAllByWeekend(Country country) {
        if (country.getPage() != null && country.getRows() != null) {
            PageHelper.startPage(country.getPage(), country.getRows());
        }
        Weekend<Country> weekend = Weekend.of(Country.class);
        WeekendCriteria<Country, Object> criteria = weekend.weekendCriteria();
        if (country.getCountryname() != null && country.getCountryname().length() > 0) {
            criteria.andLike(Country::getCountryname, "%" + country.getCountryname() + "%");
        }
        if (country.getCountrycode() != null && country.getCountrycode().length() > 0) {
            criteria.andLike(Country::getCountrycode, "%" + country.getCountrycode() + "%");
        }
        return countryMapper.selectByExample(weekend);
    }

    public Country getById(Integer id) {
        return countryMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        countryMapper.deleteByPrimaryKey(id);
    }

    public void save(Country country) {
        if (country.getId() != null) {
            countryMapper.updateByPrimaryKey(country);
        } else {
            countryMapper.insert(country);
        }
    }


    //新增测试需求

    @Resource
    private CountryMapper countryMapper1;

    public Country getCountryById(Long countryById) {
        return countryMapper1.getAllCountrybyId(countryById);
    }

    //分页
    public PageModel getAllCountrybyPage(Long pageNo, Long pageSize) {
        List<Country> countryList = countryMapper1.getAllCountrybyPage(pageNo, pageSize);
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalRecords(0L);
        pageModel.setPageList(countryList);
        return pageModel;
    }

    public List<Country> getAllCountryby() {
        return countryMapper.getAllCountryby();
    }

    public PageUtil<Country> FindPage(Long page, Long count) {
        Long totle = countryMapper1.getCOUNTCountryby();
        Long totlePage = 0L;
        if (totle % count == 0) {
            totlePage = totle / count;
        } else {
            totlePage = (totle / count) + 1;
        }
        if (page > totlePage) {
            page = totlePage;
        }
        if (page == 1) {
            page = 0L;
        } else {
            page = (page - 1) * count;
        }
        List<Country> countryList = countryMapper1.getAllCountrybyPage(page, count);
        System.out.println(totle);
        PageUtil<Country> p = new PageUtil<Country>();
        p.setPageList(countryList);
        p.setCurrentPage(page);
        p.setCount(count);
        p.setTotalCount((long) totle);
        //  Long totlePage = totle%count==0?totle/count:(totle/count)+1;

        p.setTotalPage(totlePage);
        return p;
    }

}
