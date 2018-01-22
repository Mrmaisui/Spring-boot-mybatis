package tk.mybatis.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.springboot.Application;
import tk.mybatis.springboot.model.Country;
import tk.mybatis.springboot.model.DTO.CityDTO;
import tk.mybatis.springboot.service.CityService;
import tk.mybatis.springboot.service.CountryService;
import tk.mybatis.springboot.utils.CreateSimpleExcelToDisk;
import tk.mybatis.springboot.utils.PageBean;
import tk.mybatis.springboot.utils.PageModel;
import tk.mybatis.springboot.utils.PageUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @since 2017/9/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class CountryServiceTest {

    @Resource
    private CountryService countryService;

    @Test
    public void test() {
        Country country = new Country();
        List<Country> all = countryService.getAll(country);
        for (Country c : all) {
            System.out.println(c.getCountryname());
        }
    }

    @Resource
    private CityService cityService;

    @Test
    public void getCity() {
        List<CityDTO> cityList = cityService.getCityDTO();
        for (CityDTO cityDTO : cityList) {
            System.out.println(cityDTO.getState());
            System.out.println(cityDTO.getName());
            System.out.println(cityDTO.getCountryName());
        }
    }

    @Test
    public void getCountryPage() {
        PageModel pageModel = countryService.getAllCountrybyPage(3L, 3L);
        List<Country> countryList = pageModel.getPageList();
        for (Country country : countryList) {
            System.out.println(country.getCountryname());
        }
    }

    //分页，这种方式是直接将所要查询的表内数据直接全部读取出放置集合中，当数据过多时候，效率不高！！！
    @Test
    public void getCountryPage1() {
        int page = 2;//传进来的页数
        List<Country> countryList = countryService.getAllCountryby();//将查询结果存放在List集合里

        PageBean pagebean = new PageBean(countryList.size());//初始化PageBean对象
//设置当前页
        pagebean.setCurPage(page); //这里page是从页面上获取的一个参数，代表页数
//获得分页大小
        int pagesize = pagebean.getPageSize();
//获得分页数据在list集合中的索引
        int firstIndex = (page - 1) * pagesize;
        int toIndex = page * pagesize;
        if (toIndex > countryList.size()) {
            toIndex = countryList.size();
        }
        if (firstIndex > toIndex) {
            firstIndex = 0;
            pagebean.setCurPage(1);
        }
//截取数据集合，获得分页数据
        List<Country> courseList = countryList.subList(firstIndex, toIndex);
        for (Country country : courseList) {
            System.out.println(country.getCountryname());
        }
    }

    //第二种方法 这种方法是根据用户传进的页数到数据库进行查询，效率相对提高
    @Test
    public void getCountryPage2(){
      PageUtil<Country> PageList= countryService.FindPage(50L,5L);
      List<Country> countryList1=PageList.getPageList();
      for (Country country:countryList1){
          System.out.println(country.getCountryname());
      }
    }

    @Resource
    public CreateSimpleExcelToDisk createSimpleExcelToDisk;
    @Test
    public void getExcel()throws Exception{
        String fileName="City";
        String file="D:/city.xls";
        createSimpleExcelToDisk.getExcel(fileName,file);
    }
    @Test
    public void readExcel()throws Exception{
        String fileName="City";
        String file="D:/city.xls";
        createSimpleExcelToDisk.readExcel();
    }

}
