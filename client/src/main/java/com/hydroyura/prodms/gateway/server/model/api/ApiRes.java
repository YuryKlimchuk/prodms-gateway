package com.hydroyura.prodms.gateway.server.model.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class ApiRes<T> {

    private UUID id;
    private Timestamp timestamp;

    @Accessors(chain=true)
    private T data;

    private Collection<String> warnings = new ArrayList<>();
    private Collection<String> errors = new ArrayList<>();

}
