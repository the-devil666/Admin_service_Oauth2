package com.pws.admin.service;

import com.pws.admin.dto.PermissionDTO;
import com.pws.admin.dto.SignUpDTO;
import com.pws.admin.dto.UserRoleXrefDTO;

import com.pws.admin.entity.*;
import com.pws.admin.entity.Module;
import com.pws.admin.exception.config.PWSException;
import com.pws.admin.repository.ModuleRepository;
import com.pws.admin.repository.PermissionRepository;
import com.pws.admin.repository.RoleRepository;
import com.pws.admin.repository.UserRepository;
import com.pws.admin.repository.UserRoleXrefRepository;
import com.pws.admin.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author Vinayak M
 * @Date 09/01/23
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private UserRoleXrefRepository userRoleXrefRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void UserSignUp(SignUpDTO signupDTO) throws PWSException {

        Optional<User> optionalUser = userRepository.findUserByEmail(signupDTO.getEmail());
         if(optionalUser.isPresent())
             throw new PWSException("User Already Exist with Email : "+signupDTO.getEmail());
         User user = new User();
        user.setDateOfBirth(DateUtils.getUtilDateFromString(signupDTO.getDateOfBirth()));
        user.setFirstName(signupDTO.getFirstName());
        user.setIsActive(true);
        user.setLastName(signupDTO.getLastName());
        user.setEmail(signupDTO.getEmail());
        user.setPhoneNumber(signupDTO.getPhoneNumber());
        PasswordEncoder encoder = new BCryptPasswordEncoder(8);
        // Set new password
        user.setPassword(encoder.encode(signupDTO.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void addRole(Role role) throws PWSException {
        role.setIsActive(true);
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Role role) throws PWSException {
        roleRepository.save(role);
    }

    @Override
    public List<Role> fetchAllRole() throws PWSException {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> fetchRoleById(Integer id) throws PWSException {
        return roleRepository.findById(id);
    }


    @Override
    public void deactivateOrActivateRoleById(Integer id, boolean flag) throws PWSException {
        Optional<Role> role =  roleRepository.findById(id);
        if(role.isPresent()){
            role.get().setIsActive(flag);
            roleRepository.save(role.get());
        }
    }

    @Override
    public void addModule(Module module) throws PWSException {
        moduleRepository.save(module);
    }

    @Override
    public void updateRole(Module module) throws PWSException {
       Optional<Module> optionalModule = moduleRepository.findById(module.getId());
        if(optionalModule.isPresent()){
            moduleRepository.save(module);
        }else
            throw new PWSException("Module Doest Exist");

    }

    @Override
    public List<Module> fetchAllModule() throws PWSException {
        return moduleRepository.findAll();
    }

    @Override
    public Optional<Module> fetchModuleById(Integer id) throws PWSException {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if(optionalModule.isPresent()){
            return optionalModule;
        }else
            throw new PWSException("Module Doest Exist");
    }

    @Override
    public void deactivateOrActivateModuleById(Integer id, boolean flag) throws PWSException {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        Module module =optionalModule.get();
        if(optionalModule.isPresent()){
            module.setIsActive(flag);
            moduleRepository.save(module);
        }else
            throw new PWSException("Module Doest Exist");
    }

    @Override
    public void saveOrUpdateUserXref(UserRoleXrefDTO userRoleXrefDTO) throws PWSException {
       Optional<UserRoleXref>  optionalUserRoleXref = userRoleXrefRepository.findById(userRoleXrefDTO.getId());
        UserRoleXref userRoleXref =null;
       if(optionalUserRoleXref.isPresent()){
           userRoleXref = optionalUserRoleXref.get();
        }
       else {
           userRoleXref = new UserRoleXref();
       }
        Optional<User>  optionalUser = userRepository.findById(userRoleXrefDTO.getUserId());
        if(optionalUser.isPresent()){
            userRoleXref.setUser(optionalUser.get());
        }else{
            throw new PWSException("User Doest Exist");
        }

        Optional<Role>  optionalRole = roleRepository.findById(userRoleXrefDTO.getRoleId());
        if(optionalRole.isPresent()){
            userRoleXref.setRole(optionalRole.get());
        }else{
            throw new PWSException("Role Doest Exist");
        }
        userRoleXref.setIsActive(userRoleXrefDTO.getIsActive());

        userRoleXrefRepository.save(userRoleXref);


    }

    @Override
    public void deactivateOrActivateAssignedRoleToUser(Integer id , Boolean  flag) throws PWSException {
        Optional<UserRoleXref>  optionalUserRoleXref = userRoleXrefRepository.findById(id);
        UserRoleXref userRoleXref = optionalUserRoleXref.get();
        if(optionalUserRoleXref.isPresent()) {
        	optionalUserRoleXref.get().setIsActive(flag);
            userRoleXrefRepository.save(userRoleXref);
        }else
            throw  new PWSException("Record Doest Exist");

    }

    @Override
    public Optional<UserRoleXref> fetchUserById(Integer Id) throws PWSException{
    	return userRoleXrefRepository.findById(Id);
    	
    }
    
    @Override
    public List<User> fetchUserByRole(Integer roleId) throws PWSException {
        return userRoleXrefRepository.fetchAllUsersByRoleId(roleId);
    }

    @Override
    public void addPermission(PermissionDTO permissionDTO) throws PWSException {
    	Permission permission = new Permission();
    	
    	permission.setIsActive(permissionDTO.getIsActive());
    	permission.setIsAdd(permissionDTO.getIsAdd());
    	permission.setIsDelete(permissionDTO.getIsDelete());
    	permission.setIsUpdate(permissionDTO.getIsUpdate());
    	permission.setIsView(permissionDTO.getIsView());
    	Optional<Module> module= moduleRepository.findById(permissionDTO.getModel());
    	permission.setModel(module.get());
    	Optional<Role> role = roleRepository.findById(permissionDTO.getRole());
    	permission.setRole(role.get());
    	permissionRepository.save(permission);

    }

    @Override
    public void updatePermission(PermissionDTO permissionDTO) throws PWSException {
    	Optional<Permission> optionalpermission= permissionRepository.findById(permissionDTO.getId());
    	if(optionalpermission.isPresent()) {
    		optionalpermission.get().getId();
    		optionalpermission.get().setIsActive(permissionDTO.getIsActive());
    		optionalpermission.get().setIsAdd(permissionDTO.getIsAdd());
    		optionalpermission.get().setIsDelete(permissionDTO.getIsDelete());
    		optionalpermission.get().setIsUpdate(permissionDTO.getIsUpdate());
    		optionalpermission.get().setIsView(permissionDTO.getIsView());
        	Optional<Module> module= moduleRepository.findById(permissionDTO.getModel());
        	optionalpermission.get().setModel(module.get());
        	Optional<Role> role = roleRepository.findById(permissionDTO.getRole());
        	optionalpermission.get().setRole(role.get());
        	permissionRepository.save(optionalpermission.get());
    		
    	}else
    	{
    		throw  new PWSException("Record Doest Exist");
    	}

    }

    @Override
    public List<Permission> fetchAllPermission() throws PWSException {
    	List<Permission> permissionlist=permissionRepository.findAll();
		return permissionlist;
    }

    @Override
    public Optional<Permission> fetchPermissionById(Integer id) throws PWSException {
         Optional<Permission> optionalpermission= permissionRepository.findById(id);
         if(optionalpermission.isPresent()){
             return optionalpermission;
         }else
             throw new PWSException("Permission Does't Exist");
     }
    

    @Override
    public void deactivateOrActivatePermissionById(PermissionDTO permissionDTO) throws PWSException {
        Optional<Permission>  optionalPermission = permissionRepository.findById(permissionDTO.getId());
        Permission permission = null;
        if(optionalPermission.isPresent()) {
        	permission= optionalPermission.get();
        	permission.setIsActive(permissionDTO.getIsActive());
            permissionRepository.save(permission);
        }else 
        	
        	throw  new PWSException("Permission Id Doest Exist");
        
    }
//    @Override
//    public void deactivateOrActivatePermissionById(Integer id , Boolean  flag) throws PWSException {
//        Optional<Permission>  optionalPermission = permissionRepository.findById(id);
//        Permission permission = optionalPermission.get();
//        if(optionalPermission.isPresent()) {
//        	optionalPermission.get().setIsActive(flag);
//        	optionalPermission.get().setIsView(flag);
//        	optionalPermission.get().setIsAdd(flag);
//        	optionalPermission.get().setIsUpdate(flag);
//        	optionalPermission.get().setIsDelete(flag);
//
//            permissionRepository.save(permission);
//        }else
//            throw  new PWSException("Record Doest Exist");
//
//    }
//    
    


    }

