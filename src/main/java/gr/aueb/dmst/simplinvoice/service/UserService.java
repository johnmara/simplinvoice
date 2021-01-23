package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dto.UserDto;
import gr.aueb.dmst.simplinvoice.dao.RoleRepository;
import gr.aueb.dmst.simplinvoice.dao.UserRepository;
import gr.aueb.dmst.simplinvoice.model.User;
import gr.aueb.dmst.simplinvoice.model.UserRole;
import gr.aueb.dmst.simplinvoice.validator.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${new.user.enabled}")
    boolean newUserEnabled;

    @Transactional
    public User registerNewUserAccount(UserDto userDto)
            throws EmailExistsException {

        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address: "
                            +  userDto.getEmail());
        }

        User user = new User();

        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName(UserRole.COMPANY.name())));
        user.setEnabled(newUserEnabled);

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

}
