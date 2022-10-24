package com.rbc.stocksapi.mappers;

import com.rbc.stocksapi.models.DowJonesIndex;
import com.rbc.stocksapi.models.DowJonesIndexDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IMapper {

    List<DowJonesIndexDTO> map(List<DowJonesIndex> dowJonesIndexList) ;

    DowJonesIndex map(DowJonesIndexDTO dowJonesIndexDTO);

    DowJonesIndexDTO map(DowJonesIndex dowJonesIndex);

}
