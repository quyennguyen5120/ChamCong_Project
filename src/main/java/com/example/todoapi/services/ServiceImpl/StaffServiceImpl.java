package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.dtos.UserDTO;
import com.example.todoapi.entities.*;
import com.example.todoapi.repositories.RoleRepository;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.repositories.UserRepository;
import com.example.todoapi.services.StaffService;
import com.example.todoapi.services.TimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TimeKeepingService timeKeepingService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TimeKeepingRepository timeKeepingRepository;



    public List<StaffDTO> getAll(){
        List<StaffDTO> staffDTOS =  staffRepository.getAll();
        staffDTOS.forEach(x->{
            List<TimekeepingDTO> timekeepingDTOS = timeKeepingRepository.findByStaffId(x.getId());
            Set<TimekeepingDTO> foo = new HashSet<TimekeepingDTO>(timekeepingDTOS);
            x.setTimekeeping(foo);
        });

        return staffDTOS;
    }


    public StaffDTO findById(Long id){
        StaffEntity staff = null;
        staff = staffRepository.getById(id);
        if(staff == null){
            return null;
        }
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setEmail(staff.getEmail());
        staffDTO.setAddress(staff.getAddress());
        staffDTO.setFullname(staff.getFullname());
        staffDTO.setAge(staff.getAge());
        TimekeepingDTO timekeepingDTO = timeKeepingService.getByStaff(staff.getId());
        staffDTO.setTimekeepingConvert(timekeepingDTO);
        return staffDTO;
    }

    public boolean deleteById(Long id){
        try {
            staffRepository.deleteById(id);
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public StaffDTO saveOrUpdate(StaffDTO staffDTO){
        StaffEntity staffEntity = null;

        if (staffDTO.getId() != null){
            staffEntity = staffRepository.findById(staffDTO.getId()).get();
        }else {
            staffEntity = new StaffEntity();
        }
        if(staffDTO.getEmail() != null){
            staffEntity.setEmail(staffDTO.getEmail());
        }
        staffEntity.setAge(staffDTO.getAge());
        staffEntity.setFullname(staffDTO.getFullname());
        staffEntity.setAddress(staffDTO.getAddress());

        if (staffDTO.getUserDTO().getId() != null){
            staffEntity.setUserEntity(userRepository.findById(staffDTO.getUserDTO().getId()).get());
        }else {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(staffDTO.getUserDTO().getEmail());
            userEntity.setPassword(passwordEncoder.encode(staffDTO.getUserDTO().getPassword()));
            userEntity.setUsername(staffDTO.getUserDTO().getUsername());
            userEntity.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
            userRepository.save(userEntity);
            staffEntity.setUserEntity(userEntity);
        }

        if (staffDTO.getUserParentDTO() != null && staffDTO.getUserParentDTO().getId() != null){
            staffEntity.setSubUserEntity(userRepository.findById(staffDTO.getUserParentDTO().getId()).get());
        }else {
            System.out.println("ko co UserParentDTO");
        }

        staffEntity.setTimekeeping(null);

        if (staffDTO.getSalaryDto() != null){
            SalaryEntity salaryEntity = new SalaryEntity();
            salaryEntity.setSalary(staffDTO.getSalaryDto().getSalary());
            salaryEntity.setId(staffDTO.getSalaryDto().getId());
        }
        if(staffEntity.getUserEntity().getUsername() != null || userRepository.findByUsername(staffEntity.getUserEntity().getUsername())== null){
            staffRepository.save(staffEntity);
        }else {
            staffEntity = null;
        }
//        staffEntity.setTimekeeping(null);
        staffRepository.save(staffEntity);
        return new StaffDTO(staffEntity);
    }

    @Override
    public List<UserDTO> findAllByIdRole(int id) {
        return userRepository.findUserEntitiesByRoleName(id);
    }
}
