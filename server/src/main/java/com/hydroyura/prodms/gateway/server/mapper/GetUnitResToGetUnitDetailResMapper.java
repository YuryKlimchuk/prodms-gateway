package com.hydroyura.prodms.gateway.server.mapper;

import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
import com.hydroyura.prodms.common.mapper.OneSideMapper;
import com.hydroyura.prodms.files.server.api.res.GetUrlsLatestRes;
import com.hydroyura.prodms.gateway.server.model.res.GetUnitDetailedRes;
import org.mapstruct.Mapper;

@Mapper
public interface GetUnitResToGetUnitDetailResMapper extends OneSideMapper<GetUnitRes, GetUnitDetailedRes> {


    default GetUnitDetailedRes convertWithUrls(GetUnitRes source, GetUrlsLatestRes urls) {
        var destination = toDestination(source);
        //destination.setUrls(urls.getDrawings());
        return destination;
    }

}
