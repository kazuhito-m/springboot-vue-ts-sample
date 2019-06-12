package com.github.kazuhito_m.mysample.infrastructure.datasource.operationhistory;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;

@ConfigAutowireable
@Dao
public interface OperationHistoryDao {
    @Select
    List<OperationHistoryTable> list();

    @Insert
    Result<OperationHistoryTable> register(OperationHistoryTable operationHistory);
}
