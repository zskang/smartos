package com.zskang.bus.customer.test;

import com.zskang.bus.customer.model.CustomerVO;
import com.zskang.bus.customer.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    public void getCustomerListTest() throws Exception{
        List<CustomerVO> customerVOList=this.customerService.getCustomerList("");
        Assert.assertEquals(2,customerVOList.size());
    }



}
