package cn.sd.yz.service;

import java.util.List;
import cn.sd.yz.domain.Product;

public interface ProductService {
    List<Product> findAll() throws Exception;

    void saveProduct(Product product) throws Exception;
}
