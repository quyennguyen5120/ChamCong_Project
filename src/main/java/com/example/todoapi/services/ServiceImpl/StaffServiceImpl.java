package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.UserRepository;
import com.example.todoapi.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    UserRepository userRepository;

    public List<StaffDTO> getAll(){
        return  staffRepository.getAll();
    }

    public StaffDTO findById(Long id){
        return new StaffDTO(staffRepository.findById(id).get());
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
            staffEntity = staffRepository.findById(staffEntity.getId()).get();
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
        }


        if (staffDTO.getUserParentDTO().getId() != null){
            staffEntity.setSubUserEntity(userRepository.findById(staffDTO.getUserParentDTO().getId()).get());
        }

        return new StaffDTO(staffEntity);
    }

}
