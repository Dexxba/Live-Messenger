package com.live.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Users getUserById(int id) {
        return usersRepository.findById(id).orElse(null);
    }

    public UserDto getUserDto(Integer id) {
        Users user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setProfilePicture(user.getProfilePicture());
        userDto.setAdmin(user.isAdmin());
        return userDto;
    }

    public Users createUser(UserDto userDto) {
        Users newUser = new Users();
        return saveUser(userDto, newUser);
    }

    public Users updateUser(int id, UserDto userDto) {
        Users existingUser = usersRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return saveUser(userDto, existingUser);
    }

    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }

    public Users saveUser(UserDto userDto, Users existingUser) {
        existingUser.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        existingUser.setEmail(userDto.getEmail());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setProfilePicture(userDto.getProfilePicture());
        existingUser.setAdmin(userDto.isAdmin());
        return usersRepository.save(existingUser);
    }
}

