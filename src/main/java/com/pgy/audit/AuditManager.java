package com.pgy.audit;

import java.util.List;

import com.pgy.audit.bean.AuditLog;

/**
 * The aduit log manager.
 *
 * @author Felix
 */
public interface AuditManager {

    int create(AuditLog auditLog);

    List<AuditLog> getAuditLog(long materialId);

}
