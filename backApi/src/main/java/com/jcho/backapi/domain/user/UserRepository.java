package com.jcho.backapi.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUsersByLoginIdAndLoginPw(User user);
}