package cn.sd.yz.controller;

import cn.sd.yz.domain.Role;
import cn.sd.yz.domain.UserInfo;
import cn.sd.yz.service.RoleService;
import cn.sd.yz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findAll();
        mv.addObject("userList", userList);
        mv.setViewName("user-list");

        return mv;
    }


    @RequestMapping("/save.do")
    @PreAuthorize("authentication.principal.username='tom'")  //只有用户名为tom可以操作这个方法
    public String save(UserInfo userInfo) throws Exception {
        userService.saveUser(userInfo);

        return "redirect:findAll.do";
    }


    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user", userInfo);
        mv.setViewName("user-show");

        return mv;
    }

    @RequestMapping("/findUserByIdAndAllRole")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String userId) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo user = userService.findById(userId);
        List<Role> roleList = userService.findOtherRole(userId);

        mv.addObject("user", user);
        mv.addObject("roleList",roleList);
        mv.setViewName("user-role-add");

        return mv;
    }

    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name="userId",required = true) String userId,@RequestParam(name="ids",required = true) String[] roleIds) throws Exception{
        userService.addRoleToUser(userId,roleIds);

        return "redirect:findAll.do";
    }

}
