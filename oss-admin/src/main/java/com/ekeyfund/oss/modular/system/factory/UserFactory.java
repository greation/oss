package com.ekeyfund.oss.modular.system.factory;

import com.ekeyfund.oss.modular.system.model.User;
import com.ekeyfund.oss.modular.system.transfer.UserDto;
import com.ekeyfund.oss.modular.system.transfer.UserEditDto;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 用户创建工厂
 *
 * @author fengshuonan
 * @date 2017-05-05 22:43
 */
public class UserFactory {

    public static User createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }

    public static UserDto createUserDto(User user){
        if(user == null){
            return null;
        }else{
            UserDto userDto = new UserDto();

            BeanUtils.copyProperties(user,userDto);
            return userDto;
        }
    }

    public static User createUser(UserEditDto userEditDto){
        if(userEditDto==null){
            return null;
        }

        else{
            User user =new User();
            user.setUpdatetime(new Date());

            BeanUtils.copyProperties(userEditDto,user);
            return user;
        }
    }


}
