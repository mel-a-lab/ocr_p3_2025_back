//package com.rentalsbackend.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.Service;
//import com.rentalsbackend.rzepository.UserRepository;
//import com.rentalsbackend.entity.User;
//
//import java.util.Collections;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // On récupère l'utilisateur en BDD
//        User user = userRepository.findByName(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("Utilisateur introuvable : " + username);
//        }
//
//        // Retourne un UserDetails sans aucun rôle
//        return new org.springframework.security.core.userdetails.User(
//                user.getName(),
//                user.getPassword(),
//                Collections.emptyList() // aucune autorité => pas de rôle
//        );
//    }
//}
//