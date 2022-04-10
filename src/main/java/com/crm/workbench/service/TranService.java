package com.crm.workbench.service;

import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran getTranById(String id);

    List<TranHistory> getTranHistoryByTranId(String tranId);

    boolean changeStage(Tran t);


    Map<String, Object> getCharts();
}
