package ru.kharevich.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kharevich.authenticationservice.model.User;


import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
