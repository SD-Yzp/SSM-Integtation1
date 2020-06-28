package cn.sd.yz.service.impl;

import cn.sd.yz.dao.OrdersDao;
import cn.sd.yz.domain.Orders;
import cn.sd.yz.service.OrdersService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    @Override
    public List<Orders> findAll(int page,int size) throws Exception{
        //参数pageNum表示开始页码，pageSize表示每页条数
        PageHelper.startPage(page,size);
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(String id) throws Exception{
        return ordersDao.findById(id);
    }
}
