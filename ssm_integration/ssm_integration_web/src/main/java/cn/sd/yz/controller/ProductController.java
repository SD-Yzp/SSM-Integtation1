package cn.sd.yz.controller;


import cn.sd.yz.domain.Product;
import cn.sd.yz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    //@RolesAllowed("ADMIN")  //JSR250
    @RequestMapping("/findAll.do")
    @Secured("ROLE_ADMIN")   //需要加前缀
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> productList = productService.findAll();
        mv.addObject("productList",productList);
        mv.setViewName("product-list");

        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        productService.saveProduct(product);

        return "redirect:findAll.do";
    }

}
