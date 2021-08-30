package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.AuditorException;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Auditor;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class AuditorTransformer {

    public Auditor transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        var auditCompany = TransformExceptionHandler.handle(exceptions::add, () -> transformAuditorCompany(sheet), key);
        var auditorName = TransformExceptionHandler.handle(exceptions::add, () -> transformAuditorName(sheet), key);
        var auditorEmail = TransformExceptionHandler.handle(exceptions::add, () -> transformAuditorEmail(sheet), key);
        var auditorPhone = TransformExceptionHandler.handle(exceptions::add, () -> transformAuditorPhone(sheet), key);

        return Auditor.builder()
                .auditCompany(auditCompany.map(m -> (String) m).orElse(""))
                .auditorName(auditorName.map(m -> (String) m).orElse(""))
                .auditorEmail(auditorEmail.map(m -> (String) m).orElse(""))
                .auditorPhone(auditorPhone.map(m -> (String) m).orElse(""))
                .build();
    }

    private String transformAuditorCompany(Sheet sheet) throws AuditorException {
        try {
            return "";
        } catch (Exception e) {
            throw new AuditorException("auditCompany");
        }
    }

    private String transformAuditorName(Sheet sheet) throws AuditorException {
        try {
            return "";
        } catch (Exception e) {
            throw new AuditorException("auditorName");
        }
    }

    private String transformAuditorEmail(Sheet sheet) throws AuditorException {
        try {
            return "";
        } catch (Exception e) {
            throw new AuditorException("auditorEmail");
        }
    }

    private String transformAuditorPhone(Sheet sheet) throws AuditorException {
        try {
            return "";
        } catch (Exception e) {
            throw new AuditorException("auditorPhone");
        }
    }
}
