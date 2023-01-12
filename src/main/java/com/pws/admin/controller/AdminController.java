package com.pws.admin.controller;

import com.pws.admin.dto.PermissionDTO;
import com.pws.admin.dto.SignUpDTO;
import com.pws.admin.dto.UserRoleXrefDTO;
import com.pws.admin.ApiSuccess;
import com.pws.admin.entity.Module;
import com.pws.admin.entity.Permission;
import com.pws.admin.entity.Role;
import com.pws.admin.entity.User;
import com.pws.admin.entity.UserRoleXref;
import com.pws.admin.exception.config.PWSException;
import com.pws.admin.service.AdminService;
import com.pws.admin.utility.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author Vinayak M
 * @Date 09/01/23
 */

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("public//signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpDTO signUpDTO) throws PWSException {
        adminService.UserSignUp(signUpDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.CREATED));
    }


    @PostMapping("private/role/add")
    public ResponseEntity<Object> addRole(@RequestBody Role role) throws PWSException {
        adminService.addRole(role);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

    @PutMapping ("private/role/update")
    public ResponseEntity<Object> updateRole(@RequestBody Role role) throws PWSException {
        adminService.updateRole(role);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

    @GetMapping ("private/role/fetch/by/id")
    public ResponseEntity<Object> fetchRoleById( @RequestParam Integer id) throws PWSException {
        Optional<Role> role = adminService.fetchRoleById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, role.get()));
    }

    @GetMapping("private/role/fetch/all")
    public ResponseEntity<Object> fetchAllRole() throws PWSException {
        List<Role> roleList = adminService.fetchAllRole();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, roleList));
    }

    @PostMapping("private/role/activate/deactivate")
    public ResponseEntity<Object> deactivateOrActivateRoleById(@RequestParam Integer id, @RequestParam Boolean flag) throws PWSException {
        adminService.deactivateOrActivateRoleById(id, flag);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @PostMapping("private/module/add")
    public ResponseEntity<Object> addModule(@RequestBody Module module) throws PWSException {
        adminService.addModule(module);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @PutMapping("private/module/update")
    public ResponseEntity<Object> updateRole(@RequestBody Module module) throws PWSException {
        adminService.updateRole(module);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @GetMapping("private/module/fetchall")
    public ResponseEntity<Object> fetchAllModule() throws PWSException {
        List<Module> modulelist = adminService.fetchAllModule();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, modulelist));
    }
    
    @GetMapping("private/module/fetch/id")
    public ResponseEntity<Object> fetchModuleById(@RequestParam Integer id) throws PWSException {
        Optional<Module> module= adminService.fetchModuleById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, module));
    }
    
    @PostMapping("private/module/activate/inactivate")
    public ResponseEntity<Object> deactivateOrActivateModuleById(@RequestParam  Integer id,@RequestParam Boolean flag) throws PWSException {
        adminService.deactivateOrActivateModuleById(id,flag);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @PostMapping("private/save/update/userxref")
    public ResponseEntity<Object> saveOrUpdateUserXref(@RequestBody  UserRoleXrefDTO userRoleXrefDTO) throws PWSException {
        adminService.saveOrUpdateUserXref(userRoleXrefDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @PostMapping("private/userxref/activate/deactivate/byuser")
    public ResponseEntity<Object> deactivateOrActivateAssignedRoleToUser(@RequestParam Integer id, @RequestParam Boolean flag) throws PWSException {
        adminService.deactivateOrActivateAssignedRoleToUser(id, flag);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    
    @GetMapping("private/fetch/userbyrole")
    public ResponseEntity<Object> fetchUserByRole(@RequestParam Integer roleId) throws PWSException {
        List<User> user = adminService.fetchUserByRole(roleId);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, user));
    }
    
    @GetMapping("private/fetch/fetchUserById")
    public ResponseEntity<Object> fetchUserById(@RequestParam Integer Id) throws PWSException {
    			adminService.fetchUserById(Id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

    @PostMapping("private/permmision/add")
    public ResponseEntity<Object> addPermission(@RequestBody PermissionDTO permissionDTO) throws PWSException {
        adminService.addPermission(permissionDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @PutMapping("private/permmision/update")
    public ResponseEntity<Object> updatePermission(@RequestBody PermissionDTO permissionDTO) throws PWSException {
        adminService.updatePermission(permissionDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    @GetMapping("private/permission/fetchall")
    public ResponseEntity<Object> fetchAllPermission() throws PWSException {
    	 List<Permission> permissionlist = adminService.fetchAllPermission();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, permissionlist ));
    }
    
    @GetMapping("private/permission/fetchPermission/Id")
    public ResponseEntity<Object> fetchPermissionById(@RequestParam Integer id) throws PWSException {
    	Optional<Permission> optionalpermission = adminService.fetchPermissionById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, optionalpermission));
    }
    
    @PutMapping("private/permission/activate/deactivate/byuser")
    public ResponseEntity<Object> deactivateOrActivatePermissionById(@RequestBody PermissionDTO permissionDTO) throws PWSException {
        adminService.deactivateOrActivatePermissionById(permissionDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, permissionDTO));
    }
    
    
}