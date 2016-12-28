/*
 * **************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * **************************************************************************
 */
package com.qcadoo.mes.basic.product.importing;

import com.qcadoo.mes.basic.constants.BasicConstants;
import com.qcadoo.mes.basic.constants.ProductFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssortmentCellBinder implements CellBinder {

    private final DataDefinitionService dataDefinitionService;

    @Autowired
    public AssortmentCellBinder(DataDefinitionService dataDefinitionService) {
        this.dataDefinitionService = dataDefinitionService;
    }

    private DataDefinition getAssortmentDataDefinition() {
        // TODO assortment model is missing in BasicConstants. Probably we should add it there
        return dataDefinitionService.get(BasicConstants.PLUGIN_IDENTIFIER, "assortment");
    }

    @Override
    public void bind(Cell cell, Entity entity, BindingErrorsAccessor errorsAccessor) {
        if (null != cell) {
            String value = formatCell(cell);
            try {
                Entity assortment = getAssortmentDataDefinition()
                        .find()
                        .add(SearchRestrictions.eq("name", value))
                        .uniqueResult();
                if (null != assortment) {
                    entity.setField(getFieldName(), assortment);
                } else {
                    errorsAccessor.addError("notFound");
                }
            } catch (RuntimeException re) {
                errorsAccessor.addError("invalid");
            }
        }
    }

    @Override
    public String getFieldName() {
        return ProductFields.ASSORTMENT;
    }

}
