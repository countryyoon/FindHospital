package com.example.findhospital.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/*
@Root(name = "Note", strict = false)
data class getHospital @JvmOverloads constructor(
    @field:Element(name = "to")
    @param:Element(name = "to")
    val To: String,
    @field:Element(name = "from")
    @param:Element(name = "from")
    val From: String,
    @field:Element(name = "heading")
    @param:Element(name = "heading")
    val Heading: String,
    @field:Element(name = "body")
    @param:Element(name = "body")
    val Body: String
)

 */

@Root(name = "response", strict = false)
data class getHospital @JvmOverloads constructor(
    @field:ElementList(name = "header", required = false, inline = true)
    var rHeader: List<head> ?= null
)

@Root(name = "header", strict = true)
data class head(
    @field:Element(name = "resultCode", required = false)
    var rCode: String ? =null
)

/*
@Root(name = "response", strict = false)
data class getHospital @JvmOverloads constructor(
    @field:Element(name = "header", required = false)
    @param:Element(name = "header", required = false)
    var rHeader: head ?= null
)



@Root(name = "header", strict = false)
open class head @JvmOverloads constructor (
    @field:Element(name = "resultCode", required = false)
    @param:Element(name = "resultCode")
    var rCode: Int?=null,
    @field:Element(name = "resultMsg", required = false)
    @param:Element(name = "resultMsg")
    var rMsg: String?=null
)


 */


/*
@Root(name = "response", strict = false)
data class getHospital(
    @field:Element(name = "header", required = false)
    var header: String
)


@Root(name = "header")
data class gHeader(
    @field:Element(name = "resultCode", required = false)
    var resultCode: String,
    @field:Element(name = "resultMsg", required = false)
    var resultMsg: String
)
 */


/*
@Root(name = "items", strict = false)
data class getItemList(
    @field:ElementList(name = "item", inline = true)
    var itemList: List<getItem>?=null
)

@Root(name = "item")
data class getItem (
    @field:Element(name="dutyName")
    var dutyName: String? = null,
    @field:Element(name="dutyTel1")
    var dutyTel1: String? = null,
    @field:Element(name="dutyAddr")
    var dutyAddr: String? = null,
    @field:Element(name="dutyIdName")
    var dutyIdName: List<String>? = null
)
*/