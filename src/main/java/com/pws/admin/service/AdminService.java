package com.pws.admin.service;

import com.pws.admin.dto.PermissionDTO;
import com.pws.admin.dto.SignUpDTO;
import com.pws.admin.dto.UserRoleXrefDTO;
import com.pws.admin.entity.Module;
import com.pws.admin.entity.Permission;
import com.pws.admin.entity.Role;
import com.pws.admin.entity.User;
import com.pws.admin.entity.UserRoleXref;
import com.pws.admin.exception.config.PWSException;

import java.util.List;
import java.util.Optional;

/**
 * @Author Vinayak M
 * @Date 09/01/23
 */
public interface AdminService {

    void UserSignUp(SignUpDTO signupDTO) throws PWSException;

    //Role Services

    void addRole(Role role) throws PWSException;

    void updateRole(Role role) throws PWSException;

    List<Role> fetchAllRole() throws PWSException;

    Optional<Role> fetchRoleById(Integer id) throws PWSException;

    void deactivateOrActivateRoleById(Integer id,  boolean flag) throws PWSException;

    //Module Service

    void addModule(Module module) throws PWSException;

    void updateRole(Module module) throws PWSException;

    List<Module> fetchAllModule() throws PWSException;

    Optional<Module> fetchModuleById(Integer id) throws PWSException;

    void deactivateOrActivateModuleById(Integer id, boolean flag) throws PWSException;

    // User Role Services
    //Create a DTO and pass in this method
    void saveOrUpdateUserXref(UserRoleXrefDTO userRoleXrefDTO) throws PWSException;

    //Create a DTO and pass in this method
    void deactivateOrActivateAssignedRoleToUser( Integer id , Boolean  flag) throws PWSException;

    List<User> fetchUserByRole(Integer roleId)throws PWSException;

    Optional<UserRoleXref> fetchUserById(Integer Id) throws PWSException;
    //Role Permission Services

    void addPermission(PermissionDTO permissionDTO) throws PWSException;

    void updatePermission(PermissionDTO permissionDTO) throws PWSException;

    List<Permission> fetchAllPermission() throws PWSException;

    Optional<Permission> fetchPermissionById(Integer id) throws PWSException;

    void deactivateOrActivatePermissionById(PermissionDTO permissionDTO) throws PWSException;






}
