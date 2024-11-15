package com.skydev.product_inventory_management.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.skydev.product_inventory_management.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skydev.product_inventory_management.persistence.entity.CredentialEntity;
import com.skydev.product_inventory_management.persistence.repository.ICredentialEntityRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements  UserDetailsService{

    private final ICredentialEntityRepository credentialRepository;
    private final MessageUtil messageUtil;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        CredentialEntity findCred = credentialRepository.findByUsername(username)
                                            .orElseThrow(() ->
                                                    new UsernameNotFoundException(messageUtil.CREDENTIALS_USERNAME_NOT_FOUND));

        List<SimpleGrantedAuthority> listAuthority = new ArrayList<>();

        listAuthority.add(new SimpleGrantedAuthority("ROLE_".concat(findCred.getUser().getRole().getRoleName())));

        return new User(findCred.getUsername(),
                        findCred.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        listAuthority);
    }

}
