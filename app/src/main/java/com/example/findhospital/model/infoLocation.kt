package com.example.findhospital.model

import android.os.Parcel
import android.os.Parcelable
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class infoLocation @JvmOverloads constructor(
    @field:Element(name = "body", required = false)
    var rBody: lbody ?= null
)

@Root(name = "body", strict = false)
data class lbody(
    @field:Element(name = "items", required = false)
    var itemlist: lItems ?= null,
    @field:Element(name = "numOfRows", required = false)
    var nRow: Int ?= null,
    @field:Element(name = "pageNo", required = false)
    var pNo: Int ?= null,
    @field:Element(name = "totalCount", required = false)
    var tCount: Int ?= null
)

@Root(name = "items", strict = false)
data class lItems(
    @field:ElementList(name = "item",inline = true ,required = false)
    var elementItem: List<lItem> ?= null
)

@Root(name = "item", strict = true)
data class lItem(
    @field:Element(name = "rnum", required = false)
    var rnum: String? = null,
    @field:Element(name = "distance", required = false)
    var distance: String? = null,
    @field:Element(name = "dutyAddr", required = false)
    var dAddr: String? = null,
    @field:Element(name = "dutyDiv", required = false)
    var dDiv: String? = null,
    @field:Element(name = "dutyDivName", required = false)
    var dDivName: String? = null,
    @field:Element(name = "dutyEmcls", required = false)
    var dEmcls: String? = null,
    @field:Element(name = "dutyFax", required = false)
    var dFax: String? = null,
    @field:Element(name = "dutyLvkl", required = false)
    var dLvkl: String? = null,
    @field:Element(name = "dutyName", required = false)
    var dName: String? = null,
    @field:Element(name = "dutyTel1", required = false)
    var dTel: String? = null,
    @field:Element(name = "latitude", required = false)
    var ilat: String? = null,
    @field:Element(name = "longitude", required = false)
    var ilon: String? = null,
    @field:Element(name = "startTime", required = false)
    var sTime: String? = null,
    @field:Element(name = "endTime", required = false)
    var eTime: String? = null,
    @field:Element(name = "hpid", required = false)
    var hpId: String? = null,
    @field:Element(name = "cnt", required = false)
    var cnt: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(rnum)
        writeString(distance)
        writeString(dAddr)
        writeString(dDiv)
        writeString(dDivName)
        writeString(dEmcls)
        writeString(dFax)
        writeString(dLvkl)
        writeString(dName)
        writeString(dTel)
        writeString(ilat)
        writeString(ilon)
        writeString(sTime)
        writeString(eTime)
        writeString(cnt)
        writeString(hpId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<lItem> = object : Parcelable.Creator<lItem> {
            override fun createFromParcel(source: Parcel): lItem = lItem(source)
            override fun newArray(size: Int): Array<lItem?> = arrayOfNulls(size)
        }
    }
}