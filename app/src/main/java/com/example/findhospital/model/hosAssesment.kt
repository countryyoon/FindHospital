package com.example.findhospital.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class hosAssesment @JvmOverloads constructor(
    @field:Element(name = "body", required = false)
    var asmBody: asmbody ?= null
)

@Root(name = "body", strict = false)
data class asmbody(
    @field:Element(name = "items", required = false)
    var itemlist: aItems ?= null,
    @field:Element(name = "numOfRows", required = false)
    var nRow: Int ?= null,
    @field:Element(name = "pageNo", required = false)
    var pNo: Int ?= null,
    @field:Element(name = "totalCount", required = false)
    var tCount: Int ?= null
)

@Root(name = "items", strict = false)
data class aItems(
    @field:Element(name = "item", required = false)
    var elementItem: aItem ?= null
)

@Root(name = "item", strict = false)
data class aItem(
    @field:Element(name = "addr", required = false)
    var addr: String ?=null,
    @field:Element(name = "asmGrd1", required = false)
    var asmGrd1: String ?=null,
    @field:Element(name = "asmGrd10", required = false)
    var asmGrd10: String ?=null,
    @field:Element(name = "asmGrd11", required = false)
    var asmGrd11: String ?=null,
    @field:Element(name = "asmGrd12", required = false)
    var asmGrd12: String ?=null,
    @field:Element(name = "asmGrd13", required = false)
    var asmGrd13: String ?=null,
    @field:Element(name = "asmGrd14", required = false)
    var asmGrd14: String ?=null,
    @field:Element(name = "asmGrd15", required = false)
    var asmGrd15: String ?=null,
    @field:Element(name = "asmGrd16", required = false)
    var asmGrd16: String ?=null,
    @field:Element(name = "asmGrd17", required = false)
    var asmGrd17: String ?=null,
    @field:Element(name = "asmGrd18", required = false)
    var asmGrd18: String ?=null,
    @field:Element(name = "asmGrd19", required = false)
    var asmGrd19: String ?=null,
    @field:Element(name = "asmGrd2", required = false)
    var asmGrd2: String ?=null,
    @field:Element(name = "asmGrd20", required = false)
    var asmGrd20: String ?=null,
    @field:Element(name = "asmGrd21", required = false)
    var asmGrd21: String ?=null,
    @field:Element(name = "asmGrd22", required = false)
    var asmGrd22: String ?=null,
    @field:Element(name = "asmGrd23", required = false)
    var asmGrd23: String ?=null,
    @field:Element(name = "asmGrd24", required = false)
    var asmGrd24: String ?=null,
    @field:Element(name = "asmGrd25", required = false)
    var asmGrd25: String ?=null,
    @field:Element(name = "asmGrd26", required = false)
    var asmGrd26: String ?=null,
    @field:Element(name = "asmGrd27", required = false)
    var asmGrd27: String ?=null,
    @field:Element(name = "asmGrd28", required = false)
    var asmGrd28: String ?=null,
    @field:Element(name = "asmGrd29", required = false)
    var asmGrd29: String ?=null,
    @field:Element(name = "asmGrd3", required = false)
    var asmGrd3: String ?=null,
    @field:Element(name = "asmGrd30", required = false)
    var asmGrd30: String ?=null,
    @field:Element(name = "asmGrd4", required = false)
    var asmGrd4: String ?=null,
    @field:Element(name = "asmGrd5", required = false)
    var asmGrd5: String ?=null,
    @field:Element(name = "asmGrd6", required = false)
    var asmGrd6: String ?=null,
    @field:Element(name = "asmGrd7", required = false)
    var asmGrd7: String ?=null,
    @field:Element(name = "asmGrd8", required = false)
    var asmGrd8: String ?=null,
    @field:Element(name = "asmGrd9", required = false)
    var asmGrd9: String ?=null,
    @field:Element(name = "clCd", required = false)
    var clCd: String ?=null,
    @field:Element(name = "clCdNm", required = false)
    var clCdNm: String ?=null,
    @field:Element(name = "yadmNm", required = false)
    var yadmNm: String ?=null
)