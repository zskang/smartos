package com.zskang.bus.customer.test;

import com.zskang.bus.customer.model.CustomerVO;
import com.zskang.bus.customer.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() {
        System.out.println("单元测试初始化...");
    }

    @Test
    public void getCustomerListTest() throws Exception {
        List<CustomerVO> customerVOList = this.customerService.getCustomerList("");
        Assert.assertEquals(2, customerVOList.size());
    }

    @Test
    public void getCustomerTest() throws Exception {
        int id = 1;
        CustomerVO customerVO = this.customerService.getDetailInfo(id);
        Assert.assertNotNull(customerVO);
    }

    @Test
    public void createCustomerTest() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "test");
        map.put("contact", "aaaaa");
        map.put("telphone", "13244444444");
        map.put("email", "1111111@ww.com");
        boolean b = this.customerService.createCustomer(map);
        Assert.assertTrue(b);
    }

    @Test
    public void updateCustomerTest() throws Exception {
        int id = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "00000");
        map.put("contact", "01111111111111111111111");
        map.put("telphone", "22222222222");
        map.put("email", "eeeeeee@44.com");
        boolean b = this.customerService.updateCustomer(id, map);
        Assert.assertTrue(b);
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        int id = 1;
        boolean b = this.customerService.deleteCustomer(id);
        Assert.assertTrue(b);
    }


}
