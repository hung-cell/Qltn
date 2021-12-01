package com.example.officebuilding.service.contract;

import com.example.officebuilding.dtos.ContractDTO;
import com.example.officebuilding.entities.ContractEntity;
import com.example.officebuilding.repository.IContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService implements IContractService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IContractRepository contractRepository;

    @Override
    public List<ContractDTO> findAll() {
        // Lấy list entities từ db lên
        List<ContractEntity> contractEntities = contractRepository.findAll();

        // Đổi list entities đó về dto rồi trả về
        List<ContractDTO> contractDTOs = contractEntities.stream()
                .map(contractEntity -> modelMapper.map(contractEntity, ContractDTO.class))
                .collect(Collectors.toList());
        return contractDTOs;
    }

    @Override
    public Optional<ContractDTO> findById(Integer id) {
        // Lấy entity từ db lên
        Optional<ContractEntity> contractEntity = contractRepository.findById(id);

        // Chuyển sang dto rồi trả về
        Optional<ContractDTO> contractDTO = contractEntity
                .map(contractEntity1 -> modelMapper.map(contractEntity1, ContractDTO.class));
        return contractDTO;
    }

    @Override
    public ContractDTO save(ContractDTO contractDTO) {
        // chuyển từ DTO sang entity:
        ContractEntity contractEntity = modelMapper.map(contractDTO, ContractEntity.class);

        // save xuống db:
        ContractEntity updatedEntity = contractRepository.save(contractEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,ContractDTO.class);
    }

    @Override
    public void remove(Integer id) {
        contractRepository.deleteById(id);
    }
}