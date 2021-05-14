package com.swp12.meogjapatfrontend.Fragment_navi

class Meeting(
    private var menu: String, private var date: String, private var time: String,
    private var age:Int, private var num: Int)
{

    fun getMenu(): String {
        return this.menu
    }

    fun getDate(): String {
        return this.date
    }

    fun getTime(): String {
        return this.time
    }

    fun getAge(): Int {
        return this.age
    }

    fun getNum(): Int {
        return this.num
    }
}