package cn.sd.yz.service;

import cn.sd.yz.domain.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> findAll(int page,int size) throws Exception;

    Orders findById(String id) throws Exception;
}
