package com.hydroyura.prodms.gateway.server.controller.swagger;

import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import com.hydroyura.prodms.archive.client.model.req.CreateUnitReq;
import com.hydroyura.prodms.archive.client.model.req.ListUnitsReq;
import com.hydroyura.prodms.archive.client.model.req.PatchUnitReq;
import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
import com.hydroyura.prodms.archive.client.model.res.ListUnitsRes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UnitDocumentedController {

    ResponseEntity<ApiRes<GetUnitRes>> get(String number, HttpServletRequest request);
    ResponseEntity<ApiRes<ListUnitsRes>> list(ListUnitsReq req, HttpServletRequest request);
    ResponseEntity<ApiRes<Void>> create(CreateUnitReq req, HttpServletRequest request);
    ResponseEntity<ApiRes<Void>> patch(String number, PatchUnitReq req, HttpServletRequest request);
    ResponseEntity<ApiRes<Void>> delete(String number, HttpServletRequest request);


}



/*

1. Получить список units по фильтру (предикаты, сортировка, пагинация) (легковесное представление объектов)
2. Получить детальную информацию об конкретном unit (полная информация: обычные поля, история изменений, чертежи, тех. процесс)
3. Добавить новый unit с базовыми полями.
4. Удалить unit.
5. Изменить базовые параметры unit (статус, имя)
6. Добавить к существующему unit чертеж.
7. Добавить к существующему unit тех.процесс.



 */