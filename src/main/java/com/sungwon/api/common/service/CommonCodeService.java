package com.sungwon.api.common.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sungwon.api.common.constant.ResultCode;
import com.sungwon.api.common.dto.CommonCodeDTO;
import com.sungwon.api.common.dto.CommonGroupCodeDTO;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.entity.CommonCode;
import com.sungwon.api.common.entity.CommonGroupCode;
import com.sungwon.api.common.repository.CommonCodeRepository;
import com.sungwon.api.common.repository.CommonGroupCodeRepository;
import com.sungwon.api.common.utility.CommonUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCodeService {

    @Autowired
    CommonCodeRepository commonCodeRepository;

    @Autowired
    CommonGroupCodeRepository commonGroupCodeRepository;

    @Autowired
    CommonUtil commonUtil;

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    public ResponseDTO findAllCommonGroupCode() {
        List<CommonGroupCode> list = new ArrayList<>();
        commonGroupCodeRepository.findByUseYn("Y").forEach(e -> list.add((CommonGroupCode) e));
        ResponseDTO responseDTO = commonUtil.selectObject(list);

        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO insertCommonGroupCode(CommonGroupCodeDTO dto) {
        CommonGroupCode commonGroupCode = new CommonGroupCode();
        BeanUtils.copyProperties(dto, commonGroupCode);
        ResponseDTO responseDTO = validationCheckGroupCode(commonGroupCode);
        if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
            commonGroupCodeRepository.save(commonGroupCode);
            responseDTO.setResultCode(ResultCode.INSERT.getName());
            responseDTO.setMsg(ResultCode.INSERT.getValue());
            responseDTO.setRes(commonGroupCode);
        }

        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO modifyCommonGroupCode(Long id, CommonGroupCodeDTO dto) {
        Optional<CommonGroupCode> group = commonGroupCodeRepository.findById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if(group.isPresent()) {
            List<CommonCode> codeList = commonCodeRepository.findByGroupCode(dto.getGroupCode());
            if(codeList.isEmpty()) {
                CommonGroupCode commonGroupCode = new CommonGroupCode();
                BeanUtils.copyProperties(dto, commonGroupCode);
                commonGroupCode.setGroupId(id);
                responseDTO = validationCheckGroupCode(commonGroupCode);
                if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
                    commonGroupCodeRepository.save(commonGroupCode);
                    responseDTO.setResultCode(ResultCode.UPDATE.getName());
                    responseDTO.setMsg(ResultCode.UPDATE.getValue());
                    responseDTO.setRes(commonGroupCode);
                }
            } else {
                responseDTO.setResultCode(ResultCode.NO_MODIFY_COMMON_GROUP_CODE.getName());
                responseDTO.setMsg(ResultCode.NO_MODIFY_COMMON_GROUP_CODE.getValue());
            }
        } else {
            responseDTO.setResultCode(ResultCode.NO_FOUND_COMMON_GROUP_CODE.getName());
            responseDTO.setMsg(ResultCode.NO_FOUND_COMMON_GROUP_CODE.getValue());
        }

        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO removeCommonGroupCode(Long id) {
        Optional<CommonGroupCode> group = commonGroupCodeRepository.findById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if(group.isPresent()) {
            List<CommonCode> codeList = commonCodeRepository.findByGroupCode(group.get().getGroupCode());
            if(codeList.isEmpty()) {
                CommonGroupCode commonGroupCode = new CommonGroupCode();
                BeanUtils.copyProperties(group, commonGroupCode);
                commonGroupCode.setUseYn("N");
                commonGroupCodeRepository.save(commonGroupCode);
                responseDTO.setResultCode(ResultCode.UPDATE.getName());
                responseDTO.setMsg(ResultCode.UPDATE.getValue());
                responseDTO.setRes(commonGroupCode);
            } else {
                responseDTO.setResultCode(ResultCode.NO_MODIFY_COMMON_GROUP_CODE.getName());
                responseDTO.setMsg(ResultCode.NO_MODIFY_COMMON_GROUP_CODE.getValue());
            }
        } else {
            responseDTO.setResultCode(ResultCode.NO_FOUND_COMMON_GROUP_CODE.getName());
            responseDTO.setMsg(ResultCode.NO_FOUND_COMMON_GROUP_CODE.getValue());
        }

        return responseDTO;
    }

    private ResponseDTO validationCheckGroupCode(CommonGroupCode commonGroupCode) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<CommonGroupCode> groupCodes = commonGroupCodeRepository.findByGroupCode(commonGroupCode.getGroupCode());
        if(groupCodes.size() > 0) {
            responseDTO.setResultCode(ResultCode.NO_INSERT_SAME_COMMON_GROUP_CODE.getName());
            responseDTO.setMsg(ResultCode.NO_INSERT_SAME_COMMON_GROUP_CODE.getValue());
        }

        return responseDTO;
    }

    public ResponseDTO findCommonCode(String groupCode) {
        List<CommonCode> list = new ArrayList<>();
        commonCodeRepository.findByGroupCode(groupCode).forEach(e -> list.add(e));
        ResponseDTO responseDTO = commonUtil.selectObject(list);

        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO insertCommonCode(CommonCodeDTO dto) {
        CommonCode commonCode = new CommonCode();
        BeanUtils.copyProperties(dto, commonCode);
        ResponseDTO responseDTO = validationCheckCommonCode(commonCode);
        if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
            commonCodeRepository.save(commonCode);
            responseDTO.setResultCode(ResultCode.INSERT.getName());
            responseDTO.setMsg(ResultCode.INSERT.getValue());
            responseDTO.setRes(commonCode);
        }
        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO modifyCommonCode(Long id, CommonCodeDTO dto) {
        Optional<CommonCode> code = commonCodeRepository.findById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if(code.isPresent()) {
            CommonCode commonCode = new CommonCode();
            BeanUtils.copyProperties(dto, commonCode);
            commonCode.setId(id);
            commonCodeRepository.save(commonCode);
            responseDTO.setResultCode(ResultCode.UPDATE.getName());
            responseDTO.setMsg(ResultCode.UPDATE.getValue());
            responseDTO.setRes(commonCode);
        } else {
            responseDTO.setResultCode(ResultCode.NO_FOUND_COMMON_CODE.getName());
            responseDTO.setMsg(ResultCode.NO_FOUND_COMMON_CODE.getValue());
        }

        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO removeCommonCode(Long id) {
        Optional<CommonCode> code = commonCodeRepository.findById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if(code.isPresent()) {
            CommonCode commonCode = new CommonCode();
            BeanUtils.copyProperties(code, commonCode);
            commonCode.setUseYn("N");
            commonCodeRepository.save(commonCode);
            responseDTO.setResultCode(ResultCode.DELETE.getName());
            responseDTO.setMsg(ResultCode.DELETE.getValue());
            responseDTO.setRes(commonCode);
        } else {
            responseDTO.setResultCode(ResultCode.NO_FOUND_COMMON_CODE.getName());
            responseDTO.setMsg(ResultCode.NO_FOUND_COMMON_CODE.getValue());
        }

        return responseDTO;
    }

    private ResponseDTO validationCheckCommonCode(CommonCode commonCode) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<CommonGroupCode> codes = commonGroupCodeRepository.findByGroupCode(commonCode.getCode());
        if(codes.size() > 0) {
            responseDTO.setResultCode(ResultCode.NO_INSERT_SAME_COMMON_CODE.getName());
            responseDTO.setMsg(ResultCode.NO_INSERT_SAME_COMMON_CODE.getValue());
        }

        return responseDTO;
    }

}
