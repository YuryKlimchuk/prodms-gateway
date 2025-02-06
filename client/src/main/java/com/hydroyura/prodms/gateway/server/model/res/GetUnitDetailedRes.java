package com.hydroyura.prodms.gateway.server.model.res;

import java.time.Instant;
import java.util.Collection;
import lombok.Data;

@Data
public class GetUnitDetailedRes {

    private String number;
    private String name;
    private UnitType type;
    private UnitStatus status;
    private Integer version;
    private Instant createdAt;
    private Instant updatedAt;
    private String additional;
    private Collection<UnitHist> history;



}
