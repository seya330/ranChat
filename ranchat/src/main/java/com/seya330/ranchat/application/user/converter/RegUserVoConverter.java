package com.seya330.ranchat.application.user.converter;

import com.seya330.ranchat.application.user.vo.RegUserVo;
import com.seya330.ranchat.core.user.domain.RegUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegUserVoConverter {

  public RegUserVoConverter INSTANCE = Mappers.getMapper(RegUserVoConverter.class);

  RegUserVo toRegUserVo(RegUser regUser);
}
