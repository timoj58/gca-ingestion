package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.OmittedProductsException;
import com.tabiiki.gca.gcaingestion.model.claim.OmittedProduct;
import com.tabiiki.gca.gcaingestion.util.CellUtils;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


@UtilityClass
public class OmittedProductTransformer {
    public List<OmittedProduct> transform(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<OmittedProduct> omittedProducts = new ArrayList<>();

        try {

            var startCell = CellUtils.locate(sheet, "Please provide the details of the products that were omitted:", new OmittedProductsException("failed to locate table"));
            var endCell = CellUtils.locate(sheet, "Claim Description/Explanation", new OmittedProductsException("failed to locate table"));

            for (int i = startCell.getRowIndex() + 1; i < endCell.getRowIndex() - 1; i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(1).toString().isEmpty()) {  //as poi doesnt get blank lines very well.  need to test all lines
                    omittedProducts.add(OmittedProduct.builder().build().builder()
                            .productCode(
                                    transformCell(() -> CellUtils.getValue(row.getCell(1)),
                                            exceptions, key, row.getRowNum(), "productCode")
                                            .map(m -> (String) m).orElse("")
                            )
                            .productDescription(
                                    transformCell(() -> CellUtils.getValue(row.getCell(2)),
                                            exceptions, key, row.getRowNum(), "productDescription")
                                            .map(m -> (String) m).orElse("")
                            )
                            .from(
                                    transformCell(() -> CellUtils.getValue(row.getCell(3)),
                                            exceptions, key, row.getRowNum(), "from")
                                            .map(m -> (String) m).orElse("")
                            )
                            .to(
                                    transformCell(() -> CellUtils.getValue(row.getCell(4)),
                                            exceptions, key, row.getRowNum(), "to")
                                            .map(m -> (String) m).orElse("")
                            )
                            .total(
                                    transformCell(() -> CellUtils.getValue(row.getCell(5)),
                                            exceptions, key, row.getRowNum(), "total")
                                            .map(m -> (String) m).orElse("")
                            )
                            .build());
                }
            }

        } catch (RuntimeException e) {
            exceptions.add(e);
        }

        return omittedProducts;
    }

    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new OmittedProductsException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }
}
