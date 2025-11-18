package Entity

import android.graphics.Bitmap
import java.util.Date

class Person {

    private var id: String=""
    private var name: String=""
    private var flastName: String=""
    private var sLastName: String=""
    private var phone: Int=0
    private var email: String=""
    private lateinit var birthday: Date
    private lateinit var province: Province
    private var state: String=""
    private var district: String=""
    private var address: String=""
    private var latitude: Int=0
    private var longitude: Int=0
    private lateinit var photo: Bitmap

    constructor(id: String, name: String, flastname: String, slastname: String, phone: Int, email: String,
                birthday: Date, province: Province, state: String, district: String, address: String){
        this.id=id
        this.name=name
        this.flastName=flastname
        this.sLastName=slastname
        this.phone=phone
        this.email=email
        this.birthday=birthday
        this.province=province
        this.state=state
        this.district=district
        this.address=address

    }

    var Id: String
        get()= this.id
        set(value) {this.id=value}

    var Name: String
        get()= this.name
        set(value) {this.name=value}

    var flastname: String
        get()= this.flastName
        set(value) {this.flastName = value }

    var slastname: String
        get()= this.sLastName
        set(value) {this.sLastName = value }

    var BirthDate: Date
        get()=this.BirthDate
        set(value){this.BirthDate = value }

    var Province: Province
        get()=this.Province
        set(value){this.Province = value }

    var Phone: Int
        get()= this.phone
        set(value){this.phone = value }

    var Email: String
        get()= this.email
        set(value) {this.email = value }

    var Address: String
        get()= this.address
        set(value) {this.address =value}

    fun FullName()= "$this.name $this.flastName $this.slastname"

}
