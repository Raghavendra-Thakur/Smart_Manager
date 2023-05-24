package com.smartcontactmanager.smartcontact.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontactmanager.smartcontact.Dao.UserRepo;
import com.smartcontactmanager.smartcontact.entities.UserEntity;

public class UserDetailsServiceimpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        UserEntity user = userRepo.getuserEntitybyname(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        CustomUSerDetails customUSerDetails = new CustomUSerDetails(user);

        return customUSerDetails;
    }

}
