package com.hydroyura.prodms.gateway.server.mapper;

import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
import com.hydroyura.prodms.common.mapper.OneSideMapper;
import com.hydroyura.prodms.gateway.server.model.res.GetUnitDetailedRes;

public interface GetUnitResToGetUnitDetailResMapper extends OneSideMapper<GetUnitRes, GetUnitDetailedRes> {


    default GetUnitDetailedRes convertWithUrls(GetUnitRes source) {
        var destination = toDestination(source);
        destination
    }

}
