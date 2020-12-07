package gr.aueb.invoicesmanagement.service;

import gr.aueb.invoicesmanagement.dao.RoleRepository;
import gr.aueb.invoicesmanagement.dao.UserRepository;
import gr.aueb.invoicesmanagement.dto.UserDto;
import gr.aueb.invoicesmanagement.model.User;
import gr.aueb.invoicesmanagement.model.UserRole;
import gr.aueb.invoicesmanagement.validator.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public User registerNewUserAccount(UserDto userDto)
            throws EmailExistsException {

        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address: "
                            +  userDto.getEmail());
        }

        final User user = new User();

        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName(UserRole.USER.name())));

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
