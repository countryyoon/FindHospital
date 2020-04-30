package com.example.findhospital.model

import android.os.Parcel
import android.os.Parcelable
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root


@Root(name = "response", strict = false)
data class getHospital @JvmOverloads constructor(
    @field:Element(name = "body", required = false)
    var rBody: rbody ?= null
)

@Root(name = "body", strict = false)
data class rbody(
    @field:Element(name = "items", required = false)
    var itemlist: rItems ?= null,
    @field:Element(name = "numOfRows", required = false)
    var nRow: Int ?= null,
    @field:Element(name = "pageNo", required = false)
    var pNo: Int ?= null,
    @field:Element(name = "totalCount", required = false)
    var tCount: Int ?= null
)

@Root(name = "items", strict = false)
data class rItems(
    @field:ElementList(name = "item",inline = true ,required = false)
    var elementItem: List<rItem> ?= null
)

@Root(name = "item", strict = true)
data class rItem(
    @field:Element(name = "hpid", required = false)
    var hpId: String? = null,
    @field:Element(name = "dutyName", required = false)
    var hName: String? = null,
    @field:Element(name = "postCdn1", required = false)
    var pCdn1: String? = null,
    @field:Element(name = "postCdn2", required = false)
    var pCdn2: String? = null,
    @field:Element(name = "dutyAddr", required = false)
    var hAddress: String? = null,
    @field:Element(name = "dutyTel1", required = false)
    var dTel1: String? = null,
    @field:Element(name = "dutyTel3", required = false)
    var dTel3: String? = null,
    @field:Element(name = "hvec", required = false)
    var hVec: String? = null,
    @field:Element(name = "hvoc", required = false)
    var hVoc: String? = null,
    @field:Element(name = "hvcc", required = false)
    var hVcc: String? = null,
    @field:Element(name = "hvncc", required = false)
    var hVncc: String? = null,
    @field:Element(name = "hvccc", required = false)
    var hVccc: String? = null,
    @field:Element(name = "hvicc", required = false)
    var hVicc: String? = null,
    @field:Element(name = "hvgc", required = false)
    var hVgc: String? = null,
    @field:Element(name = "dutyHayn", required = false)
    var dHayn: String? = null,
    @field:Element(name = "dutyHano", required = false)
    var dHano: String? = null,
    @field:Element(name = "dutyInf", required = false)
    var dInfo: String? = null,
    @field:Element(name = "dutyMapimg", required = false)
    var dMapimg: String? = null,
    @field:Element(name = "dutyEryn", required = false)
    var dEryn: String? = null,
    @field:Element(name = "dutyTime1c", required = false)
    var dTime1c: String? = null,
    @field:Element(name = "dutyTime1s", required = false)
    var dTime1s: String? = null,
    @field:Element(name = "dutyTime2c", required = false)
    var dTime2c: String? = null,
    @field:Element(name = "dutyTime2s", required = false)
    var dTime2s: String? = null,
    @field:Element(name = "dutyTime3c", required = false)
    var dTime3c: String? = null,
    @field:Element(name = "dutyTime3s", required = false)
    var dTime3s: String? = null,
    @field:Element(name = "dutyTime4c", required = false)
    var dTime4c: String? = null,
    @field:Element(name = "dutyTime4s", required = false)
    var dTime4s: String? = null,
    @field:Element(name = "dutyTime5c", required = false)
    var dTime5c: String? = null,
    @field:Element(name = "dutyTime5s", required = false)
    var dTime5s: String? = null,
    @field:Element(name = "dutyTime6c", required = false)
    var dTime6c: String? = null,
    @field:Element(name = "dutyTime6s", required = false)
    var dTime6s: String? = null,
    @field:Element(name = "dutyTime7c", required = false)
    var dTime7c: String? = null,
    @field:Element(name = "dutyTime7s", required = false)
    var dTime7s: String? = null,
    @field:Element(name = "dutyTime8c", required = false)
    var dTime8c: String? = null,
    @field:Element(name = "dutyTime8s", required = false)
    var dTime8s: String? = null,
    @field:Element(name = "MKioskTy25", required = false)
    var MKioskTy25: String? = null,
    @field:Element(name = "MKioskTy1", required = false)
    var MKioskTy1: String? = null,
    @field:Element(name = "MKioskTy2", required = false)
    var MKioskTy2: String? = null,
    @field:Element(name = "MKioskTy3", required = false)
    var MKioskTy3: String? = null,
    @field:Element(name = "MKioskTy4", required = false)
    var MKioskTy4: String? = null,
    @field:Element(name = "MKioskTy5", required = false)
    var MKioskTy5: String? = null,
    @field:Element(name = "MKioskTy6", required = false)
    var MKioskTy6: String? = null,
    @field:Element(name = "MKioskTy7", required = false)
    var MKioskTy7: String? = null,
    @field:Element(name = "MKioskTy8", required = false)
    var MKioskTy8: String? = null,
    @field:Element(name = "MKioskTy9", required = false)
    var MKioskTy9: String? = null,
    @field:Element(name = "MKioskTy10", required = false)
    var MKioskTy10: String? = null,
    @field:Element(name = "MKioskTy11", required = false)
    var MKioskTy11: String? = null,
    @field:Element(name = "o001", required = false)
    var o001: String? = null,
    @field:Element(name = "o002", required = false)
    var o002: String? = null,
    @field:Element(name = "o003", required = false)
    var o003: String? = null,
    @field:Element(name = "o004", required = false)
    var o004: String? = null,
    @field:Element(name = "o005", required = false)
    var o005: String? = null,
    @field:Element(name = "o006", required = false)
    var o006: String? = null,
    @field:Element(name = "o007", required = false)
    var o007: String? = null,
    @field:Element(name = "o008", required = false)
    var o008: String? = null,
    @field:Element(name = "o009", required = false)
    var o009: String? = null,
    @field:Element(name = "o010", required = false)
    var o010: String? = null,
    @field:Element(name = "o011", required = false)
    var o011: String? = null,
    @field:Element(name = "o012", required = false)
    var o012: String? = null,
    @field:Element(name = "o013", required = false)
    var o013: String? = null,
    @field:Element(name = "o014", required = false)
    var o014: String? = null,
    @field:Element(name = "o015", required = false)
    var o015: String? = null,
    @field:Element(name = "o016", required = false)
    var o016: String? = null,
    @field:Element(name = "o017", required = false)
    var o017: String? = null,
    @field:Element(name = "o018", required = false)
    var o018: String? = null,
    @field:Element(name = "o019", required = false)
    var o019: String? = null,
    @field:Element(name = "o020", required = false)
    var o020: String? = null,
    @field:Element(name = "o021", required = false)
    var o021: String? = null,
    @field:Element(name = "o022", required = false)
    var o022: String? = null,
    @field:Element(name = "o023", required = false)
    var o023: String? = null,
    @field:Element(name = "o024", required = false)
    var o024: String? = null,
    @field:Element(name = "o025", required = false)
    var o025: String? = null,
    @field:Element(name = "o026", required = false)
    var o026: String? = null,
    @field:Element(name = "o027", required = false)
    var o027: String? = null,
    @field:Element(name = "o028", required = false)
    var o028: String? = null,
    @field:Element(name = "o029", required = false)
    var o029: String? = null,
    @field:Element(name = "o030", required = false)
    var o030: String? = null,
    @field:Element(name = "o031", required = false)
    var o031: String? = null,
    @field:Element(name = "o032", required = false)
    var o032: String? = null,
    @field:Element(name = "o033", required = false)
    var o033: String? = null,
    @field:Element(name = "o034", required = false)
    var o034: String? = null,
    @field:Element(name = "o035", required = false)
    var o035: String? = null,
    @field:Element(name = "o036", required = false)
    var o036: String? = null,
    @field:Element(name = "o037", required = false)
    var o037: String? = null,
    @field:Element(name = "o038", required = false)
    var o038: String? = null,
    @field:Element(name = "wgs84Lat", required = false)
    var wgsLat: String? = null,
    @field:Element(name = "wgs84Lon", required = false)
    var wgsLon: String? = null,
    @field:Element(name = "dgidIdName", required = false)
    var rSubject: String? = null,
    @field:Element(name = "hpbdn", required = false)
    var hpbdn: String? = null,
    @field:Element(name = "hpccuyn", required = false)
    var hpccuyn: String? = null,
    @field:Element(name = "hpcuyn", required = false)
    var hpcuyn: String? = null,
    @field:Element(name = "hperyn", required = false)
    var hperyn: String? = null,
    @field:Element(name = "hpgryn", required = false)
    var hpgryn: String? = null,
    @field:Element(name = "hpicuyn", required = false)
    var hpicuyn: String? = null,
    @field:Element(name = "hpnicuyn", required = false)
    var hpnicuyn: String? = null,
    @field:Element(name = "hpopyn", required = false)
    var hpopyn: String? = null
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
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(hpId)
        writeString(hName)
        writeString(pCdn1)
        writeString(pCdn2)
        writeString(hAddress)
        writeString(dTel1)
        writeString(dTel3)
        writeString(hVec)
        writeString(hVoc)
        writeString(hVcc)
        writeString(hVncc)
        writeString(hVccc)
        writeString(hVicc)
        writeString(hVgc)
        writeString(dHayn)
        writeString(dHano)
        writeString(dInfo)
        writeString(dMapimg)
        writeString(dEryn)
        writeString(dTime1c)
        writeString(dTime1s)
        writeString(dTime2c)
        writeString(dTime2s)
        writeString(dTime3c)
        writeString(dTime3s)
        writeString(dTime4c)
        writeString(dTime4s)
        writeString(dTime5c)
        writeString(dTime5s)
        writeString(dTime6c)
        writeString(dTime6s)
        writeString(dTime7c)
        writeString(dTime7s)
        writeString(dTime8c)
        writeString(dTime8s)
        writeString(MKioskTy25)
        writeString(MKioskTy1)
        writeString(MKioskTy2)
        writeString(MKioskTy3)
        writeString(MKioskTy4)
        writeString(MKioskTy5)
        writeString(MKioskTy6)
        writeString(MKioskTy7)
        writeString(MKioskTy8)
        writeString(MKioskTy9)
        writeString(MKioskTy10)
        writeString(MKioskTy11)
        writeString(o001)
        writeString(o002)
        writeString(o003)
        writeString(o004)
        writeString(o005)
        writeString(o006)
        writeString(o007)
        writeString(o008)
        writeString(o009)
        writeString(o010)
        writeString(o011)
        writeString(o012)
        writeString(o013)
        writeString(o014)
        writeString(o015)
        writeString(o016)
        writeString(o017)
        writeString(o018)
        writeString(o019)
        writeString(o020)
        writeString(o021)
        writeString(o022)
        writeString(o023)
        writeString(o024)
        writeString(o025)
        writeString(o026)
        writeString(o027)
        writeString(o028)
        writeString(o029)
        writeString(o030)
        writeString(o031)
        writeString(o032)
        writeString(o033)
        writeString(o034)
        writeString(o035)
        writeString(o036)
        writeString(o037)
        writeString(o038)
        writeString(wgsLat)
        writeString(wgsLon)
        writeString(rSubject)
        writeString(hpbdn)
        writeString(hpccuyn)
        writeString(hpcuyn)
        writeString(hperyn)
        writeString(hpgryn)
        writeString(hpicuyn)
        writeString(hpnicuyn)
        writeString(hpopyn)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<rItem> = object : Parcelable.Creator<rItem> {
            override fun createFromParcel(source: Parcel): rItem = rItem(source)
            override fun newArray(size: Int): Array<rItem?> = arrayOfNulls(size)
        }
    }
}

