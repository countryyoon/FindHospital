package com.example.findhospital.model

import android.os.Parcel
import android.os.Parcelable
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class hosLocation @JvmOverloads constructor(
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
    @field:ElementList(name = "item", inline = true, required = false)
    var elementItem: List<rItem>? = null
) : Parcelable{
    constructor(source: Parcel) : this(
    source.createTypedArrayList(rItem.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(elementItem)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<rItems> = object : Parcelable.Creator<rItems> {
            override fun createFromParcel(source: Parcel): rItems = rItems(source)
            override fun newArray(size: Int): Array<rItems?> = arrayOfNulls(size)
        }
    }
}

@Root(name = "item", strict = true)
data class rItem(
    @field:Element(name = "ykiho", required = false)
    var ykiho: String? = null,
    @field:Element(name = "yadmNm", required = false)
    var yadmNm: String? = null,
    @field:Element(name = "clCd", required = false)
    var clCd: String? = null,
    @field:Element(name = "clCdNm", required = false)
    var clCdNm: String? = null,
    @field:Element(name = "sidoCd", required = false)
    var sidoCd: String? = null,
    @field:Element(name = "sidoCdNm", required = false)
    var sidoCdNm: String? = null,
    @field:Element(name = "sgguCd", required = false)
    var sgguCd: String? = null,
    @field:Element(name = "sgguCdNm", required = false)
    var sgguCdNm: String? = null,
    @field:Element(name = "emdongNm", required = false)
    var emdongNm: String? = null,
    @field:Element(name = "postNo", required = false)
    var postNo: String? = null,
    @field:Element(name = "addr", required = false)
    var addr: String? = null,
    @field:Element(name = "telno", required = false)
    var telno: String? = null,
    @field:Element(name = "hospUrl", required = false)
    var hospUrl: String? = null,
    @field:Element(name = "estbDd", required = false)
    var estbDd: String? = null,
    @field:Element(name = "drTotCnt", required = false)
    var drTotCnt: String? = null,
    @field:Element(name = "gdrCnt", required = false)
    var gdrCnt: String? = null,
    @field:Element(name = "intnCnt", required = false)
    var intnCnt: String? = null,
    @field:Element(name = "resdntCnt", required = false)
    var resdntCnt: String? = null,
    @field:Element(name = "sdrCnt", required = false)
    var sdrCnt: String? = null,
    @field:Element(name = "XPos", required = false)
    var XPos: String? = null,
    @field:Element(name = "YPos", required = false)
    var YPos: String? = null,
    @field:Element(name = "distance", required = false)
    var distance: String? = null
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
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(ykiho)
        writeString(yadmNm)
        writeString(clCd)
        writeString(clCdNm)
        writeString(sidoCd)
        writeString(sidoCdNm)
        writeString(sgguCd)
        writeString(sgguCdNm)
        writeString(emdongNm)
        writeString(postNo)
        writeString(addr)
        writeString(telno)
        writeString(hospUrl)
        writeString(estbDd)
        writeString(drTotCnt)
        writeString(gdrCnt)
        writeString(intnCnt)
        writeString(resdntCnt)
        writeString(sdrCnt)
        writeString(XPos)
        writeString(YPos)
        writeString(distance)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<rItem> = object : Parcelable.Creator<rItem> {
            override fun createFromParcel(source: Parcel): rItem = rItem(source)
            override fun newArray(size: Int): Array<rItem?> = arrayOfNulls(size)
        }
    }
}